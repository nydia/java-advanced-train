package io.kimmking.rpcfx.demo.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther: hqlv
 * @Date: 2021/7/7 23:55
 * @Description: java动态代理
 */
public class ProxyDemo {

    public static void main(String[] args) {

        RpcfxInvocationHandler<ProxyDemoInterface> handler = new RpcfxInvocationHandler(ProxyDemoInterface.class);
        ProxyDemoInterface proxy = handler.getProxy();
        System.out.println("代理类的全限定名称：" + proxy.getClass().getName());
        proxy.proxyMetho(1);
        System.out.println("nihao");

    }

    public static class RpcfxInvocationHandler<T> implements InvocationHandler {
        private Class<T> proxyInterface;
        RpcfxInvocationHandler(Class<T> proxyInterface){
            this.proxyInterface = proxyInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
            System.out.println("执行invoke");
            return "success";
        }

        public T getProxy(){
            return (T)Proxy.newProxyInstance(proxyInterface.getClassLoader(), new Class[]{proxyInterface}, this);
        }

    }
}
