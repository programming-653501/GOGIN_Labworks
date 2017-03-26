package com.company;
/**
 * Created by yuragogin on 26.03.17.
 */
public class BinarySearchTree {
    public Node find(Node root, int x)
    {
        if(root==null)
            return null;
        if(root.key==x)
            return root;
        if(root.key > x){

            return find(root.r, x);
        }
        else{
            return find(root.l, x);
        }
    }
    public Node insert(Node root, int x)
    {
        if(root == null) {
            Node temp =  new Node();
            temp.key = x;
            return temp;
        }

        if(root.key > x){

            root.r = insert(root.r, x);
        }
        if(root.key < x){

            root.l = insert(root.l, x);
        }
        return root;
    }
    public  void printUP(Node x)
    {
        if(x == null) return;
        printUP(x.r);
        System.out.println(x.key);
        printUP(x.l);
    }
    public  void printDown(Node x)
    {
        if(x == null) return;
        printDown(x.l);
        System.out.println(x.key);
        printDown(x.r);
    }
}