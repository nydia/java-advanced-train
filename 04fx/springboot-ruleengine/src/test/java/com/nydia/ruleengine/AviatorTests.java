package com.nydia.ruleengine;

import com.googlecode.aviator.AviatorEvaluator;
import com.nydia.ruleengine.aviator.utils.AviatorUtil;
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

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
class AviatorTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void aviator() {
		for(int i = 0; i < 1000; i ++){
			AviatorUtil.execute();
		}
	}

	/**
	 * 算数表达式
	 */
	@Test
	public void test1() {
		Long sum = (Long) AviatorEvaluator.execute("1 + 2 + 3");
		System.out.println(sum);
	}
	/**
	 * 逻辑表达式
	 */
	@Test
	public void test2() {
		Boolean result = (Boolean)AviatorEvaluator.execute("3 > 1 && 2 != 4 || true");
		System.out.println(result);
	}
	/**
	 * 往表达式传入值
	 */
	@Test
	public void test3() {
		Map<String, Object> env = new HashMap<>();
		env.put("name", "ruilin.shao");
		String str = "'hello ' + name";
		String result = (String) AviatorEvaluator.execute(str, env);
		System.out.println(result);
		//写法二
		String result2 = (String)AviatorEvaluator.exec(str, "便利蜂");
		System.out.println(result2);
	}
	/**
	 * 三元表达式
	 */
	@Test
	public void test4() {
		String result = (String)AviatorEvaluator.execute("3 > 0 ? yes : no");
		System.out.println(result);
	}
	/**
	 * 函数调用
	 */
	@Test
	public void test5() {
		System.out.println("string.length('hello') = " + AviatorEvaluator.execute("string.length('hello')"));//求字符串长度,不能用String.length();
		System.out.println("string.contains('hello', 'h') = " + AviatorEvaluator.execute("string.contains('hello', 'h')"));//判断字符串中是否包含某个字符串
		System.out.println("math.pow(-3, 2) = " + AviatorEvaluator.execute("math.pow(-3, 2)"));
		System.out.println("math.sqrt(9.0) = " + AviatorEvaluator.execute("math.sqrt(9.0)"));
	}

}
