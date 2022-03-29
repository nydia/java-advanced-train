package com.example.springbootdemo;

/**
 * 模拟基础dao接口
 *
 */
public interface IBaseDao {
 
	/**
 * 模拟查询
 * @param id
 */
	void get(Long id);

	/**
	 * 模拟删除
	 * @param id
	 */
	void delete(Long id);
}