package com.mengxiu.gold.manager.transformer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2023/1/12 23:35
 * @Description: MonitorTransformer
 */
public class MonitorTransformer implements ClassFileTransformer {

    /**
     * 配置哪些包下的类可以被加载
     */
    private String config;
    private ClassPool pool;

    public MonitorTransformer() {
    }

    public MonitorTransformer(String config, ClassPool pool) {
        this.config = config;
        this.pool = pool;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className == null || !className.replaceAll("/", ".").startsWith(this.config)) {
            return null;
        }
        try {
            className = className.replaceAll("/", ".");
            CtClass ctClass = pool.get(className);
            // 这里对类的所有方法进行监控,可以通过config配置具体监控哪些包下的类
            for (CtMethod method : ctClass.getDeclaredMethods()) {
                //TODO
                System.out.println(method.getName());
                //newMethod(method, pool);

            }
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
