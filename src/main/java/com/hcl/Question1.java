package com.hcl;

public class Question1 {
	class Node {
	    int key;
	    Node left, right;
	    public Node(int item) {
	        key = item;
	        left = right = null;
	    }
	}
	static Node root;
	Question1() {  
	    root = null;  
	} 
	
	void insert(int key) { 
	   root = insertRec(root, key); 
	}
	
	Node insertRec(Node root, int key) {
	    if (root == null) {
	        root = new Node(key);
	        return root;
	    }
	    if (key < root.key)
	        root.left = insertRec(root.left, key);
	    else if (key > root.key)
	        root.right = insertRec(root.right, key);

	    return root;
	} 
	
	void traverseLeaves(Node root)  {
		if(root== null)
			return;
		if(root.right==null && root.left==null) {
			System.out.print(root.key+" ");
		}
		traverseLeaves(root.right);
		traverseLeaves(root.left);
	}

	public static void main(String[] args) {
		Question1 tree = new Question1();
	
	    tree.insert(8);
	    tree.insert(3);
	    tree.insert(10);
	    tree.insert(1);
	    tree.insert(6);
	    tree.insert(4);
	    tree.insert(7);
	    tree.insert(14);
	    tree.insert(13);
	
	    tree.traverseLeaves(root);
	}
}
