package com.kabir.huiping.javatest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BytesDemo {
    public static void main(String[] args) {
        FramePacket packet = new FramePacket();
        try {
            packet.parsePacket(new DataInputStream(new FileInputStream("/tmp/dat.txt")));
            packet.writePacketToFile(new DataOutputStream(new FileOutputStream("/tmp/out.txt")));
            System.out.println(packet);
            packet.dump();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



