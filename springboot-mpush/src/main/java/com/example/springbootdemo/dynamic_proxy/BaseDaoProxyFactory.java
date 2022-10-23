package com.example.springbootdemo.dynamic_proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * dao动态代理工厂类
 *
 * @param <T> dao
 */
public class BaseDaoProxyFactory<T> implements FactoryBean<T> {
	//代理dao 接口的class
	private Class<T> interfaceClass;
 
	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}
 
	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		return (T) new BaseDaoProxy<T>().bind(interfaceClass);
	}
 
	@Override
	public Class<?> getObjectType() {
		return interfaceClass;
	}
 
	@Override
	public boolean isSingleton() {
		// 单例模式
		return true;
	}
}