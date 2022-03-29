package com.example.springbootdemo;

public class MainTest {
 
	public static void main(String[] args) throws Exception {
		BaseDaoProxyFactory<UserTestDao> factory = new BaseDaoProxyFactory<UserTestDao>();
		factory.setInterfaceClass(UserTestDao.class);
		UserTestDao testDao = factory.getObject();
		testDao.delete(1l);
		testDao.get(3l);
	}
}