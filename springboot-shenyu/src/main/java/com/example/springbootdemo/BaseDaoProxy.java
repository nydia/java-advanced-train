package com.example.springbootdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * dao的动态代理类
 *
 * @param <T> dao
 */
public class BaseDaoProxy<T> implements InvocationHandler {
    //dao的class
    private Class<T> interfaceClass;

    public Object bind(Class<T> cls) {
        this.interfaceClass = cls;
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{interfaceClass}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        IBaseDao dao = new BaseDaoImpl();
        //通过反射获取当前执行的方法对应的BaseDaoImpl方法
        Method baseDaoMethod = dao.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        //执行反射获取的BaseDaoImpl方法
        return baseDaoMethod.invoke(dao, args);



//        Class<?> dao = Class.forName(proxy.getClass().getName());
//        //通过反射获取当前执行的方法对应的BaseDaoImpl方法
//        Method baseDaoMethod = dao.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
//        //执行反射获取的BaseDaoImpl方法
//        return baseDaoMethod.invoke(dao, args);



//        System.out.println(111);

//        return null;
    }
}