package com.nydia.nacos.gray;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 共享nacos命名空间规则
 */
public class NacosGrayRule extends AbstractLoadBalancerRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(NacosGrayRule.class);

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Autowired
    private NacosGrayProperties nacosGrayProperties;

    private static final String CLIENT_GRAY_ATTR = "gray";
    private static final String CLIENT_GRAY_ATTR_VAL = "true";
    private static final String CONSUMER_GRAY_ATTR = "gray";
    private static final String CONSUMER_GRAY_ATTR_VAL = "true";
    private static final String SERVER_GRAY_ATTR = "gray";
    private static final String SERVER_GRAY_ATTR_VAL = "true";



    /**
     * 重写choose方法
     *
     * @param key
     * @return
     */
    @Override
    public Server choose(Object key) {
        try {
            String clusterName = this.nacosDiscoveryProperties.getClusterName();
            DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
            String name = loadBalancer.getName();

            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            List<Instance> instances = namingService.selectInstances(name, true);
            if (CollectionUtils.isEmpty(instances)) {
                return null;
            }
            List<Instance> instancesToChoose = instances;
            if (StringUtils.isNotBlank(clusterName)) {
                List<Instance> sameClusterInstances = instances.stream()
                        .filter(instance -> Objects.equals(clusterName, instance.getClusterName()))
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sameClusterInstances)) {

                    //灰度处理
                    sameClusterInstances = grapHandle(sameClusterInstances);

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

    /**
     * 根据灰度规则返回处理后的实例
     *
     * @param instances
     * @return
     */
    private List<Instance> grapHandle(List<Instance> instances) {

        //客户端是否灰度
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String header_gray = request.getHeader(CLIENT_GRAY_ATTR);
        if(StringUtils.isBlank(header_gray) || !CLIENT_GRAY_ATTR_VAL.equals(header_gray)){
            return instances;
        }

        //消费端是否灰度
        if(!StringUtils.equals(CONSUMER_GRAY_ATTR_VAL, nacosGrayProperties.getGray())){
            return instances;
        }

        //服务端是否灰度
        List<Instance> insts = new ArrayList<>();
        for(Instance inst : instances){
            Map<String, String> metadata = inst.getMetadata();
            if(metadata.get(SERVER_GRAY_ATTR) == null || !SERVER_GRAY_ATTR_VAL.equalsIgnoreCase(metadata.get(SERVER_GRAY_ATTR))){
                insts.add(inst);
            }
        }

        return insts;
    }

}