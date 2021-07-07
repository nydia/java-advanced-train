package io.kimmking.rpcfx.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * AOP
 */
@Component
@Aspect  //标识为一个切面供容器读取
public class ServiceHelper {

    @Pointcut("execution(* io.kimmking.rpcfx.demo.api.*.*(..))")
    public void p(){}

    @Before("p()")
    public void beforeLog(ProceedingJoinPoint joinpoint) {
        System.out.println("开始执行前置通知  日志记录:"+id);
    }



}
