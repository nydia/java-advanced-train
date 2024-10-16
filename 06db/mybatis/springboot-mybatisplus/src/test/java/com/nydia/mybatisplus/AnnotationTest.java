package com.nydia.mybatisplus;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.nydia.mybatisplus.entity.User;
import com.nydia.mybatisplus.enums.SexEnum;
import com.nydia.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

//mybatis plus 的注解测试

@SpringBootTest
public class AnnotationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_select() {
        System.setProperty("mpw.key", "d1104d7c3b616f0b");
        
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

    @Test
    public void teset_insert() {
        System.out.println(("----- insert method test ------"));
        User user = new User();
        //user.setId(1L); // 默认的id生成策略是雪花算法
        user.setAge(1);
        user.setName("2024test");
        user.setEmail("nydia_lhq@hotmail.com");
        user.setOrgIds(Arrays.asList("11,12"));
        user.setDelF("0");
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

    //更新数据： @TableField注解添加update属性会自动添加跟新时间
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

    @Test
    public void test_TableField_FieldStrategy() {
        System.out.println(("----- FieldStrategy method test ------"));
        User user = new User();
        user.setAge(1);
        user.setName("2024test");
        //user.setEmail("nydia_lhq@hotmail.com");//设置成控制，sql语句没有了email
        user.setOrgIds(Arrays.asList("11,12"));
        user.setDelF("0");
        int result = userMapper.insert(user);
        Assert.isTrue(1 == result, "插入错误");
        System.out.println(result);
        System.out.println("插入结果====>" + user);

    }

    @Test
    public void test_TableField_fill() {
        System.out.println(("----- fill method test ------"));
        User user = new User();
        user.setAge(1);
        user.setName("2024test");
        user.setEmail("nydia_lhq@hotmail.com");
        user.setOrgIds(Arrays.asList("11,12"));
        user.setDelF("0");
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
    public void test_TableField_numericScale() {
        System.out.println(("----- numericScale method test ------"));
        User user = new User();
        user.setAge(1);
        user.setName("2024test");
        user.setEmail("nydia_lhq@hotmail.com");
        user.setDelF("0");
        user.setOrgIds(Arrays.asList("11,12"));
        user.setAmount(new BigDecimal(1.2222));
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
    public void test_version() {
        System.out.println(("----- @Version test ------"));

        //下面这个方式不支持，支持updateById
//        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("id", 1).set("name", "2024test");
//        int result = userMapper.update(updateWrapper);
//        System.out.println("更新结果====>" + result);

        //查询插入结果
        User updateUser = userMapper.selectOneById(1L);
        System.out.println(updateUser);
        updateUser.setName("test11111");
        int result = userMapper.updateById(updateUser);
        System.out.println("更新结果====>" + result);

        //查询插入结果
        updateUser = userMapper.selectOneById(1L);
        System.out.println(updateUser);
    }

    @Test
    public void test_EnumValue() {
        System.out.println(("----- @EnumValue test ------"));

        User user = new User();
        user.setAge(1);
        user.setName("2024test");
        user.setEmail("nydia_lhq@hotmail.com");
        user.setOrgIds(Arrays.asList("11,12"));
        user.setAmount(new BigDecimal(1.2222));
        user.setSex(SexEnum.WOMEN);
        user.setDelF("0");
        int result = userMapper.insert(user);
        Assert.isTrue(1 == result, "插入错误");
        System.out.println(result);
        System.out.println("插入结果====>" + user);

        //查询插入结果
        List<User> userList = new LambdaQueryChainWrapper<User>(userMapper)
                .eq(User::getName, "2024test").list();
        userList.forEach(System.out::println);

    }

    @Test
    public void test_TableLogic() {
        System.out.println(("----- @TableLogic test ------"));

        //没有  @TableLogic(value="0",delval="1") sql为 DELETE FROM `user` WHERE id=?
        //有  @TableLogic(value="0",delval="1") sql为 UPDATE `user` SET del_f='1' WHERE id=? AND del_f='0'
        int result1 = userMapper.deleteById(1);
        System.out.println("删除结果result1 : " + result1);

        int result2 = userMapper.delete(new QueryWrapper<User>().eq("id", 2));
        System.out.println("删除结果result2 : " + result2);

    }

    @Test
    public void test_KeySequence() {
        System.out.println(("----- @KeySequence test ------"));

        User user = new User();
        //user.setId(10L);
        user.setAge(1);
        user.setName("2024test");
        user.setEmail("nydia_lhq@hotmail.com");
        user.setOrgIds(Arrays.asList("11,12"));
        user.setAmount(new BigDecimal(1.2222));
        user.setSex(SexEnum.WOMEN);
        user.setDelF("0");
        int result = userMapper.insert(user);
        Assert.isTrue(1 == result, "插入错误");
        System.out.println(result);
        System.out.println("插入结果====>" + user);

        //查询插入结果
        List<User> userList = new LambdaQueryChainWrapper<User>(userMapper)
                .eq(User::getName, "2024test").list();
        userList.forEach(System.out::println);

    }

    @Test
    public void test_OrderBy() {
        System.out.println(("----- @OrderBy method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}