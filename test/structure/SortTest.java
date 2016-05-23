package structure;

import java.util.*;
import java.util.stream.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class SortTest {

    static Random rand = new Random();

    @Test
    public void testingMergeSort() {
        Integer[] a = { 3, 6, 7, 0, 2, 5, 1, 9, 8, 4 };
        Sort.mergeSort(a, true);
        assertArrayEquals(new Integer[]{0,1,2,3,4,5,6,7,8,9}, a);

        Integer[] b = new Integer[1000000];
        Integer[] c = new Integer[1000000];
        for (int i = 0; i < b.length; i ++) {
            b[i] = rand.nextInt();
            c[i] = b[i];
        }
        long time1 = System.currentTimeMillis();
        Arrays.sort(c);
        long time2 = System.currentTimeMillis();
        Sort.mergeSort(b, true);
        long time3 = System.currentTimeMillis();
        assertArrayEquals(c, b);
        System.out.println((time3 - time2) + " " + (time2 - time1));
    }

    @Test
    public void testingQuickSelect() {
        Integer[] a = { 3, 6, 7, 0, 2, 5, 1, 9, 8, 4 };
        Integer res = Sort.quickSelect(a, 5);
        Arrays.sort(a);
        Integer ans = a[4];
        assertEquals(ans, res);

        int tot = 100000;
        Integer[] b = new Integer[tot];
        for (int i = 0; i < tot; i ++)
            b[i] = rand.nextInt();
        int K = rand.nextInt(tot) + 1;
        res = Sort.quickSelect(b, K);
        Arrays.sort(b);
        ans = b[K - 1];
        assertEquals(ans, res);
    }
}