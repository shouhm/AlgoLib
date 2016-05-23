package graph;

import java.util.*;

import graph.Graph.Edge;

/**
 * Created by shouhm on 16/5/14.
 */
public class TwoSAT {
    public enum Oper {
        ADD, OR
    }

    static public class LogicalVar {
        int index;
        int pre;
        public LogicalVar(int _index, int _pre) {
            index = _index; pre = _pre;
        }
    }

    static public class BinExp {
        public LogicalVar p, q;
        public BinExp(LogicalVar _p, LogicalVar _q) {
            p = _p; q = _q;
        }
    }

    static public boolean solveTwoSAT(int n, int m, List<BinExp> exp, int[] sol) {
        Arrays.fill(sol, -1);
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m; i ++) {
            edges.add(new Edge(
                    exp.get(i).p.index + exp.get(i).p.pre * n,
                    exp.get(i).q.index + (1 - exp.get(i).q.pre) * n));
            edges.add(new Edge(
                    exp.get(i).q.index + exp.get(i).q.pre * n,
                    exp.get(i).p.index + (1 - exp.get(i).p.pre) * n));
        }

        ArrayList<Integer>[] g = Graph.getDirectedAdjList(n, edges);
        List<List<Integer>> SCCs = KosarajuSCC.KosajaruSCC(n, edges);
        int[] sccColor = new int[2 * n];
        for (int i = 0; i < SCCs.size(); i ++) {
            List<Integer> scc = SCCs.get(i);
            for (int x : scc) {
                sccColor[x] = i;
            }
        }
        for (int i = 0; i < n; i ++) {
            if (sccColor[i] == sccColor[i + n])
                return false;
        }

        for (int i = 0; i < SCCs.size(); i ++) {
            List<Integer> scc = SCCs.get(i);
            int value = 1;
            for (int x : scc) {
                if (getValue(sol, n, x) == 0)
                    value = 0;
                for (int y : g[x]) {
                    if (getValue(sol, n, y) == 0)
                        value = 0;
                }
                if (value == 0) break;
            }
            for (int x : scc) {
                if (x > n)
                    sol[x - n] = 1 - value;
                else
                    sol[x] = value;
            }
        }
        return true;
    }

    static private int getValue(int[] sol, int n, int x) {
        int r = (x > n ? x - n : x);
        if (sol[r] == -1) return -1;
        return x > n ? (1 - sol[r]) : sol[r];
    }
}
