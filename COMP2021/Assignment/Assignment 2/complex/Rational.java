// Guanlin Jiang (21093962d)

package hk.edu.polyu.comp.comp2021.assignment2.complex;

public class Rational {
    // Task 1: add the missing fields
    private int numerator;
    private int denominator;

    public Rational(int numerator, int denominator) {
        // Task 2: complete the constructor
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Rational add(Rational other) {
        // Task 2: complete the method
        int numerator_res = this.numerator * other.denominator + this.denominator * other.numerator;
        int denominator_res = other.denominator * this.denominator;
        return new Rational(numerator_res, denominator_res);
    }

    public Rational subtract(Rational other) {
        // Task 2: complete the method
        Rational res = this.add(new Rational((0 - other.numerator), other.denominator));
        return res;
    }

    public Rational multiply(Rational other) {
        // Task 2: complete the method
        int numerator_res = this.numerator * other.numerator;
        int denominator_res = this.denominator * other.denominator;
        return new Rational(numerator_res, denominator_res);
    }

    public Rational divide(Rational other) {
        // Task 2: complete the method
        return this.multiply(new Rational(other.denominator, other.numerator));
    }

    public String toString() {
        // Task 2: complete the method
        return numerator + "/" + denominator;
    }

    public void simplify() {
        // Task 2: complete the method
        if (numerator == 0) {
            denominator = 1;
        } else {
            int max_common_fact;
            int dividend;
            if (Math.abs(numerator) < Math.abs(denominator)) {
                max_common_fact = numerator;
            } else {
                max_common_fact = denominator;
            }
            if (Math.abs(numerator) > Math.abs(denominator)) {
                dividend = numerator;
            } else {
                dividend = denominator;
            }
            int mod_value = 1;
            while (mod_value != 0) {
                mod_value = dividend % max_common_fact;
                if (mod_value == 0) {
                    break;
                }
                dividend = max_common_fact;
                max_common_fact = mod_value;
            }
            numerator = numerator / max_common_fact;
            denominator = denominator / max_common_fact;
            if (denominator < 0) {
                denominator = 0 - denominator;
                numerator = 0 - numerator;
            }
        }
    }

    // ==================================

}
