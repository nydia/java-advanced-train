package com.nydia.algorithm.P2588.a2;

import java.util.Stack;

public class CQueue {

    Stack<Integer> stackIn;
    Stack<Integer> stackOut;
    public CQueue() {
        stackIn = new Stack<>();
        stackOut = new Stack<>();
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