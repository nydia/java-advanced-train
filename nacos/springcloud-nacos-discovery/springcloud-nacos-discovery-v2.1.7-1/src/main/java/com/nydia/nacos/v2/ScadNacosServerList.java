package com.nydia.nacos.v2;//package com.nydia.nacos;
//
//import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
//import com.alibaba.cloud.nacos.ribbon.NacosServer;
//import com.alibaba.cloud.nacos.ribbon.NacosServerList;
//import com.alibaba.nacos.api.naming.pojo.Instance;
//import com.alibaba.nacos.client.naming.utils.CollectionUtils;
//import com.netflix.client.config.IClientConfig;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ScadNacosServerList extends NacosServerList {
//
//    private NacosDiscoveryProperties discoveryProperties;
//
//    private String serviceId;
//
//    public ScadNacosServerList(NacosDiscoveryProperties discoveryProperties) {
//        super(discoveryProperties);
//        this.discoveryProperties = discoveryProperties;
//    }
//
//    @Override
//    public List<NacosServer> getInitialListOfServers() {
//        return getDiffNamespaceServers();
//    }
//
//    @Override
//    public List<NacosServer> getUpdatedListOfServers() {
//        return getDiffNamespaceServers();
//    }
//
//    /**
//     * nacos默认只寻找相同namespace和相同group里的服务实例。本项目需要获取同namespace中不同group
//     * 里的项目，所以对源码进行改造，能获取不同group下的服务实例。
//     *
//     * @return
//     */
//    private List<NacosServer> getDiffGroupServers() {
//        try {
//            //获取同namesapce中不同group下服务实例。如果要获取不同namespace，可以调用nacos中提供的获取所有namespace的接口，然后循环遍历即可。
//            //在nacos提供的open api中，并没有获取所有分组group的方法，因此，我们只能自己去维护我们的项目里定义了哪些分组，然后手动获取这些分组下的服务实例。
//            //获取master分组服务实例
//            List<Instance> instances1 = discoveryProperties.namingServiceInstance()
//                    .selectInstances(serviceId, "master", true);
//            //获取slave分组服务实例
//            List<Instance> instances2 = discoveryProperties.namingServiceInstance()
//                    .selectInstances(serviceId, "slave", true);
//            List<Instance> instances = new ArrayList<>();
//            instances.addAll(instances1);
//            instances.addAll(instances2);
//            //master分组和slave分组实例都加入处理
//            return instancesToServerList(instances);
//        } catch (Exception e) {
//            throw new IllegalStateException(
//                    "Can not get service instances from nacos, serviceId=" + serviceId,
//                    e);
//        }
//    }
//
//    /**
//     * nacos默认只寻找相同namespace和相同group里的服务实例。本项目需要获取同namespace中不同group
//     * 里的项目，所以对源码进行改造，能获取不同group下的服务实例。
//     *
//     * @return
//     */
//    private List<NacosServer> getDiffNamespaceServers() {
//        try {
//            discoveryProperties.namingServiceInstance().getAllInstances(serviceId, true);
//            //获取slave分组服务实例
//            List<Instance> instances = discoveryProperties.namingServiceInstance().selectInstances(serviceId, true);
//            instances.addAll(instances);
//            return instancesToServerList(instances);
//        } catch (Exception e) {
//            throw new IllegalStateException(
//                    "Can not get service instances from nacos, serviceId=" + serviceId, e);
//        }
//    }
//
//    private List<NacosServer> instancesToServerList(List<Instance> instances) {
//        List<NacosServer> result = new ArrayList<>();
//        if (CollectionUtils.isEmpty(instances)) {
//            return result;
//        }
//        for (Instance instance : instances) {
//            result.add(new NacosServer(instance));
//        }
//
//        return result;
//    }
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig iClientConfig) {
//
//        this.serviceId = iClientConfig.getClientName();
//    }
//
//}
