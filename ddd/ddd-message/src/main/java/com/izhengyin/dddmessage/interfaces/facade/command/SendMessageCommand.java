package com.izhengyin.dddmessage.interfaces.facade.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhengyin
 * Created on 2021/7/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageCommand {
    private long messageId;
    private int catId;
    private int sender;
    private int receiver;
    private String content;

    @Override
    public String toString() {
        return "SendMessageCommand{" +
                "messageId=" + messageId +
                ", catId=" + catId +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                '}';
    }
}
