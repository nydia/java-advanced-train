package com.nydia.algorithm.p2588.a1;

import java.util.Stack;

//2588
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