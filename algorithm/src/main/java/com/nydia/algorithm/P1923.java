package com.nydia.algorithm;

import com.nydia.algorithm.base.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class P1923 {
     private Map<Integer, Integer> indexMap;

     public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_rigth, int inorder_left, int inorder_right){
         if(preorder_left > preorder_rigth){
             return null;
         }
         int preorder_root = preorder_left;
         int inorder_root = indexMap.get(preorder[preorder_root]);
         TreeNode root = new TreeNode(preorder[preorder_root]);
         return null;
     }

     public TreeNode buildTree(int[] preorder, int[] inorder){
         int n = preorder.length;
         indexMap = new HashMap<Integer, Integer>();
         for (int i = 0; i< n; i ++ ){
             indexMap.put(inorder[i], i);//同一个二叉树，节点值一致，顺序不一致，所以以节点为key
         }
         return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
     }


}