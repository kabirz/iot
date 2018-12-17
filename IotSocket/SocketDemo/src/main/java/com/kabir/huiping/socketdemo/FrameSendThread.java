package com.kabir.huiping.socketdemo;

import java.io.DataOutputStream;
import java.io.IOException;

public class FrameSendThread extends Thread {
    private FrameCallBack cb;

    public FrameSendThread(FrameCallBack cb) {
        this.cb = cb;
    }

    @Override
    public void run() {
        try {
            cb.getLock().lock();
            cb.getC().await();
            cb.getLock().unlock();
            DataOutputStream dos = new DataOutputStream(cb.getClient().getOutputStream());
            byte[] a = {1, 2};
            FramePacket fp = new FramePacket(true, FrameMagic.KEY_MAGIC, FrameId.EXIT_ID, a.length, a);
            fp.dataOutput(dos);
            System.out.println(fp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


