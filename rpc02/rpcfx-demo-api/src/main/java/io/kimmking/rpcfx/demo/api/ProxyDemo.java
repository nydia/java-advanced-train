package io.kimmking.rpcfx.demo.api;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther: hqlv
 * @Date: 2021/7/7 23:55
 * @Description:
 */
public class ProxyDemo {

    public static void main(String[] args) {

        UserService userService = (UserService) Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(), UserService.class.getInterfaces(), new RpcfxInvocationHandler());

        System.out.println("nihao");

    }


    public static class RpcfxInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {

            System.out.println("nihao");

            return "seuccess";
        }


    }
}
