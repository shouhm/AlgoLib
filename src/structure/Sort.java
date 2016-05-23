package structure;

import java.util.*;

public class Sort {

    static private Random rand = new Random();

    static public <T extends Comparable<T>> T quickSelect(T[] a, int K) {
        quickSelectRecur(a, K, 0, a.length - 1);
        return a[K-1];
    }

    static public <T extends Comparable<T>> void quickSelectRecur(T[] a, int K, int L, int R) {
        if (L >= R) return;
        int l = L, r = R;
        T mid = a[L + rand.nextInt(R - L + 1)];
        while (l <= r) {
            while (a[l].compareTo(mid) < 0)
                l ++;
            while (a[r].compareTo(mid) > 0)
                r --;
            if (l <= r) {
                T t; t = a[l]; a[l] = a[r]; a[r] = t;
                l ++; r --;
            }
        }
        if (L <= r && K <= r - L + 1) quickSelectRecur(a, K, L, r);
        if (l <= R && K >= l - L + 1) quickSelectRecur(a, K - (l - L), l, R);
    }

    static public <T extends Comparable<T>> void mergeSort(T[] a, boolean inc) {
        temp = new Object[a.length];
        mergeSortRecur(a, 0, a.length - 1, inc);
    }

    static private Object[] temp;

    @SuppressWarnings("unchecked")
    static public <T extends Comparable<T>> void mergeSortRecur(T[] a, int L, int R, boolean inc) {
        if (L >= R) return;
        int mid = (L + R) / 2;
        int p1 = L, p2 = mid + 1, p = L;
        mergeSortRecur(a, L, mid, inc);
        mergeSortRecur(a, mid + 1, R, inc);
        while (p1 <= mid && p2 <= R) {
            if ((inc && a[p1].compareTo(a[p2]) <= 0) || (!inc && a[p1].compareTo(a[p2]) >= 0)) {
                temp[p] = a[p1];
                p ++; p1++;
            } else {
                temp[p] = a[p2];
                p ++; p2 ++;
            }
        }
        while (p1 <= mid) {
            temp[p] = a[p1];
            p ++; p1 ++;
        }
        while (p2 <= R) {
            temp[p] = a[p2];
            p ++; p2 ++;
        }
        for (int i = L; i <= R; i ++)
            a[i] = (T)(temp[i]);
    }
}
