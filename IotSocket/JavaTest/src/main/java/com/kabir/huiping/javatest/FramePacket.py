#!/usr/bin/env python3

import random
import sys
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("--input", '-i', help="input file")
parser.add_argument("--output", '-o', help="output file")
parser.add_argument("--endian", '-e', type=str, help="big/little endian")
args = parser.parse_args()


class framepacket():
    MAGIC = 0xdeaddead
    LITTLE = 0x11
    BIG = 0x22

    def __init__(self, input=None, output=None, endian=sys.byteorder):
        self.output = output
        self.input = input
        self.setbyteorder(endian)

    def setbyteorder(self, order):
        if order != 'little' or order != 'big':
            self.byteorder = sys.byteorder
        self.byteorder = order

    def insertdata(self, data, name):
        if type(data) != str or type(name) != str:
            sys.stderr.writelines("Error data and name type!")
            sys.exit(-1)

        self.endian = framepacket.BIG if self.byteorder == 'big' else framepacket.LITTLE
        self.id = random.randint(1, 50)
        self.flags = random.randint(ord('A'), ord('z'))
        self.data = data
        self.dlen = len(data)
        self.name = name
        self.nlen = len(name)

    def generatorpacket(self):
        with open(self.output, 'wb') as f:
            f.write(self.endian.to_bytes(1, byteorder=self.byteorder))
            f.write(framepacket.MAGIC.to_bytes(4, byteorder=self.byteorder))
            f.write(self.id.to_bytes(4, byteorder=self.byteorder))
            f.write(self.flags.to_bytes(2, byteorder=self.byteorder))
            f.write(self.dlen.to_bytes(4, byteorder=self.byteorder))
            f.write(self.data.encode())
            f.write(self.nlen.to_bytes(4, byteorder=self.byteorder))
            f.write(self.name.encode())

    def _getdata(self, buf, index, len):
        return int.from_bytes(buf[index: index + len], byteorder=self.byteorder)

    def parsepacket(self):
        with open(self.input, 'rb') as f:
            buf = f.read()
            index = 0
            self.endian = self._getdata(buf, index, 1)
            index += 1
            if self.endian == framepacket.BIG:
                self.setbyteorder('big')
            magic = self._getdata(buf, index, 4)
            if magic != framepacket.MAGIC:
                sys.stderr.write("magic error!")
                sys.exit(-1)
            index += 4

            self.id = self._getdata(buf, index, 4)
            index += 4
            self.flags = self._getdata(buf, index, 2)
            index += 2
            self.dlen = self._getdata(buf, index, 4)
            index += 4
            self.data = buf[index:index + self.dlen].decode()
            index += self.dlen

            self.nlen = self._getdata(buf, index, 4)
            index += 4
            self.name = buf[index:index + self.nlen].decode()

    def dump(self):
        print("LITTLE-ENDIAN:  ", self.endian == self.LITTLE)
        print("MAGIC:           0x%x" % framepacket.MAGIC)
        print("ID:              %d" % self.id)
        print("Flags:           %c" % self.flags)
        print("DataSize:        %d" % self.dlen)
        print("Data:           ", list(self.data.encode()))
        print("NameSize:        %d" % self.nlen)
        print("Name:            %s" % self.name)


if __name__ == "__main__":
    if args.output:
        pkt = framepacket(output=args.output, endian=args.endian)
        pkt.insertdata("FrameForTest", "kabir")
        pkt.generatorpacket()
        pkt.dump()
    elif args.input:
        pkt = framepacket(input=args.input)
        pkt.parsepacket()
        pkt.dump()
    else:
        parser.print_help()
