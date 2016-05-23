package numeric;

import algebra.NumberTheory;

public class Fraction {
    long num, den;

    public Fraction(long numerator, long denominator) {
        num = numerator; den = denominator;
        if (den < 0) {
            den = - den; num = - num;
        }
        long g = NumberTheory.gcd(Math.abs(num), den);
        num /= g; den /= g;
    }

    static public Fraction add(Fraction a, Fraction b) {
        return new Fraction(
                a.num * b.den + b.num * a.den, a.den * b.den
        );
    }

    static public Fraction minus(Fraction a, Fraction b) {
        return new Fraction(
                a.num * b.den - b.num * a.den, a.den * b.den
        );
    }

    static public Fraction mul(Fraction a, Fraction b) {
        return new Fraction(
                a.num * b.num, a.den * b.den
        );
    }

    static public Fraction div(Fraction a, Fraction b) {
        return new Fraction(
                a.num * b.den, a.den * b.num
        );
    }

    static public boolean less(Fraction a, Fraction b) {
        return a.num * b.den < a.den * b.num;
    }

    static public boolean equal(Fraction a, Fraction b) {
        return a.num * b.den == a.den * b.num;
    }
}
