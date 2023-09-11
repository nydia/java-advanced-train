package com.nydia.mybatis.test.annatations.cacheNamespace;

import com.nydia.mybatis.entity.User;
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
public class CacheNamespaceTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        //注意此处路径不要写错
        String resource = "config/annatations/cache-namespace/mybatis-config.xml";
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
    public void queryUseXml() throws IOException {
        //1、获取SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //2、打开一个会话
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        try {
            // 3、获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            CacheNamespaceUseXmlUserMapper mapper = openSession.getMapper(CacheNamespaceUseXmlUserMapper.class);
            User user = mapper.selectById(315);
            System.out.println(user);

        } finally {
            //4、使用完毕后关闭会话
            openSession.close();
        }

        //3、打开另一个会话
        SqlSession openSession2 = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        try {
            // 3、获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            CacheNamespaceUseXmlUserMapper mapper2 = openSession2.getMapper(CacheNamespaceUseXmlUserMapper.class);
            User user2 = mapper2.selectById(315);
            System.out.println(user2);

        } finally {
            //4、使用完毕后关闭会话
            openSession.close();
        }

    }

    @Test
    public void queryUseAnnatation() throws IOException {
        //1、获取SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //2、打开一个会话
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        try {
            // 3、获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            CacheNamespaceUseAnnatationUserMapper mapper = openSession.getMapper(CacheNamespaceUseAnnatationUserMapper.class);
            User user = mapper.selectById(315);
            System.out.println(user);

        } finally {
            //4、使用完毕后关闭会话
            openSession.close();
        }

        //3、打开另一个会话
        SqlSession openSession2 = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        try {
            // 3、获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            CacheNamespaceUseAnnatationUserMapper mapper2 = openSession2.getMapper(CacheNamespaceUseAnnatationUserMapper.class);
            User user2 = mapper2.selectById(315);
            System.out.println(user2);

        } finally {
            //4、使用完毕后关闭会话
            openSession.close();
        }

    }

    //开启 CacheNamespaceUseXmlUserMapper 的二级缓存。
    // CacheNamespaceUseMixedUserMapper 和 CacheNamespaceUseXmlUserMapper共享缓存，这样CacheNamespaceUseXmlUserMapper查完，
    //  CacheNamespaceUseMixedUserMapper就可以共享缓存使用。
    @Test
    public void queryUseMixed() throws IOException {
        //1、获取SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        //2、打开一个会话
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        try {
            // 3、获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            CacheNamespaceUseAnnatationUserMapper mapper = openSession.getMapper(CacheNamespaceUseAnnatationUserMapper.class);
            User user = mapper.selectById(315);
            System.out.println(user);

        } finally {
            //4、使用完毕后关闭会话
            openSession.close();
        }

        //5、打开另一个会话
        SqlSession openSession2 = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        try {
            // 6、获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            CacheNamespaceUseMixedUserMapper mapper2 = openSession2.getMapper(CacheNamespaceUseMixedUserMapper.class);
            User user2 = mapper2.selectById(315);
            System.out.println(user2);

        } finally {
            //7、使用完毕后关闭会话
            openSession.close();
        }

    }


}
