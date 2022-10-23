package com.example.springbootdemo.dynamic_proxy;

/**
 * 模拟基础dao实现
 * @author longmap
 *
 */
public class BaseDaoImpl implements IBaseDao {
 
	@Override
	public void get(Long id) {
		//你自己的数据库操作方法
		System.out.println("===========DB get()执行===========");
	}
 
	@Override
	public void delete(Long id) {
		System.out.println("===========DB delete()执行===========");
	}
}