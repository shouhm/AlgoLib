package graph;

import java.util.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class LowestCommonAncestorTest {
    @Test
    public void testLCAOnlineTwoChains() {
        int[][] edges = {
                {0,1},{1,2},{2,3},{3,4},{4,5},{5,6},{6,7},{7,8},
                {15,14},{14,13},{13,12},{12,11},{11,10},{10,9},{9,8}
        };
        int N = 16;
        int root = 8;
        LowestCommonAncestor LCASolver = new LowestCommonAncestor(N, root, GraphTestUtil.edgeArrayToList(edges));
/*
        for (int i = 0; i < N; i ++) {
            System.out.format("%d:\t", i);
            for (int j = 0; j < LCASolver.maxH; j ++) {
                System.out.print(LCASolver.anc[i][j] + " ");
            }
            System.out.println();
        }
*/
        assertEquals(8, LCASolver.lca(0, 15));
    }

    @Test
    public void testLCAOnlineComplex() {
        int[][] edges = {
                {0, 1}, { 0, 2 }, { 0, 7 },
                {1, 3}, { 1, 4 }, { 2, 5 }, { 2, 6 }, { 7, 8 }, { 7, 9 },
                { 3, 10 }, { 5, 11 }, { 5, 12 }, { 6, 14 }, { 6, 15 }, { 9, 16 }, { 9, 17 },
                { 12, 13 }
        };
        int N = 18;
        int rootNode = 0;
        LowestCommonAncestor LCASolver = new LowestCommonAncestor(N, rootNode, GraphTestUtil.edgeArrayToList(edges));

        assertEquals(0, LCASolver.lca(0, 0));
        assertEquals(0, LCASolver.lca(10, 11));
        assertEquals(5, LCASolver.lca(11, 12));
        assertEquals(5, LCASolver.lca(5, 13));
        assertEquals(2, LCASolver.lca(13, 15));
        assertEquals(0, LCASolver.lca(13, 17));
    }
}