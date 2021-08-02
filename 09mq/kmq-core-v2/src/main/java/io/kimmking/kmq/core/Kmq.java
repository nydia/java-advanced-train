package io.kimmking.kmq.core;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class Kmq {

    public Kmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue(capacity);
    }

    private String topic;

    private int capacity;

    private LinkedBlockingQueue<KmqMessage> queue;

    private ArrayList<KmqMessage> list;

    private int position;

    public boolean send(KmqMessage message) {
        //return queue.offer(message);
        return list.add(message);
    }

    public KmqMessage poll(int index) {
//        return queue.poll();
        return list.remove(index);
    }

    @SneakyThrows
    public KmqMessage poll(long timeout) {
        return queue.poll(timeout, TimeUnit.MILLISECONDS);
//        return list.removeIf()
    }

}
