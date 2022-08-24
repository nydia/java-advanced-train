package com.nydia.algorithm.p2;

import com.google.gson.Gson;

/**
 * Copyright (C) 2020 ShangHai IPS Information Technology Co.,Ltd.
 * <p>
 * All right reserved.
 * <p>
 * This software is the confidential and proprietary information of IPS
 * Company of China. ("Confidential Information"). You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the contract agreement you entered into with IPS inc.
 * <p>
 *
 * @Description:
 * @ClassName: AddTwoNumbers
 * @Auther: Nydia.LHQ
 * @Date: 2020/11/4 12:29
 */
public class AddTwoNumbers {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(4, new ListNode(5, new ListNode(9)));
        ListNode l2 = new ListNode(1, new ListNode(4, new ListNode(2)));
        ListNode result = addTwoNumbers(l1, l2);
        System.out.println(new Gson().toJson(result));
    }
}
