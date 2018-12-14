#!/usr/bin/env python3

import random
import sys
import argparse

parser = argparse.ArgumentParser()
parser.add_argument("--input", '-i', help="input file")
parser.add_argument("--output", '-o', help="output file")
args = parser.parse_args()


class framepacket():
    MAGIC = 0xdeaddead

    def __init__(self, input=None, output=None):
        self.output = output
        self.input = input
        self.byteorder = 'little'

    def setbyteorder(self, order):
        if order != 'little' or order != 'big':
            self.byteorder = sys.byteorder
        self.byteorder = order

    def insertdata(self, data, name):
        if type(data) != str or type(name) != str:
            sys.stderr.writelines("Error data and name type!")
            sys.exit(-1)

        self.id = random.randint(1, 50)
        self.flags = random.randint(ord('A'), ord('z'))
        self.data = data
        self.dlen = len(data)
        self.name = name
        self.nlen = len(name)

    def generatorpacket(self):
        with open(self.output, 'wb') as f:
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
            self.data = buf[index:index+self.dlen].decode()
            index += self.dlen

            self.nlen = self._getdata(buf, index, 4)
            index += 4
            self.name = buf[index:index+self.nlen].decode()

    def dump(self):
        print("MAGIC:       0x%x" % framepacket.MAGIC)
        print("ID:          %d" % self.id)
        print("Flags:       %c" % self.flags)
        print("DataSize:    %d" % self.dlen)
        print("Data:       ", list(self.data.encode()))
        print("NameSize:    %d" % self.nlen)
        print("Name:        %s" % self.name)


if __name__ == "__main__":
    if args.output:
        pkt = framepacket(output=args.output)
        pkt.insertdata("FrameForTest", "kabir")
        pkt.generatorpacket()
    elif args.input:
        pkt = framepacket(input=args.input)
        pkt.parsepacket()
        pkt.dump()
    else:
        parser.print_help()
