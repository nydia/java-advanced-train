package com.nydia.algorithm.p2588.a2;

import java.util.Deque;
import java.util.LinkedList;

//2588
//使用Deque实现Stack
public class CQueue {

    Deque<Integer> stackIn;
    Deque<Integer> stackOut;
    public CQueue() {
        stackIn = new LinkedList();
        stackOut = new LinkedList();
    }

    public void appendTail(int value) {
        stackIn.push(value);
    }
    
    public int deleteHead() {
        if(stackOut.isEmpty()){
            if(stackIn.isEmpty()){
                return -1;
            }
            while (!stackIn.isEmpty()){
                stackOut.push(stackIn.pop());
            }
        }
        return stackOut.pop();
    }

}