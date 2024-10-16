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
package com.nydia.agent.log4j.core.async;

import com.nydia.agent.log4j.Level;
import com.nydia.agent.log4j.LogManager;
import com.nydia.agent.log4j.core.Core;
import com.nydia.agent.log4j.core.Filter;
import com.nydia.agent.log4j.core.LogEvent;
import com.nydia.agent.log4j.core.config.AppenderRef;
import com.nydia.agent.log4j.core.config.Configuration;
import com.nydia.agent.log4j.core.config.LoggerConfig;
import com.nydia.agent.log4j.core.config.Node;
import com.nydia.agent.log4j.core.config.Property;
import com.nydia.agent.log4j.core.config.plugins.Plugin;
import com.nydia.agent.log4j.core.config.plugins.PluginAttribute;
import com.nydia.agent.log4j.core.config.plugins.PluginConfiguration;
import com.nydia.agent.log4j.core.config.plugins.PluginElement;
import com.nydia.agent.log4j.core.config.plugins.PluginFactory;
import com.nydia.agent.log4j.core.jmx.RingBufferAdmin;
import com.nydia.agent.log4j.core.util.Booleans;
import com.nydia.agent.log4j.util.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.nydia.agent.log4j.Level;
import com.nydia.agent.log4j.LogManager;
import com.nydia.agent.log4j.core.config.AppenderRef;
import com.nydia.agent.log4j.core.config.Configuration;
import com.nydia.agent.log4j.core.config.LoggerConfig;
import com.nydia.agent.log4j.core.config.Node;
import com.nydia.agent.log4j.core.config.Property;
import com.nydia.agent.log4j.core.config.plugins.Plugin;
import com.nydia.agent.log4j.core.config.plugins.PluginAttribute;
import com.nydia.agent.log4j.core.config.plugins.PluginConfiguration;
import com.nydia.agent.log4j.core.config.plugins.PluginElement;
import com.nydia.agent.log4j.core.config.plugins.PluginFactory;
import com.nydia.agent.log4j.Level;
import com.nydia.agent.log4j.LogManager;
import com.nydia.agent.log4j.core.config.AppenderRef;
import com.nydia.agent.log4j.core.config.Configuration;
import com.nydia.agent.log4j.core.config.Property;
import com.nydia.agent.log4j.core.config.plugins.*;

/**
 * Asynchronous Logger object that is created via configuration and can be
 * combined with synchronous loggers.
 * <p>
 * AsyncLoggerConfig is a logger designed for high throughput and low latency
 * logging. It does not perform any I/O in the calling (application) thread, but
 * instead hands off the work to another thread as soon as possible. The actual
 * logging is performed in the background thread. It uses the LMAX Disruptor
 * library for inter-thread communication. (<a
 * href="http://lmax-exchange.github.com/disruptor/"
 * >http://lmax-exchange.github.com/disruptor/</a>)
 * <p>
 * To use AsyncLoggerConfig, specify {@code <asyncLogger>} or
 * {@code <asyncRoot>} in configuration.
 * <p>
 * Note that for performance reasons, this logger does not include source
 * location by default. You need to specify {@code includeLocation="true"} in
 * the configuration or any %class, %location or %line conversion patterns in
 * your log4j.xml configuration will produce either a "?" character or no output
 * at all.
 * <p>
 * For best performance, use AsyncLoggerConfig with the RandomAccessFileAppender or
 * RollingRandomAccessFileAppender, with immediateFlush=false. These appenders have
 * built-in support for the batching mechanism used by the Disruptor library,
 * and they will flush to disk at the end of each batch. This means that even
 * with immediateFlush=false, there will never be any items left in the buffer;
 * all log events will all be written to disk in a very efficient manner.
 */
@Plugin(name = "asyncLogger", category = Node.CATEGORY, printObject = true)
public class AsyncLoggerConfig extends LoggerConfig {

    private final AsyncLoggerConfigDelegate delegate;

    protected AsyncLoggerConfig(final String name,
                                final List<AppenderRef> appenders, final Filter filter,
                                final Level level, final boolean additive,
                                final Property[] properties, final Configuration config,
                                final boolean includeLocation) {
        super(name, appenders, filter, level, additive, properties, config,
                includeLocation);
        delegate = config.getAsyncLoggerConfigDelegate();
        delegate.setLogEventFactory(getLogEventFactory());
    }

    /**
     * Passes on the event to a separate thread that will call
     * {@link #asyncCallAppenders(LogEvent)}.
     */
    @Override
    protected void callAppenders(final LogEvent event) {
        populateLazilyInitializedFields(event);

        if (!delegate.tryEnqueue(event, this)) {
            final EventRoute eventRoute = delegate.getEventRoute(event.getLevel());
            eventRoute.logMessage(this, event);
        }
    }

    private void populateLazilyInitializedFields(final LogEvent event) {
        event.getSource();
        event.getThreadName();
    }

    void callAppendersInCurrentThread(final LogEvent event) {
        super.callAppenders(event);
    }

    void callAppendersInBackgroundThread(final LogEvent event) {
        delegate.enqueueEvent(event, this);
    }

    /** Called by AsyncLoggerConfigHelper.RingBufferLog4jEventHandler. */
    void asyncCallAppenders(final LogEvent event) {
        super.callAppenders(event);
    }

    private String displayName() {
        return LogManager.ROOT_LOGGER_NAME.equals(getName()) ? LoggerConfig.ROOT : getName();
    }

    @Override
    public void start() {
        LOGGER.trace("AsyncLoggerConfig[{}] starting...", displayName());
        super.start();
    }

    @Override
    public boolean stop(final long timeout, final TimeUnit timeUnit) {
        setStopping();
        super.stop(timeout, timeUnit, false);
        LOGGER.trace("AsyncLoggerConfig[{}] stopping...", displayName());
        setStopped();
        return true;
    }

    /**
     * Creates and returns a new {@code RingBufferAdmin} that instruments the
     * ringbuffer of this {@code AsyncLoggerConfig}.
     *
     * @param contextName name of the {@code LoggerContext}
     * @return a new {@code RingBufferAdmin} that instruments the ringbuffer
     */
    public RingBufferAdmin createRingBufferAdmin(final String contextName) {
        return delegate.createRingBufferAdmin(contextName, getName());
    }

    /**
     * Factory method to create a LoggerConfig.
     *
     * @param additivity True if additive, false otherwise.
     * @param levelName The Level to be associated with the Logger.
     * @param loggerName The name of the Logger.
     * @param includeLocation "true" if location should be passed downstream
     * @param refs An array of Appender names.
     * @param properties Properties to pass to the Logger.
     * @param config The Configuration.
     * @param filter A Filter.
     * @return A new LoggerConfig.
     */
    @PluginFactory
    public static LoggerConfig createLogger(
            @PluginAttribute("additivity") final String additivity,
            @PluginAttribute("level") final String levelName,
            @PluginAttribute("name") final String loggerName,
            @PluginAttribute("includeLocation") final String includeLocation,
            @PluginElement("AppenderRef") final AppenderRef[] refs,
            @PluginElement("Properties") final Property[] properties,
            @PluginConfiguration final Configuration config,
            @PluginElement("Filter") final Filter filter) {
        if (loggerName == null) {
            LOGGER.error("Loggers cannot be configured without a name");
            return null;
        }

        final List<AppenderRef> appenderRefs = Arrays.asList(refs);
        Level level;
        try {
            level = Level.toLevel(levelName, Level.ERROR);
        } catch (final Exception ex) {
            LOGGER.error(
                    "Invalid Log level specified: {}. Defaulting to Error",
                    levelName);
            level = Level.ERROR;
        }
        final String name = loggerName.equals(LoggerConfig.ROOT) ? Strings.EMPTY : loggerName;
        final boolean additive = Booleans.parseBoolean(additivity, true);

        return new AsyncLoggerConfig(name, appenderRefs, filter, level,
                additive, properties, config, includeLocation(includeLocation));
    }

    // Note: for asynchronous loggers, includeLocation default is FALSE
    protected static boolean includeLocation(final String includeLocationConfigValue) {
        return Boolean.parseBoolean(includeLocationConfigValue);
    }

    /**
     * An asynchronous root Logger.
     */
    @Plugin(name = "asyncRoot", category = Core.CATEGORY_NAME, printObject = true)
    public static class RootLogger extends LoggerConfig {

        @PluginFactory
        public static LoggerConfig createLogger(
                @PluginAttribute("additivity") final String additivity,
                @PluginAttribute("level") final String levelName,
                @PluginAttribute("includeLocation") final String includeLocation,
                @PluginElement("AppenderRef") final AppenderRef[] refs,
                @PluginElement("Properties") final Property[] properties,
                @PluginConfiguration final Configuration config,
                @PluginElement("Filter") final Filter filter) {
            final List<AppenderRef> appenderRefs = Arrays.asList(refs);
            Level level;
            try {
                level = Level.toLevel(levelName, Level.ERROR);
            } catch (final Exception ex) {
                LOGGER.error(
                        "Invalid Log level specified: {}. Defaulting to Error",
                        levelName);
                level = Level.ERROR;
            }
            final boolean additive = Booleans.parseBoolean(additivity, true);

            return new AsyncLoggerConfig(LogManager.ROOT_LOGGER_NAME,
                    appenderRefs, filter, level, additive, properties, config,
                    AsyncLoggerConfig.includeLocation(includeLocation));
        }
    }
}
