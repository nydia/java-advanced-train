package com.nydia.mybatis.test.annatations;

import com.nydia.mybatis.entity.User;
import com.nydia.mybatis.mapper.UserMapper;
import com.nydia.mybatis.test.annatations.cacheNamespace.CacheNamespaceUseAnnatationUserMapper;
import com.nydia.mybatis.test.annatations.cacheNamespace.CacheNamespaceUseXmlUserMapper;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Description Flush Test
 * @Date 2023/9/11 16:40
 * @Created by nydia
 */
public class FlushTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        //注意此处路径不要写错
        String resource = "config/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void flush() throws IOException {
        //1、获取SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、打开一个会话
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            // 3、获取接口的实现类对象，会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            UserMapper mapper = openSession.getMapper(UserMapper.class);
            mapper.insert(User.builder().username("name5").password("123456").build());
            mapper.insert(User.builder().username("name5").password("123456").build());
            mapper.insert(User.builder().username("name5").password("123456").build());
            List<BatchResult> results = mapper.flush();
            openSession.commit();//这里不添加commit，会默认自动回滚
            System.out.println(results);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //4、使用完毕后关闭会话
            openSession.close();
        }

    }


}
