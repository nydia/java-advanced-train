package com.agent.demo;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

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
            // 拦截spring controller
            AgentBuilder.Identified.Extendable agentBuilder = new AgentBuilder.Default()
                    // 拦截@Controller 和 @RestController的类
                    .type(ElementMatchers.isAnnotatedWith(ElementMatchers.named("org.springframework.stereotype.Controller")
                                    .or(ElementMatchers.named("org.springframework.web.bind.annotation.RestController"))
                            //.or(ElementMatchers.named("org.springframework.stereotype.Service"))
                    ))
                    .transform((builder, typeDescription, classLoader, javaModule) ->
                            // 拦截 @RestMapping 或者 @Get/Post/Put/DeleteMapping
                            builder.method(ElementMatchers.isPublic().and(ElementMatchers.isAnnotatedWith(
                                    ElementMatchers.nameStartsWith("org.springframework.web.bind.annotation")
                                            .and(ElementMatchers.nameEndsWith("Mapping")))))
                                    // 拦截后交给 SpringControllerInterceptor 处理
                                    .intercept(MethodDelegation.to(LogInterceptor.class)));
            // 装载到 instrumentation 上
            agentBuilder.installOn(inst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
