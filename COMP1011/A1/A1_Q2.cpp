#include <iostream>

using namespace std;

int x;
int num;
int factor_i;

int main(){
    cout << "Enter a postive int number: "; cin >> num;
    srand(num);
    x = (rand() % (24680 - 2022 + 1)) + 2022;

    if (x >= 2022 && x <= 24680){
        cout <<"The number is between in this range, and the random number is: " << x << endl;
        cout << "The factor is: ";
        for (factor_i = 2; factor_i <= x; factor_i++){
            if (x % factor_i == 0) {
                cout << factor_i << " ";
            }
        }

    } else{
        cout <<"The number is not between in this range" << endl;
    }

    return 0;
}