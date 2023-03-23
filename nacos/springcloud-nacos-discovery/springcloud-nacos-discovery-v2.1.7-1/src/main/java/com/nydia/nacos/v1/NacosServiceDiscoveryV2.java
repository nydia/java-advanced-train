package com.nydia.nacos.v1;

import com.alibaba.cloud.nacos.NacosServiceInstance;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import org.springframework.cloud.client.ServiceInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description: naocs服务发现重写
 */
public class NacosServiceDiscoveryV2 extends NacosDiscoveryClient {
 
    private final NacosDiscoveryPropertiesV2 discoveryProperties;
 
    private final NacosShareProperties nacosShareProperties;
 
    public NacosServiceDiscoveryV2(NacosDiscoveryPropertiesV2 discoveryProperties, NacosShareProperties nacosShareProperties, NacosServiceManager nacosServiceManager) {
        super(discoveryProperties);
        this.discoveryProperties = discoveryProperties;
        this.nacosShareProperties = nacosShareProperties;
    }
 
    /**
     * Return all instances for the given service.
     * @param serviceId id of service
     * @return list of instances
     * @throws NacosException nacosException
     */
    public List<ServiceInstance> getInstances(String serviceId) throws NacosException {
        String group = discoveryProperties.getGroup();
        List<Instance> instances = discoveryProperties.namingServiceInstance()
                .selectInstances(serviceId, group, true);
        if (isEmpty(instances)) {
            Map<String, Set<String>> namespaceGroupMap = nacosShareProperties.getNamespaceGroupMap();
            Map<String, NamingService> namespace2NamingServiceMap = discoveryProperties.shareNamingServiceInstances();
            for (Map.Entry<String, NamingService> entry : namespace2NamingServiceMap.entrySet()) {
                String namespace;
                NamingService namingService;
                if (isNull(namespace = entry.getKey()) || isNull(namingService = entry.getValue()))
                    continue;
                Set<String> groupNames = namespaceGroupMap.get(namespace);
                List<Instance> shareInstances;
                if (isEmpty(groupNames)) {
                    shareInstances = namingService.selectInstances(serviceId, group, true);
                    if (nonEmpty(shareInstances))
                        break;
                } else {
                    shareInstances = new ArrayList<>();
                    for (String groupName : groupNames) {
                        List<Instance> subShareInstances = namingService.selectInstances(serviceId, groupName, true);
                        if (nonEmpty(subShareInstances)) {
                            shareInstances.addAll(subShareInstances);
                        }
                    }
                }
                if (nonEmpty(shareInstances)) {
                    instances = shareInstances;
                    break;
                }
            }
        }
        return hostToServiceInstanceList(instances, serviceId);
    }
 
    /**
     * Return the names of all services.
     * @return list of service names
     * @throws NacosException nacosException
     */
    public List<String> getServices() throws NacosException {
        String group = discoveryProperties.getGroup();
        ListView<String> services = discoveryProperties.namingServiceInstance()
                .getServicesOfServer(1, Integer.MAX_VALUE, group);
        return services.getData();
    }
 
    public static List<ServiceInstance> hostToServiceInstanceList(
            List<Instance> instances, String serviceId) {
        List<ServiceInstance> result = new ArrayList<>(instances.size());
        for (Instance instance : instances) {
            ServiceInstance serviceInstance = hostToServiceInstance(instance, serviceId);
            if (serviceInstance != null) {
                result.add(serviceInstance);
            }
        }
        return result;
    }
 
    public static ServiceInstance hostToServiceInstance(Instance instance,
                                                        String serviceId) {
        if (instance == null || !instance.isEnabled() || !instance.isHealthy()) {
            return null;
        }
        NacosServiceInstance nacosServiceInstance = new NacosServiceInstance();
        nacosServiceInstance.setHost(instance.getIp());
        nacosServiceInstance.setPort(instance.getPort());
        nacosServiceInstance.setServiceId(serviceId);
 
        Map<String, String> metadata = new HashMap<>();
        metadata.put("nacos.instanceId", instance.getInstanceId());
        metadata.put("nacos.weight", instance.getWeight() + "");
        metadata.put("nacos.healthy", instance.isHealthy() + "");
        metadata.put("nacos.cluster", instance.getClusterName() + "");
        metadata.putAll(instance.getMetadata());
        nacosServiceInstance.setMetadata(metadata);
 
        if (metadata.containsKey("secure")) {
            boolean secure = Boolean.parseBoolean(metadata.get("secure"));
            nacosServiceInstance.setSecure(secure);
        }
        return nacosServiceInstance;
    }
}