/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.nydia.agent.core;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public class PluginFinder {
    private static boolean IS_PLUGIN_INIT_COMPLETED = true;

    public ElementMatcher<? super TypeDescription> buildMatch() {
        // 拦截@Controller 和 @RestController的类
        return ElementMatchers.isAnnotatedWith(//
                ElementMatchers.named("org.springframework.stereotype.Controller")//
                        .or(ElementMatchers.named("org.springframework.web.bind.annotation.RestController"))//
        );
    }

    public static boolean isPluginInitCompleted() {
        return IS_PLUGIN_INIT_COMPLETED;
    }


}
