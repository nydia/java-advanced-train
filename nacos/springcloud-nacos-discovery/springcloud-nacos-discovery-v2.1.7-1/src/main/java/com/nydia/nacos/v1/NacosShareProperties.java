package com.nydia.nacos.v1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.nonNull;

/**
 * <pre>
 *  @description: 共享nacos属性
 */
@Configuration
@ConfigurationProperties(prefix = "nacos.share")
public class NacosShareProperties {

    private final Map<String, Set<String>> NAMESPACE_TO_GROUP_NAME_MAP = new ConcurrentHashMap<>();
    /**
     * 共享nacos实体列表
     */
    private List<NacosShareEntity> entities;

    public List<NacosShareEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<NacosShareEntity> entities) {
        this.entities = entities;
    }

    public Map<String, Set<String>> getNamespaceGroupMap() {
        entities.stream().filter(entity -> nonNull(entity) && nonNull(entity.getNamespace()))
                .forEach(entity -> {
                    Set<String> groupNames = NAMESPACE_TO_GROUP_NAME_MAP.computeIfAbsent(entity.getNamespace(), k -> new HashSet<>());
                    if (nonNull(entity.getGroupNames()))
                        groupNames.addAll(entity.getGroupNames());
                });
        return new HashMap<>(NAMESPACE_TO_GROUP_NAME_MAP);
    }

    @Override
    public String toString() {
        return "NacosShareProperties{" +
                "entities=" + entities +
                '}';
    }

    /**
     * 共享nacos实体
     */
    public static final class NacosShareEntity {

        /**
         * 命名空间
         */
        private String namespace;

        /**
         * 分组
         */
        private List<String> groupNames;

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public List<String> getGroupNames() {
            return groupNames;
        }

        public void setGroupNames(List<String> groupNames) {
            this.groupNames = groupNames;
        }

        @Override
        public String toString() {
            return "NacosShareEntity{" +
                    "namespace='" + namespace + "" +
                    ", groupNames=" + groupNames +
                    '}';
        }
    }
}