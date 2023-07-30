package com.nydia.mybatis.test;

import com.nydia.mybatis.DemoApplication;
import com.nydia.mybatis.entity.User;
import com.nydia.mybatis.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
		int cnt  = 100;
		while (cnt -- > 0){
			User user = userMapper.selectById(3);
			System.out.println(user.getUsername());
		}

	}

}


