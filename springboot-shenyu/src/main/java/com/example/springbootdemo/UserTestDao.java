package com.example.springbootdemo;

//public interface UserTestDao extends IBaseDao {
//
//}


public interface UserTestDao {
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

