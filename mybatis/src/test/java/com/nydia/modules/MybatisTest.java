package com.nydia.modules;

import com.nydia.modules.entity.master.User;
import com.nydia.modules.mapper.master.UserDao;
import com.nydia.modules.service.master.IUserService;
import lombok.SneakyThrows;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class MybatisTest extends SpringContextWebTestSupport {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SqlSessionTemplate materNodeSessionTemplate;

    @Test
    public void forInsert() {
        try {
            long start = System.currentTimeMillis();
            //userService.insertUser(SlaveUser.builder().userName("测试20211029").password("1").nickName("1").idCard("1").build());
            for(int i = 1; i <= 100; i ++){
                userDao.insertUser(User.builder().userName("测试20211029").password("1").nickName("1").idCard("1").build());
            }
            long end = System.currentTimeMillis();
            System.out.println("---------------" + (start - end) + "---------------");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void batchInsert() {
        try {
            long start = System.currentTimeMillis();
            SqlSession sqlSession = materNodeSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            //SqlSession sqlSession = materNodeSessionTemplate.getSqlSessionFactory().openSession();
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            for(int i = 1; i <= 100; i ++){
                mapper.insertUser(User.builder().userName("测试20211029").password("1").nickName("1").idCard("1").build());
            }
            sqlSession.commit();
            long end = System.currentTimeMillis();
            System.out.println("---------------" + (start - end) + "---------------");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}