package controller;

/**
 * “Assignment 1 Question 4 Answer”, COMP 1011 Programming Fundamental, Jan, 2022.
 * @author COMP 1011 Programming Fundamental
 */

// s = start, e = end
public class DayCalculator {
    public static int dayApart(int sDay, int sMonth, int sYear, int eDay, int eMonth, int eYear) {
        int daysOfMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        boolean isLeap = false;

        int days = 0;
        for (int currentYear = sYear; currentYear <= eYear; currentYear++) {

            // Consider the leap year and set daysOfMonth[1] to 28 or 29
            isLeap = ((currentYear % 4 == 0 && currentYear % 100 != 0) || currentYear % 400 == 0);
            daysOfMonth[1] = (isLeap ? 29 : 28);

            if (currentYear == sYear && currentYear != eYear) { // Consider the starting year and it is not equal to the ending year
                for (int currentMonth = sMonth; currentMonth <= 12; currentMonth++) {
                    if (currentMonth == sMonth) { // Current month to be considered is the month of the starting date
                        if (sDay != 1) {
                            days += daysOfMonth[currentMonth - 1] - sDay + 1;
                        } else {
                            days += daysOfMonth[currentMonth - 1];
                        }
                    } else {
                        days += daysOfMonth[currentMonth - 1];
                    }
                }
            } else if (currentYear != sYear && currentYear != eYear) { // Consider the non-starting year and non ending year
                days += (isLeap ? 366 : 365);
            } else if (currentYear != sYear && currentYear == eYear) { // Consider the ending year and it is not equal to the starting year
                for (int currentMonth = 1; currentMonth <= eMonth; currentMonth++) {
                    if (currentMonth == eMonth) { // Current month to be considered is the month of the starting date
                        if (eDay != daysOfMonth[currentMonth - 1]) {
                            days += eDay;
                        } else {
                            days += daysOfMonth[currentMonth - 1];
                        }
                    } else {
                        days += daysOfMonth[currentMonth - 1];
                    }
                }
            } else if (sYear == eYear) { // Starting year and ending year are the same
                for (int currentMonth = sMonth; currentMonth <= eMonth; currentMonth++) {
                    if (sMonth == eMonth) { // Starting month and ending month are the same
                        days += eDay - sDay + 1;
                    } else if (currentMonth == sMonth) { // Current month to be considered is the month of the starting date
                        if (sDay != 1) {
                            days += daysOfMonth[currentMonth - 1] - sDay + 1;
                        } else {
                            days += daysOfMonth[currentMonth - 1];
                        }
                    } else if (currentMonth == eMonth) { // Current month to be considered is the month of the ending date
                        if (eDay != daysOfMonth[currentMonth - 1]) {
                            days += eDay;
                        } else {
                            days += daysOfMonth[currentMonth - 1];
                        }
                    } else {
                        days += daysOfMonth[currentMonth - 1];
                    }
                }
            }

        }
        return days;
    }
}
