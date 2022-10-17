// Guanlin Jiang (21093962d)

package hk.edu.polyu.comp.comp2021.assignment2.complex;

public class Complex {
    // Task 3: add the missing fields
    private Rational real;
    private Rational imag;

    public Complex(Rational real, Rational imag) {
        // Task 4: complete the constructor
        this.real = real;
        this.imag = imag;
    }

    public Complex add(Complex other) {
        // Task 4: complete the method
        Rational real_res = real.add(other.real);
        Rational imag_res = imag.add(other.imag);
        return new Complex(real_res, imag_res);
    }

    public Complex subtract(Complex other) {
        // Task 4: complete the method
        Rational real_res = real.subtract(other.real);
        Rational imag_res = imag.subtract(other.imag);
        return new Complex(real_res, imag_res);
    }

    public Complex multiply(Complex other) {
        // Task 4: complete the method
        Rational real_res = (real.multiply(other.real)).subtract(imag.multiply(other.imag));
        Rational imag_res = (imag.multiply(other.real)).add(real.multiply(other.imag));
        return new Complex(real_res, imag_res);
    }

    public Complex divide(Complex other) {
        // Task 4: complete the method
        // you may assume 'other' is never equal to '0+/-0i'.
        Rational numerator_real = (real.multiply(other.real)).add(imag.multiply(other.imag));
        Rational denominator_real = ((other.real).multiply(other.real)).add((other.imag).multiply(other.imag));
        Rational real_res = (numerator_real).divide(denominator_real);

        Rational numerator_imag = (imag.multiply(other.real)).subtract(real.multiply(other.imag));
        Rational denominator_imag = ((other.real).multiply(other.real)).add((other.imag).multiply(other.imag));
        Rational imag_res = (numerator_imag).divide(denominator_imag);

        return new Complex(real_res,imag_res);
    }

    public void simplify() {
        // Task 4L complete the method
        real.simplify();
        imag.simplify();
    }

    public String toString() {
        // Task 4: complete the method
        return "(" + real + "," + imag +")";
    }

    // ==================================

}
