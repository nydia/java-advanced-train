package com.nydia.mybatis.test.cache;

import com.nydia.mybatis.entity.User;
import com.nydia.mybatis.test.annatations.cacheNamespace.CacheNamespaceUseAnnatationUserMapper;
import com.nydia.mybatis.test.annatations.cacheNamespace.CacheNamespaceUseMixedUserMapper;
import com.nydia.mybatis.test.annatations.cacheNamespace.CacheNamespaceUseXmlUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description CacheNamespace Test
 * @Date 2023/9/11 9:32
 * @Created by nydia
 */
public class CacheTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        //注意此处路径不要写错
        String resource = "config/cache/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 一级缓存测试
     *
     * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象
     * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码：
     * 1）、根据全局配置文件得到SqlSessionFactory；
     * 2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 一个sqlSession就是代表和数据库的一次会话，用完暂时不关，用来测试一级缓存
     * 3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     *
     * @throws IOException
     */
    @Test
    public void queryUseXml() throws IOException {
        //1、获取SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //2、打开第一个会话
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        // 获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
        CacheUserMapper mapper = openSession.getMapper(CacheUserMapper.class);
        try {

            User user = mapper.selectById(306);
            System.out.println(user);

        } finally {
            //使用完毕后关闭会话
            //openSession.close();
        }

        //3、使用第一个会话
        try {
//            // 获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
//            CacheUserMapper mapper = openSession.getMapper(CacheUserMapper.class);
            User user = mapper.selectById(306);
            System.out.println(user);

        } finally {
            //使用完毕后关闭会话
            //openSession.close();
        }

        //4、打开第二个会话
        SqlSession openSession2 = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        try {
            // 获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            CacheUserMapper mapper2 = openSession2.getMapper(CacheUserMapper.class);
            User user = mapper2.selectById(306);
            System.out.println(user);

        } finally {
            //使用完毕后关闭会话
            //openSession.close();
        }

    }


}
