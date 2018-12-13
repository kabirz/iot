package com.kabir.huiping.javatest;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

class ArrayDemo {
    private int[] a;

    ArrayDemo(int num) {
        this.a = new int[num];
    }

    void setValue() {
        Random random = new Random(new Date().getTime());
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(100);
        }
    }

    void sort() {
        Arrays.sort(a);
    }

    @Override
    public String toString() {
        return "ArrayDemo{" +
                "a=" + Arrays.toString(a) +
                '}';
    }
}
