package com.kabir.huiping.javatest;

public class TestDemo {
    public static void main(String[] args) {
        arrayTest();
        FrameDemo.test();

    }

    private static void print(Object o) {
        System.out.println(o);
    }

    private static void arrayTest() {
        ArrayDemo a = new ArrayDemo(10);
        a.setValue();
        print(a);
        a.sort();
        print(a);
    }
}
