#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include <unistd.h>

int main() {
	int fd = open("/dev/echo_char_driver", O_WRONLY);
	if (fd < 0) {
		printf("failed.\n");
		return 0;
	}

	printf("Input: ");
	char msg[256];
	fgets(msg, sizeof(msg), stdin);

	if (write(fd, msg, sizeof(msg)) < 0) {
		printf("failed.\n");
		return 0;
	}
	
	close(fd);

	return 0;
}
