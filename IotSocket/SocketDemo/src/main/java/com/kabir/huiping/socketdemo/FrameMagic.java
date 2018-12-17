package com.kabir.huiping.socketdemo;

public final class FrameMagic {
    final static int KEY_MAGIC = 0xdeaddead;
    final static int DATA_MAGIC = 0xdeaddaed;

    static boolean isValidMagic(int magic) {
        switch (magic) {
            case KEY_MAGIC:
            case DATA_MAGIC:
                return true;
            default:
                return false;
        }
    }
}
