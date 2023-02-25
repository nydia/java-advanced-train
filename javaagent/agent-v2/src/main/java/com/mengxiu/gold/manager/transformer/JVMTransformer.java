package com.mengxiu.gold.manager.transformer;

import com.mengxiu.gold.manager.func.JVMMetric;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2023/1/12 23:35
 * @Description: JVMTransformer
 */
public class JVMTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        JVMMetric.printMemoryInfo();
        JVMMetric.printGCInfo();

        return null;
    }
}
