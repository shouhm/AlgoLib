package graph;

import java.util.*;
import java.math.*;

import graph.Graph.Edge;

/**
 * Created by shouhm on 16/5/14.
 */
public class TarjanSCC {

    static int N;
    static ArrayList<Integer>[] g;
    static int[] dfn, low;
    static int counter;
    static ArrayList<Integer> component;
    static Stack<Integer> st;
    static int[] state;
    static ArrayList<List<Integer>> ret;

    static public List<List<Integer>> TarjanSCC(int n, List<Edge> directedEdges) {
        ret = new ArrayList<>();

        N = n;
        g = Graph.getDirectedAdjList(N, directedEdges);
        dfn = new int[N];
        low = new int[N];
        counter = 0;
        st = new Stack<>();
        state = new int[N]; Arrays.fill(state, 0);

        for (int x = 0; x < N; x ++) {
            if (state[x] == 0) DFS(x);
        }

        return ret;
    }

    static private void DFS(int x) {
        dfn[x] = counter;
        low[x] = counter;
        counter ++;
        st.push(x);
        state[x] = 1;

        for (int y : g[x]) {
            if (state[y] == 0) {
                DFS(y);
                low[x] = Math.min(low[x], low[y]);
            } else if (state[y] == 1){
                low[x] = Math.min(low[x], dfn[y]);
            }
        }
        if (low[x] == dfn[x]) {
            ArrayList<Integer> component = new ArrayList<>();
            int c;
            do {
                c = st.pop();
                component.add(c);
                state[c] = 2;
            } while (c != x);
            ret.add(component);
        }
    }
}
