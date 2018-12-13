package com.kabir.huiping.javatest;

import org.jetbrains.annotations.Contract;

class Hello {
    public static void printHello() {
        System.out.println("Hello, Java");
    }

    private static void test() {
        System.out.println("Hello World");
        System.out.println("Factorial: " + factorial2(6));
        System.out.println("Factorial: " + factorial2(6));
        System.out.println("Narcissistic is: " + countNarcissistic());
    }

    @Contract(pure = true)
    static int factorial1(int num) {
        int s = 1;
        for (int i = 1; i <= num; i++) {
            s *= i;
        }
        return s;
    }

    static int factorial2(int num) {
        if (num <= 1)
            return num;
        else
            return num * factorial2(num - 1);
    }

    @Contract(pure = true)
    static int countNarcissistic() {
        for (int i = 100; i < 1000; i++) {
            int a = i % 10;
            int b = i / 10 % 10;
            int c = i / 100 % 10;
            if (i == a * a * a + b * b * b + c * c * c)
                return i;
        }
        return -1;
    }
}
