package com.kabir.huiping.socketdemo;

import java.io.DataInputStream;
import java.io.IOException;

public class FrameReceiverThread extends Thread {
    private FrameCallBack cb;

    public FrameReceiverThread(FrameCallBack cb) {
        this.cb = cb;
    }

    @Override
    public void run() {
        while (true) {
            try {
                DataInputStream dis = new DataInputStream(cb.getClient().getInputStream());
                FramePacket fp = new FramePacket();
                fp.parse(dis);
                if (fp.getId() == FrameId.EXIT_ID) {
                    cb.getLock().lock();
                    cb.getC().signal();
                    cb.getLock().unlock();
                    break;
                }
                fp.handle();
                System.out.println(fp);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
