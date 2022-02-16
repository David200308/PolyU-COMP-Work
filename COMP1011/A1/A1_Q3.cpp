#include <iostream>

using namespace std;

int num;
int first_num;
int second_num;

int main(){
    do{
        cin >> num;
        if(num == 11) {
            cout << "eleven"<<endl;
            continue;
        } else if(num == 12) {
            cout << "twelve"<<endl;
            continue;
        } else if(num == 13) {
            cout << "thirteen"<<endl;
            continue;
        } else if(num == 14) {
            cout << "fourteen"<<endl;
            continue;
        } else if(num == 15) {
            cout << "fifteen"<<endl;
            continue;
        } else if(num == 16) {
            cout << "sixteen"<<endl;
            continue;
        } else if(num == 17) {
            cout << "seventeen"<<endl;
        } else if(num == 18) {
            cout << "eighteen"<<endl;
            continue;
        } else if(num == 19) {
            cout << "nineteen"<<endl;
            continue;
        } else if(num == 10) {
            cout << "ten"<<endl;
            continue;
        } else  if (num / 10 == 9) {
            cout << "ninety";
            } else if (num / 10 == 8) {
                cout << "eighty";
            } else if (num / 10 == 7) {
                cout << "seventy";
            } else if (num / 10 == 6) {
                cout << "sixty";
            } else if (num / 10 == 5) {
                cout << "fifty";
            } else if (num / 10 == 4) {
                cout << "forty";
            } else if (num / 10 == 3) {
                cout << "thirty";
            } else if (num / 10 == 2) {
                cout << "twenty";
            }
        if(num%10==0)
        {
            cout<<endl;
            continue;
        }
        if (num > 10 && num - ((num / 10)*10) != 0) 
            cout<<'-';
        num = num - ((num / 10)*10);
            if(num == 1) {
                cout << "one";
            } else if(num == 2) {
                cout << "two";
            } else if(num == 3) {
                cout << "three";
            } else if(num == 4) {
                cout << "four";
            } else if(num == 5) {
                cout << "five";
            } else if(num == 6) {
                cout << "six";
            } else if(num == 7) {
                cout << "seven";
            } else if(num == 8) {
                cout << "eight";
            } else if(num == 9) {
                cout << "nine";
            } 
            cout<<endl;
        }
    while(num != -1);
    return 0;
}