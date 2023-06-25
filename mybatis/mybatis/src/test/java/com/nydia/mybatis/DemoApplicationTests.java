package com.nydia.mybatis;

import com.nydia.mybatis.entity.User;
import com.nydia.mybatis.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
<<<<<<< HEAD
		int cnt = 100;
		while (cnt-- > 0){
			User user = userMapper.selectById(3);
			System.out.println(user);
=======
		int cnt  = 100;
		while (cnt -- > 0){
			User user = userMapper.selectById(3);
>>>>>>> 07380db41ecf2520e18f9204a7c3a0ea08f7ffee
			System.out.println(user.getUsername());
		}

	}

}
