package com.nydia.algorithm;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2022/12/12 22:17
 * @Description: P3366 / Office 22
 */
public class P3368 {

    public ListNode getKthFromEnd(ListNode head, int k) {
        //定义前后指针
        ListNode from = head,latter = head;
        //from先移动k步
        for(int i = 0; i < k; i ++){
            if(from == null)
                return null;
            from = from.next;
        }
        while(from.next != null){
            from = from.next;
            latter = latter.next;
        }
        return latter;
    }
}

