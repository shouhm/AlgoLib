package numeric;

public class Complex extends Number {
    static final double EPS = 1e-15;

    public double Real, Image;

    //Constructors
    public Complex(double R, double I) {
        Real = R;
        Image = I;
    }

    public Complex conjugate() {
        return new Complex(Real, -Image);
    }

    @Override
    public long longValue() {
        return (long) (Real * Real + Image * Image);
    }

    @Override
    public int intValue() {
        return (int) (Real * Real + Image * Image);
    }

    @Override
    public float floatValue() {
        return (float) (Real * Real + Image * Image);
    }

    @Override
    public double doubleValue() {
        return (Real * Real + Image * Image);
    }

    //Binary operations
    static public Complex add(Complex a, Complex b) {
        return new Complex(a.Real + b.Real, a.Image + b.Image);
    }

    static public Complex minus(Complex a, Complex b) {
        return new Complex(a.Real - b.Real, a.Image - b.Image);
    }

    /*
     *   (a.R + a.I * i) * (b.R + b.I * i)
     * = (a.R * b.R - a.I * b.I) + (a.R * b.I + a.I * b.R) * i
     */
    static public Complex mul(Complex a, Complex b) {
        return new Complex(
                a.Real * b.Real - a.Image * b.Image,
                a.Real * b.Image + a.Image * b.Real);
    }

    static public Complex div(Complex a, Complex b) {
        double modular = b.Real * b.Real + b.Image * b.Image;
        if (modular < EPS) {
            throw new ArithmeticException("Complex division on zero");
        }
        return new Complex(
                (a.Real * b.Real + a.Image * b.Image) / modular,
                (a.Image * b.Real - a.Real * b.Image) / modular
        );
    }
}
