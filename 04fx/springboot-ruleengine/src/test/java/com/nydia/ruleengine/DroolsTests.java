package com.nydia.ruleengine;

import com.nydia.ruleengine.grools.entity.Cat;
import com.nydia.ruleengine.grools.entity.People;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class DroolsTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private KieSession session;

	@Autowired
	private KieBase kieBase;

	@Test
	public void drools_people_0() {
		for(int i = 0; i < 1000; i ++){
			People people = new People();
			people.setName("达");
			people.setSex(0);
			people.setDrlType("people");
			session.insert(people);//插入
			session.fireAllRules();//执行规则
		}
	}

	@Test
	public void drools_people_1() {
		for(int i = 0; i < 1000; i ++){
			People people = new People();
			people.setName("达");
			people.setSex(1);
			people.setDrlType("people");
			session.insert(people);//插入
			session.fireAllRules();//执行规则
		}
	}

	@Test
	public void cat() {
		Cat cat = new Cat();
		cat.setName("金");
		cat.setSex(1);
		session.insert(cat);//插入
		session.fireAllRules();//执行规则
	}

	@AfterEach
	public void runDispose() {
		session.dispose();//释放资源
	}

}
