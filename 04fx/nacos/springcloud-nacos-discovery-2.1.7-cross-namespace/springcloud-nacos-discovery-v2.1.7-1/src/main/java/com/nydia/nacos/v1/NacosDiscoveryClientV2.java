package com.nydia.nacos.v1;

import com.alibaba.cloud.nacos.NacosServiceInstance;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @description: naocs服务发现重写
 */
public class NacosDiscoveryClientV2 extends NacosDiscoveryClient {

    private static final Logger log = LoggerFactory.getLogger(NacosDiscoveryClientV2.class);

    private final NacosDiscoveryPropertiesV2 discoveryProperties;

    private final NacosShareProperties nacosShareProperties;

    public NacosDiscoveryClientV2(NacosDiscoveryPropertiesV2 discoveryProperties, NacosShareProperties nacosShareProperties) {
        super(discoveryProperties);
        this.discoveryProperties = discoveryProperties;
        this.nacosShareProperties = nacosShareProperties;
    }

    /**
     * Return all instances for the given service.
     *
     * @param serviceId id of service
     * @return list of instances
     * @throws NacosException nacosException
     */
    public List<ServiceInstance> getInstances(String serviceId) {
        try {
            List<Instance> instances = discoveryProperties.namingServiceInstance()
                    .selectInstances(serviceId, true);
            if (CollectionUtils.isEmpty(instances)) {
                Map<String, Set<String>> namespaceGroupMap = nacosShareProperties.getNamespaceGroupMap();
                Map<String, NamingService> namespace2NamingServiceMap = discoveryProperties.shareNamingServiceInstances();
                for (Map.Entry<String, NamingService> entry : namespace2NamingServiceMap.entrySet()) {
                    String namespace;
                    NamingService namingService;
                    if (StringUtils.isBlank(namespace = entry.getKey()) || Objects.isNull(namingService = entry.getValue()))
                        continue;
                    Set<String> groupNames = namespaceGroupMap.get(namespace);
                    List<Instance> shareInstances;
                    if (Objects.isNull(groupNames)) {
                        shareInstances = namingService.selectInstances(serviceId, true);
                        if (Objects.isNull(shareInstances))
                            break;
                    } else {
                        shareInstances = new ArrayList<>();
                        for (String groupName : groupNames) {
                            List<Instance> subShareInstances = namingService.selectInstances(serviceId, groupName, true);
                            if (Objects.isNull(subShareInstances)) {
                                shareInstances.addAll(subShareInstances);
                            }
                        }
                    }
                    if (Objects.isNull(shareInstances)) {
                        instances = shareInstances;
                        break;
                    }
                }
            }
            return hostToServiceInstanceList(instances, serviceId);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Can not get hosts from nacos server. serviceId: " + serviceId, e);
        }
    }

    /**
     * Return the names of all services.
     *
     * @return list of service names
     * @throws NacosException nacosException
     */
    @Override
    public List<String> getServices() {
        try {
            ListView<String> services = discoveryProperties.namingServiceInstance()
                    .getServicesOfServer(1, Integer.MAX_VALUE);
            return services.getData();
        } catch (Exception e) {
            log.error("get service name from nacos server fail,", e);
            return Collections.emptyList();
        }
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