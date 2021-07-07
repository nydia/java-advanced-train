package io.kimmking.rpcfx.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * AOP
 */
@Component
@Aspect  //标识为一个切面供容器读取
public class RpcfxAspect {

    private Logger logger = LoggerFactory.getLogger(RpcfxAspect.class);

    @Pointcut("execution(* io.kimmking.rpcfx.demo.api.*.*(..))")
    public void pointCut() {
    }

    @After("pointCut()")
    public void after(JoinPoint jp) {
        logger.info("【注解：After】方法最后执行.....");
    }

}
