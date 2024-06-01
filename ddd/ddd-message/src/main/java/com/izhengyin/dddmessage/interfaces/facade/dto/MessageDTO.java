package com.izhengyin.dddmessage.interfaces.facade.dto;
import lombok.Data;
import java.util.Date;
@Data
public class MessageDTO {
    private long messageId;
    private int catId;
    private int sender;
    private String senderNickname;
    private String senderPhoto;
    private int receiver;
    private String receiverNickname;
    private String receiverPhoto;
    private int contentTpl;
    private boolean read;
    private String content;
    private Date sendTime;
}
