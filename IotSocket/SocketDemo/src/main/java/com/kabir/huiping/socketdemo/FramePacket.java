package com.kabir.huiping.socketdemo;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FramePacket {
    private boolean byteorder;
    private int magic;
    private int id;
    private int size;
    private byte[] data;

    public boolean isByteorder() {
        return byteorder;
    }

    public void setByteorder(boolean byteorder) {
        this.byteorder = byteorder;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public FramePacket(boolean byteorder, int magic, int id, int size, byte[] data) {
        this.byteorder = byteorder;
        this.magic = magic;
        this.id = id;
        this.size = size;
        this.data = data;
    }

    public FramePacket() {
    }

    public boolean parse(DataInputStream dis) throws IOException {
        byteorder = dis.readBoolean();
        if (byteorder) {
            magic = Integer.reverseBytes(dis.readInt());
            id = Integer.reverseBytes(dis.readInt());
            size = Integer.reverseBytes(dis.readInt());
        } else {
            magic = dis.readInt();
            id = dis.readInt();
            size = dis.readInt();
        }
         if (!FrameMagic.isValidMagic(magic)) {
             System.err.println("invalid magic");
             System.exit(-1);
         }
        data = new byte[size];
        dis.read(data);
       return true;
    }

    public boolean handle() {
        if (id == FrameId.KEY_ID) {
            IFrameEvent  fe = new FrameKey();
            fe.parse(data);
            fe.doWork();
        }
        return true;
    }
    public void dataOutput(DataOutputStream dos) throws IOException {
            dos.writeBoolean(byteorder);
            if (byteorder) {
                dos.writeInt(Integer.reverseBytes(magic));
                dos.writeInt(Integer.reverseBytes(id));
                dos.writeInt(Integer.reverseBytes(size));
            }else {
                dos.writeInt(magic);
                dos.writeInt(id);
                dos.writeInt(size);
            }
            dos.write(data);
            dos.flush();
    }
    @Override
    public String toString() {
        return "FramePacket{" +
                "byteorder=" + byteorder +
                ", magic=0x" + Integer.toHexString(magic) +
                ", id=" + id +
                ", size=" + size +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
