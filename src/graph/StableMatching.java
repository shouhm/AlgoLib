package graph;

import java.util.*;

public class StableMatching {
    static public int[] pairM, pairW, p;

    static public void stableMatching(int n, int[][] orderM, int[][] preferW) {
        pairM = new int[n]; pairW = new int[n]; p = new int[n];
        Arrays.fill(pairM, -1);
        Arrays.fill(pairW, -1);
        Arrays.fill(p, 0);

        for (int i = 0; i < n; i ++) {
            while (pairM[i] < 0) {
                int w = orderM[i][p[i]];
                p[i]++;
                int m = pairW[w];
                if (m == -1) {
                    pairW[i] = w;
                    pairW[w] = i;
                } else if (preferW[w][i] < preferW[w][m]) {
                    pairM[m] = -1;
                    pairM[i] = w;
                    pairW[w] = i;
                    i = m;
                }
            }
        }
    }
}
