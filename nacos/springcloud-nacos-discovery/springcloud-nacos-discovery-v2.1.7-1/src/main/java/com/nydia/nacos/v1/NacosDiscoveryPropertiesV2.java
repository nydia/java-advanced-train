package com.nydia.nacos.v1;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.client.naming.utils.UtilAndComs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.alibaba.nacos.api.PropertyKeyConst.ENDPOINT_PORT;
import static com.alibaba.nacos.api.PropertyKeyConst.NAMING_LOAD_CACHE_AT_START;
import static com.alibaba.nacos.api.annotation.NacosProperties.*;
import static java.util.Objects.nonNull;

/**
 * @description: naocs服务发现属性重写
 */
public class NacosDiscoveryPropertiesV2 extends NacosDiscoveryProperties {

    private static final Logger log = LoggerFactory.getLogger(NacosDiscoveryPropertiesV2.class);

    private final NacosShareProperties nacosShareProperties;

    private static final Map<String, NamingService> NAMESPACE_TO_NAMING_SERVICE_MAP = new ConcurrentHashMap<>();

    public NacosDiscoveryPropertiesV2(NacosShareProperties nacosShareProperties) {
        super();
        this.nacosShareProperties = nacosShareProperties;
    }

    public Map<String, NamingService> shareNamingServiceInstances() {
        if (!NAMESPACE_TO_NAMING_SERVICE_MAP.isEmpty()) {
            return new HashMap<>(NAMESPACE_TO_NAMING_SERVICE_MAP);
        }
        List<NacosShareProperties.NacosShareEntity> entities = Optional.ofNullable(nacosShareProperties)
                .map(NacosShareProperties::getEntities).orElse(Collections.emptyList());
        entities.stream().filter(entity -> nonNull(entity) && nonNull(entity.getNamespace()))
                .filter(distinctByKey(NacosShareProperties.NacosShareEntity::getNamespace))
                .forEach(entity -> {
                    try {
                        NamingService namingService = NacosFactory.createNamingService(getNacosProperties(entity.getNamespace()));
                        if (namingService != null) {
                            NAMESPACE_TO_NAMING_SERVICE_MAP.put(entity.getNamespace(), namingService);
                        }
                    } catch (Exception e) {
                        log.error("create naming service error! properties={}, e=", this, e);
                    }
                });
        return new HashMap<>(NAMESPACE_TO_NAMING_SERVICE_MAP);
    }

    private Properties getNacosProperties(String namespace) {
        Properties properties = new Properties();
        properties.put(SERVER_ADDR, getServerAddr());
        properties.put(NAMESPACE, namespace);
        properties.put(UtilAndComs.NACOS_NAMING_LOG_NAME, getLogName());
        String endpoint = getEndpoint();
        if (endpoint.contains(":")) {
            int index = endpoint.indexOf(":");
            properties.put(ENDPOINT, endpoint.substring(0, index));
            properties.put(ENDPOINT_PORT, endpoint.substring(index + 1));
        } else {
            properties.put(ENDPOINT, endpoint);
        }

        properties.put(ACCESS_KEY, getAccessKey());
        properties.put(SECRET_KEY, getSecretKey());
        properties.put(CLUSTER_NAME, getClusterName());
        properties.put(NAMING_LOAD_CACHE_AT_START, getNamingLoadCacheAtStart());

        //enrichNacosDiscoveryProperties(properties);
        return properties;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}