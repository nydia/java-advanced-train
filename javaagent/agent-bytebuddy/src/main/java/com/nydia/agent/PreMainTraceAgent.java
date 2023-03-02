package com.nydia.agent;

import com.nydia.agent.core.PluginFinder;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @Auther: hqlv
 * @Date: 2022/9/5 23:41
 * @Description:
 */
@Slf4j
public class PreMainTraceAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        log.info("agentArgs: " + agentArgs);
        //inst.addTransformer(new DefineTransformer(), true);

        try {
            PluginFinder pluginFinder = new PluginFinder();
            // 拦截spring controller
            AgentBuilder.Identified.Extendable agentBuilder = new AgentBuilder.Default()
                    .type(pluginFinder.buildMatch())
                    .transform(new Transformer(pluginFinder));
            // 装载到 instrumentation 上
            agentBuilder.installOn(inst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Transformer implements AgentBuilder.Transformer {
        private PluginFinder pluginFinder;

        Transformer(PluginFinder pluginFinder) {
            this.pluginFinder = pluginFinder;
        }

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
            // 拦截 @RestMapping 或者 @Get/Post/Put/DeleteMapping
            DynamicType.Builder<?> newBuilder = builder;
            return newBuilder.method(ElementMatchers.isPublic().and(ElementMatchers.isAnnotatedWith(
                    ElementMatchers.nameStartsWith("org.springframework.web.bind.annotation")
                            .and(ElementMatchers.nameEndsWith("Mapping")))))
                    // 拦截后交给 SpringControllerInterceptor 处理
                    .intercept(MethodDelegation.to(LogInterceptor.class));
        }
    }


}
