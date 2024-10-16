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

package com.nydia.agent.log4j.core.lookup;


import com.nydia.agent.log4j.Marker;
import com.nydia.agent.log4j.MarkerManager;
import com.nydia.agent.log4j.core.LogEvent;
import com.nydia.agent.log4j.core.config.plugins.Plugin;

import com.nydia.agent.log4j.Marker;
import com.nydia.agent.log4j.MarkerManager;
import com.nydia.agent.log4j.Marker;
import com.nydia.agent.log4j.MarkerManager;

/**
 * Looks-up markers.
 * 
 * @since 2.4
 */
@Plugin(name = "marker", category = StrLookup.CATEGORY)
public class MarkerLookup extends AbstractLookup {

    static final String MARKER = "marker";

    @Override
    public String lookup(final LogEvent event, final String key) {
        final Marker marker = event == null ? null : event.getMarker();
        return marker == null ? null : marker.getName();
    }

    @Override
    public String lookup(final String key) {
        return MarkerManager.exists(key) ? key : null;
    }

}
