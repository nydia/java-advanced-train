package com.agent.demo;

import com.agent.demo.transformer.DefineTransformer;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @Auther: hqlv
 * @Date: 2022/9/5 23:41
 * @Description:
 */
public class PreMainTraceAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs: " + agentArgs);
        inst.addTransformer(new DefineTransformer(), true);

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, DynamicType dynamicType) {}

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) { }

            @Override
            public void onError(String typeName, ClassLoader classLoader, JavaModule module, Throwable throwable) { }

            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module) { }
        };

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("com.example.demo")) // 指定需要拦截的类
                .transform(new DefineTransformer())
                .with(listener)
                .installOn(inst);
    }

}
