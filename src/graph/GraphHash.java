package graph;

import java.util.*;

import graph.Graph.Edge;

/**
 * Created by shouhm on 16/5/22.
 */
public class GraphHash {
    /**
     * F_{t}(i) = (F_{t-1}(i) * A + Sum(i->j)[ F_{t-1}(j) * B ] + Sum(j->i)[ F_{t-1}(j) * C ] + D * (i == a)) mod P
     */

    static private final int K = 10;
    static private final long A = 2;
    static private final long B = 3;
    static private final long C = 5;
    static private final long D = 7;
    static private final long P = 1000000007;

    static public long[] calcGraphHash(int n, List<Edge> edges) {
        long[] h = new long[n];
        long[] f = new long[n];
        for (int x = 0; x < n; x ++) {
            Arrays.fill(f, 1L);
            for (int z = 1; z <= K; z ++) {
                long[] ff = Arrays.copyOf(f, n);
                for (int i = 0; i < n; i ++) f[i] *= A;
                for (Edge e : edges) {
                    f[e.in] += ff[e.out] * B;
                    f[e.out] += ff[e.in] * C;
                }
                f[x] += D;
                for (int i = 0; i < n; i ++) f[i] %= P;
            }
            h[x] = f[x];
        }
        Arrays.sort(h);
        return h;
    }
}
