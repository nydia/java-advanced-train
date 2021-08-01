package io.kimmking.kmq.core.v2;

import io.kimmking.kmq.core.KmqMessage;

import java.util.LinkedList;

/**
 * @Auther: hqlv
 * @Date: 2021/8/1 21:23
 * @Description:
 */
public class MessageQueue {

    private MessageBuffer messageBuffer;

    private LinkedList<KmqMessage<String>> list = new LinkedList<>();

    private int size;

    private int position;

    private Object lock = new Object();

    public void add(KmqMessage message){
        int delta = message.getSize();
        int newSize = 0;
        synchronized (lock){
            list.add(message);
            size +=  delta;
            newSize = size;
        }
    }



}
