package com.company;

import java.util.Scanner;

/**
 * Created by yuragogin on 18.04.17.
 */
public class LongDecimal {
    public static void main(String args[]) {
        DoublyLinkedList<Integer> myDeque = new DoublyLinkedList<Integer>();
        myDeque.add(3);
        int N;
        System.out.println("Enter a degree of 3:");
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        int coeff = 0;
        for (int i = 0; i < N-1; i++) {
            for (int j = 0; j < myDeque.size(); j++) {
                myDeque.set(j, myDeque.get(j) * 3 + coeff);
                coeff = myDeque.get(j) / 10;
                myDeque.set(j, myDeque.get(j) % 10);
            }
            if (coeff > 0) {
                    myDeque.add(coeff);
                    coeff = 0;
                }
            }
            System.out.println("This is 3 in " + N + " degree: ");
            for (int j = myDeque.size()-1; j >=0 ; j--) System.out.print(myDeque.get(j));
        }
    }

