#include <iostream>
#include <cmath>

using namespace std;

int choice;
double a;
double b;
double answer;

int main(){
    cout << "MENU" << endl;
    cout << "   " << "1. Divide. a/b" << endl;
    cout << "   " << "2. Divide. a*b" << endl;
    cout << "   " << "3. Power. a^b" << endl;
    cout << "   " << "4. Square root. sqrt(a)" << endl;
    cout << "Enter your choice:"; cin >> choice;

    switch (choice){
    case 1:
        cout << "Enter two numbers:"; cin >> a >> b;
        answer = a / b;
        cout << a << "/" << b << "=" << answer << endl;
        break;
    case 2:
        cout << "Enter two numbers:"; cin >> a >> b;
        answer = a * b;
        cout << a << "*" << b << "=" << answer << endl;
        break;
    case 3:
        cout << "Enter two numbers:"; cin >> a >> b;
        answer = pow(a, b);
        cout << a << "^" << b << "=" << answer << endl;
        break;
    case 4:
        cout << "Enter two numbers:"; cin >> a;
        answer = sqrt(a);
        cout << "sqrt(" << a << ")=" << answer << endl;
        break;
    }

    return 0;
}