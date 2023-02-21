// GPA.c - Guanlin Jiang (21093962d)
#include <stdio.h>

int main(int argc, char *argv[])
{
  int	num_subj;
  float in_gp, sum_gp = 0.0;
  char	in_grade;
  int	i;

  // argv[0] is the name of the program
  // printf("This program is %s\n",argv[0]);
  num_subj = argc-1;
  printf("PolyU System:\n");
  int valid = 0;

  for (i = 1; i <= num_subj; i++) {
    in_grade = argv[i][0]; // get the first character
    switch (in_grade) {
      case 'A': in_gp = 4.0; break;
      case 'B': in_gp = 3.0; break;
      case 'C': in_gp = 2.0; break;
      case 'D': in_gp = 1.0; break;
      case 'F': in_gp = 0.0; break;
      default: printf("Wrong grade %s\n",argv[i]);
    }

    char c_grade[] = {in_grade, argv[i][1], '\0'};

    if (in_grade == 'D' && argv[i][1] == '-') {
      printf("Grade for subject %d is %s, invalid\n",i,c_grade);
      continue;
    }

    if (argv[i][1] == '+') in_gp = in_gp + 0.3;
    if (argv[i][1] == '-') in_gp = in_gp - 0.3;

    valid++;
    
    printf("Grade for subject %d is %s, GP %.1f\n",i,c_grade,in_gp);

    sum_gp = sum_gp + in_gp;
  }

  printf("Your GPA for %d valid subjects is %.2f\n",valid,sum_gp/valid);

  sum_gp = 0.0;

  printf("Other System:\n");

  for (i = 1; i <= num_subj; i++) {
    in_grade = argv[i][0]; // get the first character
    
    switch (in_grade) {
      case 'A': in_gp = 11; break;
      case 'B': in_gp = 8; break;
      case 'C': in_gp = 5; break;
      case 'D': in_gp = 2; break;
      case 'F': in_gp = 0; break;
      default: printf("Wrong grade %s\n",argv[i]);
    }

    if (argv[i][1] == '+') in_gp = in_gp + 1;
    if (argv[i][1] == '-') in_gp = in_gp - 1;

    char c_grade[] = {in_grade, argv[i][1], '\0'};
    
    printf("Grade for subject %d is %s, GP %.0f\n",i,c_grade,in_gp);

    sum_gp = sum_gp + in_gp;
  }

  printf("Your GPA for %d valid subjects is %.2f\n",num_subj,sum_gp/num_subj);
}
