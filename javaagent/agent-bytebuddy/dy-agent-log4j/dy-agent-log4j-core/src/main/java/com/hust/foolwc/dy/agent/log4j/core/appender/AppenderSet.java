/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package com.hust.foolwc.dy.agent.log4j.core.appender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hust.foolwc.dy.agent.log4j.core.Appender;
import com.hust.foolwc.dy.agent.log4j.core.Core;
import com.hust.foolwc.dy.agent.log4j.core.config.Configuration;
import com.hust.foolwc.dy.agent.log4j.core.config.Node;
import com.hust.foolwc.dy.agent.log4j.core.config.plugins.Plugin;
import com.hust.foolwc.dy.agent.log4j.core.config.plugins.PluginBuilderFactory;
import com.hust.foolwc.dy.agent.log4j.core.config.plugins.PluginConfiguration;
import com.hust.foolwc.dy.agent.log4j.core.config.plugins.PluginNode;
import com.hust.foolwc.dy.agent.log4j.core.config.plugins.validation.constraints.Required;
import com.hust.foolwc.dy.agent.log4j.status.StatusLogger;
/**
 * A deferred plugin for appenders.
 */
@Plugin(name = "AppenderSet", category = Core.CATEGORY_NAME, printObject = true, deferChildren = true)
public class AppenderSet {

    public static class Builder implements com.hust.foolwc.dy.agent.log4j.core.util.Builder<AppenderSet> {

        @PluginNode
        private Node node;

        @PluginConfiguration
        @Required
        private Configuration configuration;

        @Override
        public AppenderSet build() {
            if (configuration == null) {
                LOGGER.error("Configuration is missing from AppenderSet {}", this);
                return null;                
            }
            if (node == null) {
                LOGGER.error("No node in AppenderSet {}", this);
                return null;
            }
            final List<Node> children = node.getChildren();
            if (children == null) {
                LOGGER.error("No children node in AppenderSet {}", this);
                return null;
            }
            final Map<String, Node> map = new HashMap<>(children.size());
            for (final Node childNode : children) {
                final String key = childNode.getAttributes().get("name");
                if (key == null) {
                    LOGGER.error("The attribute 'name' is missing from from the node {} in AppenderSet {}",
                            childNode, children);
                } else {
                    map.put(key, childNode);
                }
            }
            return new AppenderSet(configuration, map);
        }

        public Node getNode() {
            return node;
        }

        public Configuration getConfiguration() {
            return configuration;
        }

        public Builder withNode(@SuppressWarnings("hiding") final Node node) {
            this.node = node;
            return this;
        }

        public Builder withConfiguration(@SuppressWarnings("hiding") final Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        @Override
        public String toString() {
            return getClass().getName() + " [node=" + node + ", configuration=" + configuration + "]";
        }

    }

    private static final StatusLogger LOGGER = StatusLogger.getLogger();

    private final Configuration configuration;
    private final Map<String, Node> nodeMap;

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    private AppenderSet(final Configuration configuration, final Map<String, Node> appenders) {
        this.configuration = configuration;
        this.nodeMap = appenders;
    }

    public Appender createAppender(final String appenderName, final String actualName) {
        final Node node = nodeMap.get(appenderName);
        if (node == null) {
            LOGGER.error("No node named {} in {}", appenderName, this);
            return null;
        }
        node.getAttributes().put("name", actualName);
        if (node.getType().getElementName().equals(Appender.ELEMENT_TYPE)) {
            final Node appNode = new Node(node);
            configuration.createConfiguration(appNode, null);
            if (appNode.getObject() instanceof Appender) {
                final Appender app = appNode.getObject();
                app.start();
                return app;
            }
            LOGGER.error("Unable to create Appender of type " + node.getName());
            return null;
        }
        LOGGER.error("No Appender was configured for name {} " + appenderName);
        return null;
    }
}