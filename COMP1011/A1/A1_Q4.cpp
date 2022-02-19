#include <iostream>
#include <cmath>

using namespace std;

int main() {
    int days_1, months_1, years_1; // for first time input
    int days_2, months_2, years_2; // for second time input
    int days_sum1, days_sum2;
    int answer;
    int month_day[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; // make a day for month array

    cin >> days_1 >> months_1 >> years_1;
    cin >> days_2 >> months_2 >> years_2;

    days_sum1 = years_1 * 365;
    days_sum2 = years_2 * 365;

    for (int n = 0; n < months_1; n++) {
        days_sum1 += month_day[n];
    }
    days_sum1 += days_1 - 1;

    if (months_1 < 1) {
        years_1--;
    }

    days_sum1 += (years_1 / 4) - (years_1 / 100) + (years_1 / 400);

    for (int i = 0; i < months_2; i++) {
        days_sum2 += month_day[i];
    }
    days_sum2 += days_2 + 1;

    if (months_2 < 1) {
        years_2--;
    }

    days_sum2 += (years_2 / 4) - (years_2 / 100) + (years_2 / 400);

    answer = abs(days_sum2 - days_sum1);

    cout<<"Days: "<< answer << endl;

return 0;

}