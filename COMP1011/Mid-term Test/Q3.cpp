#include <iostream>
#include <cmath>
#include <iomanip>

using namespace std;



int main() {
    int date;
    int i = 0;
    int digital_array[7];
    cout << "Please enter a date: ";
    cin >> date;

    int dates = date;

    while (date > 0) {
        int digit = date % 10;
        digital_array[i] = digit;
        date = date / 10;
        i++;
    }

    digital_array[7] = dates / 10000000;

    

    int day_sum = digital_array[0] + digital_array[1];
    int month_sum = digital_array[2] + digital_array[3];
    int year_sum = digital_array[4] + digital_array[5] + digital_array[6] + digital_array[7];

    cout << "The sum of day digits is " << day_sum << endl;
    cout << "The sum of month digits is " << month_sum << endl;
    cout << "The sum of year digits is " << year_sum << endl;
    
    int sum = day_sum + month_sum + year_sum;
    if (sum >= 10) {
        int digit_tenth = sum / 10;
        cout << "The tenth digit is " << digit_tenth << endl;
    } else {
        cout << "The tenth digit is Nothing" << endl;
    }

    int year = digital_array[7] * 1000 + digital_array[6] * 100 + digital_array[5] * 10 + digital_array[4];

    if (year % 4 == 0) {
        cout << "Leap Year: Yes" << endl;
    } else {
        cout << "Leap Year: No" << endl;
    }

    return 0;
}