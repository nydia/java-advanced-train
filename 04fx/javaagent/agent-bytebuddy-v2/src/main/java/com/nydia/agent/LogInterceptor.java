package com.nydia.agent;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class LogInterceptor {

    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @AllArguments Object[] args,
                                   @SuperCall Callable<?> callable) {
        long start = System.currentTimeMillis();
        try {
            System.out.println("start intercept .....................");
            Object res = callable.call();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;

    }

}