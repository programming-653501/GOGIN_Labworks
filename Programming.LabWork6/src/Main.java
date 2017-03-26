import javafx.scene.Scene;

import java.util.Scanner;

/**
 * Created by yuragogin on 26.03.17.
 */
public class Main {
    public static void main(String args[])
    {
        Node<Integer> root = new Node<Integer>();
        Scanner scanner = new Scanner(System.in);
        int elements_number = scanner.nextInt();
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
        for (int i = 0; i < elements_number; i++)
        {
            int p = scanner.nextInt();
            binarySearchTree.insert(root, p);

        }
        binarySearchTree.print(root);
    }
}
