#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <string.h>
#include <unistd.h>

struct socket_frame {
	char endian;
	int magic;
	int id;
	int len;
	char buf[0];
} __attribute__((packed));


static void dump(const struct socket_frame *sf, const char *data)
{
        if (sf == NULL || data == NULL) {
                printf("Dump error, NULL pointer\n");
                return;
        }

        printf("edian: %s, magic: %#x, id: %d, len: %d ",
               sf->endian ? "little" : "big", sf->magic, sf->id, sf->len);
        for (int i = 0; i < sf->len; i++) {
                if (i == 0)
                        printf("data: [%d ", data[i]);
                else if (i == sf->len -1)
                        printf("%d]\n", data[i]);
                else
                        printf("%d ", data[i]);
        }
}
// send data to server
int socket_send_data(int sfd)
{
	struct socket_frame *mybuf = NULL;
        char buf[5] = {'M'};
        int len = sizeof(struct socket_frame) + sizeof(buf);

        srand(time(NULL));
        for (int i = 1; i < sizeof(buf); i++) {
                buf[i] = rand() % 120;
        }
        if (sfd < 0) {
                printf("error fd\n");
                return -1;
        }

        if ((mybuf = malloc(len)) == NULL) {
                printf("malloc error!\n");
                return -1;
        }

        mybuf->endian = 1;
        mybuf->magic = 0x12345678;
        mybuf->id = 0x1;
        mybuf->len = sizeof(buf);
        memcpy(mybuf->buf, buf, sizeof(buf));

        int size = write(sfd, mybuf, len);
        if (size != len) {
                printf("send error!\n");
                return -1;
        }

        mybuf->id = 0x0;
        for (int i = 1; i < sizeof(buf); i++) {
                mybuf->buf[i] = rand() % 120;
        }
        size = write(sfd, mybuf, len);
        if (size != len) {
                printf("send error!\n");
                return -1;
        }
        
        free(mybuf);
}
//receive data from server
int socket_receive_data(int rfd)
{
        struct socket_frame mybuf;
        char *buf;
        int len = 0;
        if (rfd < 0) {
                printf("error fd\n");
                return -1;
        }

        memset(&mybuf, 0, sizeof(mybuf));
        while(len != sizeof(mybuf)){
                len += read(rfd, (char*)&mybuf + len, sizeof(mybuf) - len);
        }

        if ((buf = malloc(mybuf.len)) == NULL) {
                printf("malloc error!\n");
                return -1;
        }
        len = 0;
        while(len != mybuf.len){
                len += read(rfd, buf + len, mybuf.len - len);
        }
        dump(&mybuf, buf);
        free(buf);
}
