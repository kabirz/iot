package com.kabir.huiping.javatest;

import java.awt.Frame;

public class FrameDemo {
    public static void test() {
        Frame f = new Frame();
        f.setTitle("Test sample");
        f.setSize(480, 320);
        f.setVisible(true);
        f.setLocation(1000, 1000);
        //f.doLayout();
    }
}
