package com.hcl;

import java.util.*; 
class Node 
{ 
    int data; 
    Node left, right; 
    public Node(int item) 
    { 
        data = item; 
        left = right = null; 
    } 
} 
public class Question3 {
  
  
    public static void main(String args[])  
    { 
    	Node root;
        root = new Node(1); 
        root.right = new Node(2); 
        root.right.right = new Node(5); 
        root.right.right.right = new Node(6); 
        root.right.right.left = new Node(3); 
        root.right.right.left.right = new Node(4); 
  
        System.out.println("Breadth First Search"); 
        Queue<Node> queue = new LinkedList<Node>(); 
        queue.add(root); 
        while (!queue.isEmpty())  
        { 
            Node tempNode = queue.poll(); 
            System.out.print(tempNode.data); 
  
            if (tempNode.left != null) { 
                queue.add(tempNode.left); 
            } 
  
            if (tempNode.right != null) { 
                queue.add(tempNode.right); 
            }
            if(!queue.isEmpty()) {
            	System.out.print("->");
            }
        } 
    } 
}
