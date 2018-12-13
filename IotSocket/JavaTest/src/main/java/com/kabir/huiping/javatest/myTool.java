package com.kabir.huiping.javatest;

class myTool {
    static StringBuilder print9(int num) {
        StringBuilder buf = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= i; j++) {
                buf.append(j + "x" + i + "=" + i * j + "\t");
            }
            buf.append("\n");
        }
        return buf;
    }

    private static void test() {
        System.out.println(myTool.print9(9));
    }


}
