package controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DayCalculatorTest {

    @Test
    void dayApartTest() {
        int year1 = 2022;
        int month1 = 10;
        int day1 = 1;

        int year2 = 2022;
        int month2 = 11;
        int day2 = 1;
        assertEquals(32, new DayCalculator().dayApart(day1, month1, year1, day2, month2, year2));
    }
}