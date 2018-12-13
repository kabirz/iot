package com.kabir.huiping.javatest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FramePacket {
    final static private int MAGIC = 0xdeaddead;

    private int magic;
    private int id;
    private char flags;
    private int bufferLen;
    private byte[] bufffer;
    private int nameLen;
    private String name;

    private static String byteArrayToStr(byte[] b, int len) {
        char[] buf = new char[len];
        for (int i = 0; i < len; i++) {
            buf[i] = (char) b[i];
        }
        return String.copyValueOf(buf);
    }

    private static byte[] strToByteArray(String a) {
        byte[] buf = new byte[a.length()];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) a.toCharArray()[i];
        }
        return buf;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getFlags() {
        return flags;
    }

    public void setFlags(char flags) {
        this.flags = flags;
    }

    public int getBufferLen() {
        return bufferLen;
    }

    public void setBufferLen(int bufferLen) {
        this.bufferLen = bufferLen;
    }

    public byte[] getBufffer() {
        return bufffer;
    }

    public void setBufffer(byte[] bufffer) {
        this.bufffer = bufffer;
    }

    public int getNameLen() {
        return nameLen;
    }

    public void setNameLen(int nameLen) {
        this.nameLen = nameLen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void parsePacket(DataInputStream dis) throws IOException {
        magic = Integer.reverseBytes(dis.readInt());
        checkMagic();
        id = Integer.reverseBytes(dis.readInt());
        flags = Character.reverseBytes(dis.readChar());
        bufferLen = Integer.reverseBytes(dis.readInt());
        bufffer = new byte[bufferLen];
        int len = dis.read(bufffer);
        nameLen = Integer.reverseBytes(dis.readInt());
        byte[] _name = new byte[nameLen];
        name = byteArrayToStr(_name, dis.read(_name));

    }

    @Override
    public String toString() {
        checkMagic();
        return "myPacket{" +
                "magic=0x" + Integer.toHexString(magic) +
                ", id=" + id +
                ", flags=" + flags +
                ", bufferLen=" + bufferLen +
                ", bufffer=" + Arrays.toString(bufffer) +
                ", nameLen=" + nameLen +
                ", name='" + name + '\'' +
                '}';
    }

    private void checkMagic() {
        if (magic != MAGIC) {
            System.err.println("magic error!");
            System.exit(-1);
        }
    }

    public void writePacketToFile(DataOutputStream dos) throws IOException {
        dos.writeInt(Integer.reverseBytes(magic));
        dos.writeInt(Integer.reverseBytes(id));
        dos.writeChar(Character.reverseBytes(flags));
        dos.writeInt(Integer.reverseBytes(bufferLen));
        dos.write(bufffer);
        dos.writeInt(Integer.reverseBytes(nameLen));
        dos.write(strToByteArray(name));
    }

    public void dump() {
        System.out.println("MAGIC:      0x" + Integer.toHexString(magic));
        System.out.println("ID:         " + id);
        System.out.println("Flags:      " + flags);
        System.out.println("DataSize:   " + bufferLen);
        System.out.println("Data:       " + Arrays.toString(bufffer));
        System.out.println("NameSize:   " + nameLen);
        System.out.println("Name:       " + name);
    }
}