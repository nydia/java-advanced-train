package com.izhengyin.dddmessage.test.domain.aggregate.message;

import com.izhengyin.dddmessage.domain.aggregate.message.entity.Message;
import com.izhengyin.dddmessage.domain.aggregate.message.entity.valueobject.Content;
import com.izhengyin.dddmessage.domain.aggregate.message.entity.valueobject.User;
import com.izhengyin.dddmessage.domain.shared.enums.MessageCategory;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

/**
 * @author zhengyin
 * Created on 2021/7/23
 */
public class MessageTest {

    @Test
    public void newMessage() {
        long messageId = System.currentTimeMillis();
        User sender = new User(1, "nickname1", "photo1");
        User receiver = new User(2, "nickname2", "photo2");
        Content content = new Content(MessageCategory.CHAT,"xxx");
        Date sendTime = new Date();
        Message message = new Message(messageId, MessageCategory.CHAT, sender, receiver, content, sendTime);
        //正常情况
        Assert.assertEquals(messageId, message.getMessageId());
        Assert.assertTrue(sender.sameValueAs(message.getSender()));
        Assert.assertTrue(receiver.sameValueAs(message.getReceiver()));
        Assert.assertEquals(content, message.getContent());
        Assert.assertEquals(sendTime, message.getSendTime());
        //异常测试
        Assert.assertThrows(IllegalArgumentException.class, () -> new Message(0, MessageCategory.CHAT, sender, sender, content, sendTime));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Message(messageId,MessageCategory.CHAT,  new User(0, "nickname1", "photo1"), receiver, content, sendTime));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Message(messageId, MessageCategory.CHAT, sender, new User(0, "nickname2", "photo2"), content, sendTime));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Message(messageId, MessageCategory.CHAT, sender, receiver, new Content(MessageCategory.CHAT,""), sendTime));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Message(messageId, MessageCategory.CHAT, sender, sender, content, sendTime));
    }

    @Test
    public void sameIdentityAs() {
        long messageId = System.currentTimeMillis();
        User sender = new User(1, "nickname1", "photo1");
        User receiver = new User(2, "nickname2", "photo2");
        Content content = new Content(MessageCategory.CHAT,"xxx");
        Date sendTime = new Date();
        Message message = new Message(messageId, MessageCategory.CHAT, sender, receiver, content, sendTime);
        Assert.assertTrue(message.sameIdentityAs(new Message(messageId,  MessageCategory.CHAT,sender, receiver, new Content(MessageCategory.CHAT,content + "/" + UUID.randomUUID()), sendTime)));
    }
}
