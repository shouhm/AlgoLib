package graph;

import java.util.*;

import org.junit.Test;

import static org.junit.Assert.*;

import graph.Graph.Edge;

/**
 * Created by shouhm on 16/5/13.
 */
public class KosarajuSCCTest {
    @Test
    public void testKosaraju() {
        int N = 8;
        int[][] dirEdgesArr = {
                {0, 1},
                {1, 2}, {1, 4}, {1, 5},
                {2, 3}, {2, 6},
                {3, 2}, {3, 7},
                {4, 0}, {4, 5},
                {5, 6},
                {6, 5}, {6, 7},
                {7, 7}
        };
        ArrayList<Edge> dirEdges = GraphTestUtil.edgeArrayToList(dirEdgesArr);
        List<List<Integer>> ans = KosarajuSCC.KosajaruSCC(N, dirEdges);
        assertEquals(ans.size(), 4);
    }
}
