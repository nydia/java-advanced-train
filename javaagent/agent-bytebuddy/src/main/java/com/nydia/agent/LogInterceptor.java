package com.nydia.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class LogInterceptor {

    private static ObjectMapper mapper = new ObjectMapper();

    private static Logger log = LoggerFactory.getLogger(LogInterceptor.class);

    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @AllArguments Object[] args,
                                   @SuperCall Callable<?> callable) {
        long start = System.currentTimeMillis();
        log.info("before controller: {}", method.getName());
        log.info("args: {}", Arrays.toString(args));
        try {
            for (Object arg : args) {
                if (arg instanceof WebRequest) {
                    log.info("arg: {}", mapper.writeValueAsString(((WebRequest) arg).getParameterMap()));
                }
            }
            Object res = callable.call();
            log.info("result: {}", res);
            return res;
        } catch (Exception e) {
            log.error("controller error: ", e);
        } finally {
            long end = System.currentTimeMillis();
            log.info("after controller execute in {} ms", end - start);
        }
        return null;

    }

}