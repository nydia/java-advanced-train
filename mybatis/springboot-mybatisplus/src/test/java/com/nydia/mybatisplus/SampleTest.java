package com.nydia.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

    @Test
    public void tesetInsert() {
        System.out.println(("----- insert method test ------"));
        User user = new User();
        //user.setId(1L); // 默认的id生成策略是雪花算法
        user.setAge(1);
        user.setName("2024test");
        user.setEmail("nydia_lhq@hotmail.com");
        user.setOrgIds(Arrays.asList("11,12"));
        int result = userMapper.insert(user);
        Assert.isTrue(1 == result, "插入错误");
        System.out.println(result);
        System.out.println("插入结果====>" + user);

        //查询插入结果
        User userQuery = new User();
        userQuery.setName("2024test");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void test_TableField_TypeHandler() {
        //使用 mybatis plus 封装的方法可以获取到orgIds
        System.out.println(("----- select one method test ------"));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 1);
        //测试 ： @TableField(value = "org_ids", typeHandler = ListTypeHandler.class)
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void test_TableName_resultMap() {
        //使用自定义的方法获取不到 orgIds, typeHandle不起作用
        //解决方案： 在Mapper的selectOneById上面增加 @ResultMap("mybatis-plus_User")
        System.out.println(("----- select one method test ------"));
        User user = userMapper.selectOneById(1L);
        System.out.println(user);
    }

    @Test
    public void test_TableField_condition() {
        System.out.println(("----- selectMaps method test ------"));
        User user = new User();
        user.setName("a");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        //测试 ： @TableField(value = "name", condition = SqlCondition.LIKE)
        // 输出 SQL 为：select 表 where name LIKE CONCAT('%',值,'%')
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void test_TableField_update() {
        System.out.println(("----- update method test ------"));
        User user = new User();
        user.setId(1L);
        user.setName("2024test");
        int result = userMapper.updateById(user);
        Assert.isTrue(1 == result, "更新错误");
        System.out.println(result);
        System.out.println("插入结果====>" + user);

        //查询更新结果: 可以看到TableField的update属性生效，updateTime有值了。
        User userQuery = new User();
        userQuery.setName("2024test");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

}