import socket
import struct
from time import sleep

s = socket.socket()
s.connect(('localhost', 12345))

data = (ord('M'),2,4)
format = "<b3I%db"%len(data)

buf = (1, 0x12345678, 1, len(data), *data)
frame=struct.pack(format, *buf)
s.send(frame)

buf = (1, 0x12345678, 0, len(data), *data)
frame=struct.pack(format, *buf)
s.send(frame)
sleep(1)
a = s.recv(512)
byteorder = int(a[0])
format1 = "%s3I" % ('<' if byteorder else '>')
d1 =struct.unpack(format1, a[1:13])
format = "<b3I%db"%d1[2]
d1 = struct.unpack(format, a)
print(d1)
s.close()
