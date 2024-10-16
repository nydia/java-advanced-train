package main

type ListNode struct {
	Val  int
	Next *ListNode
}

func deleteNode(head *ListNode, val int) *ListNode {
	if head == nil {
		return nil
	}
	if head.Val == val {
		return head.Next
	}
	pre := head
	cur := head.Next
	for cur != nil && cur.Val != val {
		pre = cur
		cur = cur.Next
	}
	if cur != nil {
		pre.Next = cur.Next
	}
	return head
}
