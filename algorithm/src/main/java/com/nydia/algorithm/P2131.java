package com.nydia.algorithm;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2022/12/7 22:44
 * @Description: P2131
 */
public class P2131 {

    public static void main(String[] args) {

    }

    public static ListNode deleteNode(ListNode head, int val) {
        if (head.val == val) return head.next;
        ListNode pre = head, cur = head.next;
        while (cur != null && cur.val != val) {
            pre = cur;
            cur = cur.next;
        }
        if (cur != null) pre.next = cur.next;
        return head;
    }

}