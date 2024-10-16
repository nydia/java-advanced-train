package com.nydia.agent.core;

import com.nydia.agent.core.enhance.AbstractMethodInterceptor;
import com.nydia.agent.core.enhance.BuilderDecorator;
import com.nydia.agent.core.enhance.Interceptor;
import com.nydia.agent.core.enhance.TypeMatcher;
import com.nydia.agent.log4j.LogManager;
import com.nydia.agent.log4j.Logger;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.Default;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.MethodGraph.Compiler.ForDeclaredMethods;
import net.bytebuddy.utility.JavaModule;
import org.reflections.Reflections;

/**
 * @Author: foolwc
 * @Date: 2019/8/7 13:40
 */
public class AgentLauncher {

    private static final Logger LOG = LogManager.getLogger(AgentLauncher.class);

    public static void premain(String args, Instrumentation inst) throws Exception {
        initConfigAndVariables();
        decorateInst(inst);
        LOG.info("Agent started!");
    }

    private static void decorateInst(Instrumentation inst) {
        String prefix = AgentLauncher.class.getPackage().getName();
        Reflections reflections = new Reflections(prefix);
        Set<Class<? extends AbstractMethodInterceptor>> itcptSet = reflections
                .getSubTypesOf(AbstractMethodInterceptor.class);
        List<Interceptor> itrcpts = new ArrayList<>();
        for (Class<? extends AbstractMethodInterceptor> itcpt : itcptSet) {
            try {
                LOG.info("initializing intercepter {}.", itcpt.getName());
                itrcpts.add(itcpt.newInstance());
            } catch (Exception e) {
                LOG.error("initialize {} failed!", itcpt.getName(), e);
            }
        }

        InterceptorChainFactoryUtils.INTERCEPTORS = Collections.unmodifiableList(itrcpts);
        InterceptorChainFactoryUtils.METHOD.addInterceptors(itrcpts);
        InterceptorChainFactoryUtils.CONSTRUCTOR.addInterceptors(itrcpts);

        new Default()
                .ignore(TypeMatcher.INSTANCE.ignoreRule())
                .type(TypeMatcher.INSTANCE.passRule(itrcpts))
                .transform((builder, typeDescription, classLoader, module) -> {
                    //decorate builder.
                    return BuilderDecorator.INSTANCE.decorate(builder, typeDescription, classLoader, module);
                })
                .with(new ByteBuddy().with(ForDeclaredMethods.INSTANCE))
                .with(new DefaultAgentListener())
                .installOn(inst);
    }

    /**
     * 获取程序需要的各种参数
     */
    private static void initConfigAndVariables() {
    }

    static class DefaultAgentListener implements AgentBuilder.Listener {

        @Override
        public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule javaModule, boolean b) {
        }

        public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                boolean loaded, DynamicType dynamicType) {
        }

        public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module,
                boolean loaded) {
        }

        public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded,
                Throwable throwable) {
            LOG.error("{} load {} error.", classLoader, typeName, throwable);
        }

        public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
        }
    }
}
