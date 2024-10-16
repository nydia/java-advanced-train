package com.nydia.mybatis.test.session;

import com.nydia.mybatis.entity.User;
import com.nydia.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2023/7/28 00:16
 * @Description: SqlSessionTest
 */
public class SqlSessionTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        //注意此处路径不要写错
        String resource = "config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象
     * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码：
     * 1）、根据全局配置文件得到SqlSessionFactory；
     * 2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 一个sqlSession就是代表和数据库的一次会话，用完关闭
     * 3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     *
     * @throws IOException
     */
    @Test
    public void selectMap() throws IOException {
        //1、获取SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、打开一个会话
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        try {
            //第一种方式: 获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            UserMapper mapper = openSession.getMapper(UserMapper.class);
            User user = mapper.selectById(306);
            System.out.println(user);

            //第二种方式,直接查询
            //分页
            RowBounds rowBounds = new RowBounds(0,10);
            //statement必须要待全路径,不然相同的statement会报非唯一性错误
            //Map<String, User> map = openSession.selectMap("selectByNameForMap","name3","username", rowBounds);
            Map<String, User> map = openSession.selectMap("com.nydia.mybatis.mapper.UserMapper.selectByNameForMap","name3","username", rowBounds);
            System.out.println(map);

        } finally {
            //4、使用完毕后关闭会话
            openSession.close();
        }
    }

}
