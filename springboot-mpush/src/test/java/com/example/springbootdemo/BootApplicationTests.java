package com.example.springbootdemo;

import com.mpush.api.spi.common.MQClient;
import com.mpush.api.spi.common.MQClientFactory;
import com.mpush.api.spi.common.MQMessageReceiver;
import com.mpush.bootstrap.ServerLauncher;
import com.mpush.common.message.BizMessage;
import com.mpush.core.MPushServer;
import com.mpush.core.push.PushCenter;
import com.mpush.core.router.BizMessageListener;
import com.mpush.core.router.RouterChangeListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class BootApplicationTests {

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
		MQMessageReceiver mqMessageReceiver = new BizMessageListener();

		String topic = "bizMessage";
		mqClient.subscribe("bizMessage", mqMessageReceiver);

		BizMessage bizMessage = new BizMessage();
		bizMessage.setContentBytes("i come from spring boot".getBytes());
		bizMessage.setMessageId("1");
		bizMessage.setToUserIds(Collections.singletonList("1"));
		mqClient.publish(topic, bizMessage);
	}

}
