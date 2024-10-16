package com.agent.demo;

import com.agent.demo.transformer.DefineTransformer;
import com.agent.demo.transformer.MyClassTransformer;

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
        inst.addTransformer(new MyClassTransformer(), true);
    }

}
