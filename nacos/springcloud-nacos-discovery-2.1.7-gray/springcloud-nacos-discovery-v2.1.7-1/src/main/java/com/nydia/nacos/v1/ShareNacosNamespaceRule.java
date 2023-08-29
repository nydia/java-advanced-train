package com.nydia.nacos.v1;

import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
                } else {
                    LOGGER.warn("A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}",
                            name, clusterName, instances);
                }
            }
 
            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToChoose);
 
            return new NacosServer(instance);
        } catch (Exception e) {
            LOGGER.warn("NacosRule error", e);
            return null;
        }
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
 
    }
 
}