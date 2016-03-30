package numeric;

import algebra.NumberTheory;

public class Fourier {

    static final long NTT_G = 3;
    static final long NTT_P = 1004535809;

    static private <T> void rader(T[] a, int len) {
        for (int i = 1, j = 0; i < len - 1; i++) {
            for (int s = len; (~j & s) > 0; j ^= s >>= 1) {
            }
            if (i < j) {
                T x;
                x = a[i];
                a[i] = a[j];
                a[j] = x;
            }
        }
    }

    static public void FFT(Complex[] a, int len, int oper) {
        rader(a, len);
        for (int i = 2; i <= len; i *= 2) {
            Complex wn = new Complex(Math.cos(2 * Math.PI / i), Math.sin(2 * Math.PI / i));
            for (int j = 0; j < len; j += i) {
                Complex w = new Complex(1.0, 0.0);
                for (int k = 0; k < i / 2; k++) {
                    Complex u = a[j + k];
                    Complex z = Complex.mul(a[j + i / 2 + k], w);
                    a[j + k] = Complex.add(u, z);
                    a[j + i / 2 + k] = Complex.minus(u, z);
                    w = Complex.mul(w, wn);
                }
            }
        }
    }

    static public void NTT(Long[] a, int len, int oper) {
        rader(a, len);
        for (int i = 2, id = 0; i <= len; i *= 2, id++) {
            long wn = NumberTheory.powerMod(NTT_G, (NTT_P - 1) / i, NTT_P);
            for (int j = 0; j < len; j += i) {
                long w = 1;
                for (int k = 0; k < i / 2; k++) {
                    long u = a[j + k];
                    long z = a[j + k + i / 2] * w % NTT_P;
                    a[j + k] = u + z;
                    if (a[j + k] >= NTT_P) a[k] -= NTT_P;
                    a[j + k + i / 2] = u - z + NTT_P;
                    if (a[j + k + i / 2] >= NTT_P) a[j + k + i / 2] -= NTT_P;
                    w = w * wn % NTT_P;
                }
            }
        }
        if (oper == -1) {
            for (int i = 1; i < len / 2; i++) {
                Long x = a[i];
                a[i] = a[len - i];
                a[len - i] = x;
            }
            long inv = NumberTheory.powerMod(len, NTT_P - 2, NTT_P);
            for (int i = 1; i < len; i++)
                a[i] = a[i] * inv % NTT_P;
        }
    }

    static public long[] Convolution(long[] a, long[] b, int len) {
        Long[] A = new Long[len];
        Long[] B = new Long[len];
        for (int i = 0; i < len; i++) {
            A[i] = a[i];
            B[i] = b[i];
        }
        NTT(A, len, 1);
        NTT(B, len, 1);
        for (int i = 0; i < len; i++) {
            A[i] = A[i] * B[i] % NTT_P;
        }
        NTT(A, len, -1);
        long[] ret = new long[len];
        for (int i = 0; i < len; i++)
            ret[i] = A[i];
        return ret;
    }
}
