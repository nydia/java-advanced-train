package io.kimmking.rpcfx.demo.api;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther: hqlv
 * @Date: 2021/7/8 23:51
 * @Description:
 */
public class ProxySpringAop {
    public static void main(String[] args) {
        // 1.创建代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        // 3.设置代理实现接口
        proxyFactory.setInterfaces(new Class[]{ProxyDemoInterface.class});
        // 4.创建正则表达式切面类
        RegexpMethodPointcutAdvisor advisor = new RegexpMethodPointcutAdvisor();
        //5. 添加环绕增强
        advisor.setAdvice(new BrowserAroundAdvice());
        //6. 设置切入点正则表达式
    }

}
