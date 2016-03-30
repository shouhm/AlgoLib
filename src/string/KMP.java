package string;

public class KMP {
    static public int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        int[] fail = getFailLink(needle);
        int p = -1;
        for (int i = 0; i < n; i++) {
            while (p >= 0 && needle.charAt(p + 1) != haystack.charAt(i)) p = fail[p];
            if (haystack.charAt(i) == needle.charAt(p + 1)) {
                p++;
                if (p == m - 1) {
                    return i - (m - 1);
                }
            }
        }
        return -1;
    }

    static public int[] getFailLink(String s) {
        int n = s.length();
        int[] fail = new int[n];
        fail[0] = -1;
        int p = -1;
        for (int i = 1; i < n; i++) {
            while (p >= 0 && s.charAt(p + 1) != s.charAt(i)) p = fail[p];
            if (s.charAt(i) == s.charAt(p + 1)) {
                fail[i] = p + 1;
                p++;
            } else {
                fail[i] = p;
            }
        }
        return fail;
    }
}
