package com.kabir.huiping.javatest;

final class OverloadDemo {
    static int sum(int a, int b) {
        return a + b;
    }

    static float sum(float a, float b) {
        return a + b;
    }

    private static void test() {
        System.out.println(sum(100, 120));
        System.out.println(sum(105, 1.20f));
    }
}
