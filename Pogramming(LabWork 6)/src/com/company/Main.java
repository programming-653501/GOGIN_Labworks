package com.company;

import java.util.Scanner;

/**
 * Created by yuragogin on 26.03.17.
 */
public class Main {
    public static void main(String args[])
    {
        Node root = new Node();
        Scanner scanner = new Scanner(System.in);
        int elements_number = scanner.nextInt();
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        for (int i = 0; i < elements_number; i++)
        {
            int p = scanner.nextInt();
            binarySearchTree.insert(root, p);

        }
        binarySearchTree.printUP(root);
        binarySearchTree.printDown(root);
    }
}
