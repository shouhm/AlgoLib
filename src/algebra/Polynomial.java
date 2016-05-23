package algebra;

import java.util.*;

public class Polynomial {
    static final double EPS = 1e-12;
    static final double INF = 1e12;

    public double[] coef;

    public Polynomial(double[] Coef) {
        coef = Coef;
    }

    public ArrayList<Double> solveEquation() {
        return solveEquation(coef);
    }

    static public int sign(double x) {
        return x < -EPS ? -1 : (x > EPS ? 1 : 0);
    }

    static public double getValue(double[] coef, double x) {
        double e = 1.0, s = 0.0;
        for (int i = 0; i < coef.length; i ++) {
            s += coef[i] * e;
            e *= x;
        }
        return s;
    }

    static public ArrayList<Double> solveEquation(double[] coef) {
        ArrayList<Double> ret = new ArrayList<>();
        if (coef.length == 2) {
            if (coef[1] != 0)
                ret.add( - coef[0] / coef[1] );
            return ret;
        }
        int n = coef.length;
        double[] dcoef = new double[n - 1];
        for (int i = 0; i < n - 1 - 1; i ++)
            dcoef[i] = coef[i + 1] * (i + 1);
        ArrayList<Double> droot = solveEquation(dcoef);
        droot.add(0, -INF);
        droot.add(INF);
        for (int i = 0; i + 1 < droot.size(); i ++) {
            double tmp = findRoot(coef, droot.get(i), droot.get(i + 1));
            if (tmp < INF) ret.add(tmp);
        }
        return ret;
    }

    static public double findRoot(double[] coef, double lower, double upper) {
        int sign_low = sign(getValue(coef, lower));
        int sign_up  = sign(getValue(coef, upper));
        if (sign_low == 0) return lower;
        if (sign_up == 0) return upper;
        if (sign_low * sign_up > 0) return INF;
        for (int step = 0; step < 100 && upper - lower > EPS; step ++) {
            double mid = (lower + upper) * 0.5;
            int sign_mid = sign(getValue(coef, mid));
            if (sign_mid == 0) return mid;
            if (sign_low * sign_mid < 0) {
                upper = mid;
            } else {
                lower = mid;
            }
        }
        return (lower + upper) / 0.5;
    }
}
