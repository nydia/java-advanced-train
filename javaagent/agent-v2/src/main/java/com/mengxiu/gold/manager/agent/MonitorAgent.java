package com.mengxiu.gold.manager.agent;

import com.mengxiu.gold.manager.transformer.JVMTransformer;
import com.mengxiu.gold.manager.transformer.MonitorTransformer;
import com.mengxiu.gold.manager.transformer.TestTransformer;
import javassist.ClassPool;

import java.lang.instrument.Instrumentation;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2023/1/12 23:59
 * @Description: MonitorAgent
 */
public class MonitorAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("this is an perform monitor agent.");
        System.out.println("before main: testAgent start!" + agentArgs);
        inst.addTransformer(new TestTransformer());
        inst.addTransformer(new JVMTransformer());

        ClassPool pool = ClassPool.getDefault();
        String config = agentArgs;
        inst.addTransformer(new MonitorTransformer(config, pool));

//        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                JVMMetric.printMemoryInfo();
//                JVMMetric.printGCInfo();
//            }
//        }, 0, 5000, TimeUnit.MILLISECONDS);

    }

}
