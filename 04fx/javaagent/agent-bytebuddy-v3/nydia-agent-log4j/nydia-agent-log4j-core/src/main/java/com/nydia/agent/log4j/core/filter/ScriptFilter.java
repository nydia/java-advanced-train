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
package com.nydia.agent.log4j.core.filter;

import com.nydia.agent.log4j.Level;
import com.nydia.agent.log4j.Marker;
import com.nydia.agent.log4j.core.Filter;
import com.nydia.agent.log4j.core.LogEvent;
import com.nydia.agent.log4j.core.Logger;
import com.nydia.agent.log4j.core.config.Configuration;
import com.nydia.agent.log4j.core.config.Node;
import com.nydia.agent.log4j.core.config.plugins.Plugin;
import com.nydia.agent.log4j.core.config.plugins.PluginAttribute;
import com.nydia.agent.log4j.core.config.plugins.PluginConfiguration;
import com.nydia.agent.log4j.core.config.plugins.PluginElement;
import com.nydia.agent.log4j.core.config.plugins.PluginFactory;
import com.nydia.agent.log4j.core.script.AbstractScript;
import com.nydia.agent.log4j.core.script.ScriptRef;
import com.nydia.agent.log4j.message.Message;
import com.nydia.agent.log4j.message.ObjectMessage;
import com.nydia.agent.log4j.message.SimpleMessage;
import com.nydia.agent.log4j.status.StatusLogger;

import javax.script.SimpleBindings;

/**
 * Returns the onMatch result if the script returns True and returns the onMisMatch value otherwise.
 */
@Plugin(name = "ScriptFilter", category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE, printObject = true)
public final class ScriptFilter extends AbstractFilter {

    private static com.nydia.agent.log4j.Logger logger = StatusLogger.getLogger();

    private final AbstractScript script;
    private final Configuration configuration;

    private ScriptFilter(final AbstractScript script, final Configuration configuration, final Result onMatch,
                         final Result onMismatch) {
        super(onMatch, onMismatch);
        this.script = script;
        this.configuration = configuration;
        if (!(script instanceof ScriptRef)) {
            configuration.getScriptManager().addScript(script);
        }
    }

    @Override
    public Result filter(final com.nydia.agent.log4j.core.Logger logger, final Level level, final Marker marker, final String msg,
                         final Object... params) {
        final SimpleBindings bindings = new SimpleBindings();
        bindings.put("logger", logger);
        bindings.put("level", level);
        bindings.put("marker", marker);
        bindings.put("message", new SimpleMessage(msg));
        bindings.put("parameters", params);
        bindings.put("throwable", null);
        bindings.putAll(configuration.getProperties());
        bindings.put("substitutor", configuration.getStrSubstitutor());
        final Object object = configuration.getScriptManager().execute(script.getName(), bindings);
        return object == null || !Boolean.TRUE.equals(object) ? onMismatch : onMatch;
    }

    @Override
    public Result filter(final Logger logger, final Level level, final Marker marker, final Object msg,
                         final Throwable t) {
        final SimpleBindings bindings = new SimpleBindings();
        bindings.put("logger", logger);
        bindings.put("level", level);
        bindings.put("marker", marker);
        bindings.put("message", msg instanceof String ? new SimpleMessage((String)msg) : new ObjectMessage(msg));
        bindings.put("parameters", null);
        bindings.put("throwable", t);
        bindings.putAll(configuration.getProperties());
        bindings.put("substitutor", configuration.getStrSubstitutor());
        final Object object = configuration.getScriptManager().execute(script.getName(), bindings);
        return object == null || !Boolean.TRUE.equals(object) ? onMismatch : onMatch;
    }

    @Override
    public Result filter(final Logger logger, final Level level, final Marker marker, final Message msg,
                         final Throwable t) {
        final SimpleBindings bindings = new SimpleBindings();
        bindings.put("logger", logger);
        bindings.put("level", level);
        bindings.put("marker", marker);
        bindings.put("message", msg);
        bindings.put("parameters", null);
        bindings.put("throwable", t);
        bindings.putAll(configuration.getProperties());
        bindings.put("substitutor", configuration.getStrSubstitutor());
        final Object object = configuration.getScriptManager().execute(script.getName(), bindings);
        return object == null || !Boolean.TRUE.equals(object) ? onMismatch : onMatch;
    }

    @Override
    public Result filter(final LogEvent event) {
        final SimpleBindings bindings = new SimpleBindings();
        bindings.put("logEvent", event);
        bindings.putAll(configuration.getProperties());
        bindings.put("substitutor", configuration.getStrSubstitutor());
        final Object object = configuration.getScriptManager().execute(script.getName(), bindings);
        return object == null || !Boolean.TRUE.equals(object) ? onMismatch : onMatch;
    }

    @Override
    public String toString() {
        return script.getName();
    }

    /**
     * Creates the ScriptFilter.
     * @param script The script to run. The script must return a boolean value. Either script or scriptFile must be 
     *      provided.
     * @param match The action to take if a match occurs.
     * @param mismatch The action to take if no match occurs.
     * @param configuration the configuration 
     * @return A ScriptFilter.
     */
    @PluginFactory
    public static ScriptFilter createFilter(
            @PluginElement("Script") final AbstractScript script,
            @PluginAttribute("onMatch") final Result match,
            @PluginAttribute("onMismatch") final Result mismatch,
            @PluginConfiguration final Configuration configuration) {

        if (script == null) {
            LOGGER.error("A Script, ScriptFile or ScriptRef element must be provided for this ScriptFilter");
            return null;
        }
        if (script instanceof ScriptRef) {
            if (configuration.getScriptManager().getScript(script.getName()) == null) {
                logger.error("No script with name {} has been declared.", script.getName());
                return null;
            }
        }

        return new ScriptFilter(script, configuration, match, mismatch);
    }

}
