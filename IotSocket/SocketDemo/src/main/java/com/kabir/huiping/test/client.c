#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <errno.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <signal.h>

extern int socket_send_data(int sfd);
extern int socket_receive_data(int rfd);

int main(int argc, char const *argv[])
{
	int sfd;
	struct sockaddr_in servaddr;

	if (argc < 3) {
		printf("Usage: %s ip port\n", argv[0]);
		return -1;
	}
	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sfd < 0) {
		perror("socket error");
		exit(1);
	}

	servaddr.sin_family = AF_INET;
	servaddr.sin_port = htons(atoi(argv[2]));
	if (inet_pton(AF_INET, argv[1], &servaddr.sin_addr) < 0) {
		printf("inet_pton error for %s\n", argv[1]);
		exit(1);
	}

	if (connect(sfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) {
		perror("connect error");
		exit(1);
	}

        if (socket_send_data(sfd)){
                perror("socket send error\n");
                close(sfd);
                return -1;
        }
       
        if ( socket_receive_data(sfd)){
                perror("socket receive error\n");
                close(sfd);
                return -1;
        }
        close(sfd);
        return 0;
}
