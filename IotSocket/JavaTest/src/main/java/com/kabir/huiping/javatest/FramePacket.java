package com.kabir.huiping.javatest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FramePacket {
    final static int MAGIC = 0xdeaddead;
    final static byte LITTLE = 0x11;
    final static byte BIG = 0x22;

    private byte endian;
    private int magic;
    private int id;
    private char flags;
    private int bufferLen;
    private byte[] bufffer;
    private int nameLen;
    private String name;
    private boolean littleEndian;

    public FramePacket() {
    }

    public FramePacket(boolean littleEndian, int id, char flags, int bufferLen, byte[] bufffer, int nameLen, String name) {
        endian = littleEndian ? LITTLE : BIG;
        magic = MAGIC;
        this.id = id;
        this.flags = flags;
        this.bufferLen = bufferLen;
        this.bufffer = bufffer;
        this.nameLen = nameLen;
        this.name = name;
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

    public boolean isLittleEndian() {
        return littleEndian;
    }

    public void setLittleEndian(boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    public byte getEndian() {
        return endian;
    }

    public void setEndian(byte endian) {
        this.endian = endian;
    }

    public void parsePacket(DataInputStream dis) throws IOException {
        endian = dis.readByte();
        if (endian == LITTLE)
            setLittleEndian(true);
        else if (endian == BIG)
            setLittleEndian(false);
        else {
            System.err.println("Error big/little endian flags");
            throw new IOException();
        }

        magic = dis.readInt();
        if (littleEndian)
            magic = Integer.reverseBytes(magic);
        checkMagic();

        id = dis.readInt();
        if (littleEndian)
            id = Integer.reverseBytes(id);

        flags = dis.readChar();
        if (littleEndian)
            flags = Character.reverseBytes(flags);

        bufferLen = dis.readInt();
        if (littleEndian)
            bufferLen = Integer.reverseBytes(bufferLen);
        bufffer = new byte[bufferLen];

        int len = dis.read(bufffer);
        nameLen = dis.readInt();
        if (littleEndian)
            nameLen = Integer.reverseBytes(nameLen);
        byte[] _name = new byte[nameLen];
        len = dis.read(_name);
        name = new String(_name);

    }

    @Override
    public String toString() {
        return "FramePacket{" +
                "magic=" + Integer.toHexString(magic) +
                ", id=" + id +
                ", flags=" + flags +
                ", bufferLen=" + bufferLen +
                ", bufffer=" + Arrays.toString(bufffer) +
                ", nameLen=" + nameLen +
                ", name='" + name + '\'' +
                ", littleEndian=" + littleEndian +
                '}';
    }

    private void checkMagic() {
        if (magic != MAGIC) {
            System.err.println("magic error!");
            System.exit(-1);
        }
    }

    public void writePacket(DataOutputStream dos) throws IOException {
        dos.writeByte(endian);
        if (littleEndian) {
            dos.writeInt(Integer.reverseBytes(magic));
            dos.writeInt(Integer.reverseBytes(id));
            dos.writeChar(Character.reverseBytes(flags));
            dos.writeInt(Integer.reverseBytes(bufferLen));
        } else {
            dos.writeInt(magic);
            dos.writeInt(id);
            dos.writeChar(flags);
            dos.writeInt(bufferLen);
        }
        dos.write(bufffer);
        if (littleEndian) {
            dos.writeInt(Integer.reverseBytes(nameLen));
        } else {
            dos.writeInt(nameLen);
        }
        dos.write(name.getBytes());
    }

    public  void doWork() {
        if (id == SocketId.KEY) {
            SocketKey key = new SocketKey();
            key.parse(bufffer);
            key.doWork();
        }
    }

    public void dump() {
        System.out.println("Little-Endian:    " + littleEndian);
        System.out.println("MAGIC:            0x" + Integer.toHexString(magic));
        System.out.println("ID:               " + id);
        System.out.println("Flags:            " + flags);
        System.out.println("DataSize:         " + bufferLen);
        System.out.println("Data:             " + Arrays.toString(bufffer));
        System.out.println("NameSize:         " + nameLen);
        System.out.println("Name:             " + name);
    }
}