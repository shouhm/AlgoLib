package algebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class NumberTheory {

    static final int MILLER_TEST_TOTAL = 7;
    static final long[] MILLER_TEST_KEY = {2, 3, 5, 7, 11, 13, 17};

    static public long gcd(long a, long b) {
        return (b == 0 ? a : gcd(b, a % b));
    }

    static public long[] extGcd(long a, long b) {
        if (b == 0) {
            return new long[]{a, 1, 0};
        } else {
            long[] res = extGcd(b, a % b);
            long d = res[0], x = res[1], y = res[2];
            return new long[]{d, y, x - (a / b) * y};
        }
    }

    static public long uniformMod(long a, long m) {
        return (a % m + m) % m;
    }

    static public long invMod(long a, long m) {
        long[] r = extGcd(a, m);
        long d = r[0], x = r[1], y = r[2];
        if (d == 1)
            return uniformMod(x, m);
        else
            return -1;
    }

    /*
     * Solve linear congruence equation ax = b(mod n)
     *
     * Let d = gcd(a, n), if b % d > 0, there must be no solution.
     * Then convert this equation to: a' = a/d, b' = b/d, n' = n/d
     * a'x = b'(mod n')
     *
     * Now, gcd(a', n') = 1. Using extended gcd, we can have:
     * a' * u + n' * v = 1, i.e. a'u = 1(mod n'), a' = u^(-1)(mod n)
     *
     * Thus x = (a')^(-1) * b' (mod n') = u * b'(mod n')
     *
     * To populate the solution x to Z_n, there should be d solutions
     * in the form: x + i * n' for all i = 0..d-1
     */
    static public long[] linearCongruence(long a, long b, long n) {
        long[] r = extGcd(a, n);
        long d = r[0], u = r[1], v = r[2];
        if (b % d != 0) return new long[]{};
        long base_sol = u * (b / d) % n;
        long[] sol = new long[(int) d];
        for (int i = 0; i < d; i++)
            sol[i] = uniformMod((base_sol + i * (n / d)), n);
        Arrays.sort(sol);
        return sol;
    }

    //Chinese Reminder Theorem
    long chineseRemainder(long[] a, long[] m) {
        long n = a.length;
        long M = 1;
        for (long num : m) M *= num;
        long x = 0;
        for (int i = 0; i < n; i++) {
            long w = M / m[i];
            long[] r = extGcd(m[i], w);
            long d = r[0], u = r[1], v = r[2];
            x = (x + v * w * a[i]) % M;
        }
        x = uniformMod(x, M);
        return x;
    }

    static public long powerMod(long x, long k, long P) {
        long ret = 1;
        while (k > 0) {
            if (k % 2 == 1) ret = ret * x % P;
            k /= 2;
            x = x * x % P;
        }
        return ret;
    }

    static public long[] primeDivisors(long a) {
        ArrayList<Long> ret = new ArrayList<>();
        for (long i = 2; i * i <= a; i++) {
            if (a % i == 0) {
                while (a % i == 0) {
                    a /= i;
                }
                ret.add(i);
            }
        }
        if (a > 1) ret.add(a);
        return ret.stream().mapToLong(x -> x).toArray();
    }

    static public boolean gTest(long g, long P, long[] testPrimes) {
        for (long testP : testPrimes) {
            if (powerMod(g, (P - 1) / testP, P) == 1)
                return false;
        }
        return true;
    }

    static public long findG(long P) {
        long[] testPrimes = primeDivisors(P - 1);
        for (long g = 1; g < P; g++) {
            if (gTest(g, P, testPrimes))
                return g;
        }
        return -1;
    }

    //TODO test
    long quadraticResidue(long a, long n) {
        //check if there is a solution
        if (n == 2) return a % n;
        if (powerMod(a, (n - 1) / 2, n) != 1) return -1;
        long x;
        if (n % 4 == 3)
            x = powerMod(a, (n + 1) / 4, n);
        else {
            long b = 1;
            for (; powerMod(b, (n - 1) / 2, n) == 1; b++) ;
            long i = (n - 1) / 2, k = 0;
            do {
                i /= 2;
                k /= 2;
                if (powerMod(a, i, n) * (powerMod(b, k, n) + 1) % n == 0)
                    k += (n - 1) / 2;
            } while (i % 2 == 0);
            x = (powerMod(a, (i + 1) / 2, n) * powerMod(b, k / 2, n)) % n;
        }
        if (x * 2 > n)
            x = n - x;
        return x;
    }

    static public long discreteLog(long x, long n, long m) {
        long s = (long) (Math.sqrt((double) m));
        while (s * s <= m) s++;

        HashMap<Long, Long> rec = new HashMap<>();
        long mul = 1;
        for (long i = 0; i < s; i++, mul = (mul * x) % m)
            rec.put(mul, i);
        for (long i = 0, cur = 1; i < s; i++, cur = (cur * mul) % m) {
            long remain = n * powerMod(cur, m - 2, m) % m;
            if (rec.containsKey(remain)) {
                return i * s + rec.get(remain);
            }
        }
        return -1;
    }

    //TODO test
    static public long[] discreteRoot(long a, long n, long P) {
        long g = findG(P);
        long m = discreteLog(g, a, P);
        if (a == 0)
            return new long[]{0};
        if (m == -1)
            return new long[]{};
        long[] sol = linearCongruence(a, m, P - 1);
        long[] ret = LongStream.of(sol).map(y -> powerMod(g, y, m))
                .distinct().toArray();
        return ret;
    }

    static public boolean millerRabinTest(long a, long n) {
        long r = 0;
        long s = n - 1;
        while (s % 2 == 0) {
            s /= 2;
            r++;
        }
        long x = powerMod(a, s, n);
        if (x == 1) return true;
        for (long j = 0; j < r; j++) {
            if (x == n - 1) return true;
            x = (x * x) % n;
        }
        return false;
    }

    static public boolean isPrime(long n) {
        if (n < MILLER_TEST_KEY[MILLER_TEST_TOTAL - 1])
            return (Arrays.binarySearch(MILLER_TEST_KEY, n) >= 0);
        for (long p : MILLER_TEST_KEY)
            if (!millerRabinTest(p, n)) return false;
        return true;
    }

    static public int[] eratosthenesSieve(int n) {
        boolean[] valid = new boolean[n + 1];
        Arrays.fill(valid, true);
        for (int i = 2; i * i <= n; i++) {
            if (valid[i]) {
                for (int j = i * i; j <= n; j += i)
                    valid[j] = false;
            }
        }
        return IntStream.range(2, n + 1).filter(i -> valid[i]).toArray();
    }

    static public int[] eulerSieve(int n) {
        boolean[] valid = new boolean[n + 1];
        Arrays.fill(valid, true);
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (valid[i])
                primes.add(i);
            for (int j = 0; j < primes.size(); j++) {
                if (primes.get(j) * i > n) break;
                valid[i * primes.get(j)] = false;
                if (i % primes.get(j) == 0) break;
            }
        }
        return primes.stream().mapToInt(x -> x).toArray();
    }

    static public int[] eulerFunc(int n) {
        boolean[] valid = new boolean[n + 1];
        Arrays.fill(valid, true);
        ArrayList<Integer> primes = new ArrayList<>();
        int[] phi = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            if (valid[i]) {
                primes.add(i);
                phi[i] = i - 1;
            }
            for (int j = 0; j < primes.size(); j++) {
                int x = primes.get(j) * i;
                if (x > n) break;
                valid[x] = false;
                if (i % primes.get(j) == 0) {
                    phi[x] = phi[i] * primes.get(j);
                    break;
                } else {
                    phi[x] = phi[i] * (primes.get(j) - 1);
                }
            }
        }
        return phi;
    }

    static public int[] mobiusFunc(int n) {
        boolean[] valid = new boolean[n + 1];
        Arrays.fill(valid, true);
        ArrayList<Integer> primes = new ArrayList<>();
        int[] mobius = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            if (valid[i]) {
                primes.add(i);
                mobius[i] = -1;
            }
            for (int j = 0; j < primes.size(); j++) {
                int x = primes.get(j) * i;
                if (x > n) break;
                valid[x] = false;
                if (i % primes.get(j) == 0) {
                    mobius[x] = 0;
                    break;
                } else {
                    mobius[x] = -mobius[i];
                }
            }
        }
        return mobius;
    }

    private static long rhoPolynomial(long x) {
        return x * x + 1;
    }

    public static long pollardRho(long n, boolean random) {
        long x1, x2, divisor;
        if (random) {
            Random rand = new Random();
            x1 = rand.nextLong() % n;
            x2 = x1;
        } else {
            x1 = 2;
            x2 = 2;
        }
        if (n % 2 == 0)
            return 2;
        do {
            x1 = rhoPolynomial(x1) % n;
            x2 = rhoPolynomial(rhoPolynomial(x2) % n) % n;
            divisor = gcd(Math.abs(x1 - x2), n);
        } while (divisor == 1);
        return divisor;
    }

    /*
     * Farey Sequence:
     * //TODO
     */

    /*
     * Pell equation:
     *
     * //TODO comments
     * //TODO more tests
     */
    public static long[] pell_equation(long n) {
        ArrayList<Long> p, q, a, g, h;
        p = new ArrayList<>(Arrays.asList(0L, 1L));
        q = new ArrayList<>(Arrays.asList(1L, 0L));
        long init_a = (long) (Math.sqrt(n));
        g = new ArrayList<>(Arrays.asList(0L, 0L));
        h = new ArrayList<>(Arrays.asList(0L, 1L));
        a = new ArrayList<>(Arrays.asList(0L, 0L));
        int i = 2;
        a.add(init_a);
        for (; ; i++) {
            g.add(-g.get(i - 1) + a.get(i) * h.get(i - 1));
            g.add((n - g.get(i) * g.get(i)) / h.get(i - 1));
            a.add((g.get(i) + init_a) / h.get(i));
            p.add(a.get(i) * p.get(i - 1) + p.get(i - 2));
            q.add(a.get(i) * q.get(i - 1) + q.get(i - 2));
            if (p.get(i) * p.get(i) - n * q.get(i) * q.get(i) == 1)
                return new long[]{p.get(i), q.get(i)};
        }
    }
}
