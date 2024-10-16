package com.nydia.nacos.v1;

import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * @description: 共享nacos命名空间规则
 */
public class ShareNacosNamespaceRule extends AbstractLoadBalancerRule {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(ShareNacosNamespaceRule.class);
 
    @Autowired
    private NacosDiscoveryPropertiesV2 nacosDiscoveryPropertiesV2;
    @Autowired
    private NacosShareProperties nacosShareProperties;
 
    /**
     * 重写choose方法
     *
     * @param key
     * @return
     */
    @Override
    public Server choose(Object key) {
        try {
            String clusterName = this.nacosDiscoveryPropertiesV2.getClusterName();
            DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
            String name = loadBalancer.getName();

            NamingService namingService = nacosDiscoveryPropertiesV2
                    .namingServiceInstance();
            List<Instance> instances = namingService.selectInstances(name, true);
            if (CollectionUtils.isEmpty(instances)) {
                LOGGER.warn("no instance in service {}, then to get share service's instance", name);
                List<Instance> shareNamingService = this.getShareNamingService(name);
                if (!CollectionUtils.isEmpty(shareNamingService))
                    instances = shareNamingService;
                else
                    return null;
            }
            List<Instance> instancesToChoose = instances;
            if (StringUtils.isNotBlank(clusterName)) {
                List<Instance> sameClusterInstances = instances.stream()
                        .filter(instance -> Objects.equals(clusterName,
                                instance.getClusterName()))
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sameClusterInstances)) {
                    instancesToChoose = sameClusterInstances;
                }
                else {
                    LOGGER.warn(
                            "A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}",
                            name, clusterName, instances);
                }
            }
 
            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToChoose);
 
            return new NacosServer(instance);
        }
        catch (Exception e) {
            LOGGER.warn("NacosRule error", e);
            return null;
        }
    }
 
 
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
 
    }
 
    private List<Instance> getShareNamingService(String serviceId) throws NacosException {
        List<Instance> instances = Collections.emptyList();
        Map<String, Set<String>> namespaceGroupMap = nacosShareProperties.getNamespaceGroupMap();
        Map<String, NamingService> namespace2NamingServiceMap = nacosDiscoveryPropertiesV2.shareNamingServiceInstances();
        for (Map.Entry<String, NamingService> entry : namespace2NamingServiceMap.entrySet()) {
            String namespace;
            NamingService namingService;
            if (isNull(namespace = entry.getKey()) || isNull(namingService = entry.getValue()))
                continue;
            Set<String> groupNames = namespaceGroupMap.get(namespace);
            List<Instance> shareInstances;
            if (Objects.isNull(groupNames)) {
                shareInstances = namingService.selectInstances(serviceId, true);
                if (!CollectionUtils.isEmpty(shareInstances))
                    break;
            } else {
                shareInstances = new ArrayList<>();
                for (String groupName : groupNames) {
                    List<Instance> subShareInstances = namingService.selectInstances(serviceId, groupName, true);
                    if (!CollectionUtils.isEmpty(subShareInstances)) {
                        shareInstances.addAll(subShareInstances);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(shareInstances)) {
                instances = shareInstances;
                break;
            }
        }
        return instances;
    }
}