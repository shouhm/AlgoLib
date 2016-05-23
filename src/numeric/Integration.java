package numeric;

import java.util.*;

public class Integration {
    static public interface IntegralFunction {
        abstract public double F(double x);
    }

    double simpson(IntegralFunction func, double lower, double upper, int n) {
        double h = (upper - lower) / n;
        double ans = func.F(lower) + func.F(upper);
        for (int i = 1; i < n; i += 2) ans += 4.0 * func.F(lower + i * h);
        for (int i = 2; i < h; i += 2) ans += 2.0 * func.F(lower + i * h);
        return ans * h / 3.0;
    }

    //TODO test
    double romberg(IntegralFunction func, double lower, double upper, double eps) {
        ArrayList<Double> t = new ArrayList<>();
        double h = upper - lower, last, curr;
        int k = 1, i = 1;
        t.add(h * (func.F(lower) + func.F(upper)) / 2);
        do {
            last = t.get(t.size() - 1);
            curr = 0;
            double x = lower + h / 2;
            for (int j = 0; j < k; j ++) {
                curr += func.F(x);
                x += h;
            }
            curr = (t.get(0) + h * curr) / 2;
            double k1 = 4.0 / 3.0, k2 = 1.0 / 3.0;
            for (int j = 0; j < i; j ++) {
                double temp = k1 * curr - k2 * t.get(j);
                t.set(j, curr);
                curr = temp;
                k2 /= 4 * k1 - k2;
                k1 = k2 + 1;
            }
            t.add(curr);
            k *= 2;
            h /= 2;
            i ++;
        } while (Math.abs(last - curr) > eps);
        return t.get(t.size() - 1);
    }

}
