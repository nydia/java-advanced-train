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

package com.nydia.agent.log4j.core.appender;

import java.util.Objects;
import com.nydia.agent.log4j.core.Layout;
import com.nydia.agent.log4j.core.LogEvent;
import com.nydia.agent.log4j.core.LoggerContext;
import com.nydia.agent.log4j.core.config.Configuration;

public abstract class HttpManager extends AbstractManager {
    private final Configuration configuration;

    protected HttpManager(final Configuration configuration, final LoggerContext loggerContext, final String name) {
        super(loggerContext, name);
        this.configuration = Objects.requireNonNull(configuration);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void startup() {
        // This default implementation does nothing
    }

    public abstract void send(Layout<?> layout, LogEvent event) throws Exception;
}
