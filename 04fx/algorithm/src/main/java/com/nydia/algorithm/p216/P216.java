package com.nydia.algorithm.p216;

import com.nydia.algorithm.base.ListNode;

/**
 * @Auther: hqlv
 * @Date: 2021/11/3 22:44
 * @Description: 反转链表
 */
public class P216 {

    public static void main(String[] args) {

    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        while (current != null){
            ListNode next = current.next;//记录原有链表的下一个节点
            current.next = prev;//将当前节点的下一个指针指向前一个节点，进行反转
            prev = current;
            current = next;
        }
        return prev;
    }
}
