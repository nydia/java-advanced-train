package com.example.springbootdemo;

import com.mpush.api.spi.common.MQClient;
import com.mpush.api.spi.common.MQClientFactory;
import com.mpush.api.spi.common.MQMessageReceiver;
import com.mpush.bootstrap.ServerLauncher;
import com.mpush.core.MPushServer;
import com.mpush.core.push.PushCenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {

	@Test
	public void testPush() {
		ServerLauncher serverLauncher = new ServerLauncher();
		MPushServer mPushServer = serverLauncher.getMPushServer();
		PushCenter pushCenter = mPushServer.getPushCenter();

		//IPushMessage message = null;
		pushCenter.push(null);
	}

	@Test
	public void testPublish(){
		MQClient mqClient = MQClientFactory.create();
		//MQMessageReceiver mqMessageReceiver = new Biz
		mqClient.subscribe("bizMessage", null);
	}

}
