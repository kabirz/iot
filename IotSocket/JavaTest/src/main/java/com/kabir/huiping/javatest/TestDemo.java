package com.kabir.huiping.javatest;

public class TestDemo {
    public static void main(String[] args) {
        System.out.println("Hello, Java");
        print9(9);
    }

    private static void print9(int num) {
        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(i + "x" + j + "=" + i * j + "\t");
            }
            System.out.println();
        }
    }

}
