#include<stdio.h>
#include<stdlib.h>

int main(int argc, char *argv[]) {
    printf("parent process %d:\n", getpid());
	int i;
	for(i = 3; i < argc; i++) {
		if(fork() == 0) {
			printf("child process %d, from User %s\n",getpid(), argv[i]);
			exit(0);
		}
        sleep(2);
        
	}
    int i;
    for (i = 3; i < argc; i++) {
        wait(NULL);
    }
    
    return 0; 
}
