package numeric;

public class BigInt {
    static public final int BASE = 10000;
    static public final int BASE_LEN = 4;
    static public final int BASE_BIN_EXP = 8192;
    static public final int BASE_BIN_INDEX = 13;
    static public final int[] BASE_ARR =
            {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    static final char SPLIT_CHAR = ',';

    public int[] d;
    public int len;

    private void shrinkMemory() {

    }

    private void expandMemory(int pos) {
        System.out.println("expand Memory!");
        int expLen = 1;
        while (expLen - 1 < pos) expLen *= 2;
        int[] expand = new int[expLen];
        System.arraycopy(d, 0, expand, 0, len);
    }

    private void removePrefixZero() {
        while (len > 1 && d[len - 1] == 0) len--;
    }

    private void setDigit(int i, int v) {
        if (i >= d.length)
            expandMemory(i);
        d[i] = v;
    }

    //-------------------------------Constructors of BigInt---------------------------//
    public BigInt(BigInt num) {
        len = num.len;
        d = new int[len];
        System.arraycopy(num.d, 0, d, 0, len);
    }

    public BigInt(char[] s) {
        len = s.length / BASE_LEN + (s.length % BASE_LEN > 0 ? 1 : 0);
        d = new int[len];
        for (int i = s.length - 1, pos = 0; i >= 0; i -= BASE_LEN, pos++) {
            int word = 0;
            for (int j = 0; i - j >= 0 && j < BASE_LEN; j++)
                word += (s[i - j] - '0') * BASE_ARR[j];
            d[pos] = word;
        }
    }

    public BigInt(String s) {
        this(s.toCharArray());
    }

    public BigInt(Long num) {
        this(num.toString());
    }

    public BigInt(Integer num) {
        this(num.toString());
    }

    public BigInt() {
        this("0");
    }

    //--------------------------------- Output of BigInt -----------------------------//
    @Override
    public String toString() {
        return generateString(false);
    }

    public String toSplitString() {
        return generateString(true);
    }

    private String generateString(boolean split) {
        StringBuilder sb = new StringBuilder();
        sb.append(d[len - 1]);
        for (int i = len - 1 - 1; i >= 0; i--) {
            if (split) {
                sb.append(SPLIT_CHAR);
            }
            sb.append(String.format("%04d", d[i]));
        }
        return sb.toString();
    }

    //---------------------------------- Binary Operations ---------------------------//
    static private int compareDataArray(int[] arr1, int[] arr2) {
        int len1 = arr1.length, len2 = arr2.length;
        if (len1 != len2)
            return Integer.signum(len1 - len2);
        for (int i = len1 - 1; i >= 0; i--) {
            if (arr1[i] != arr2[i])
                return Integer.signum(arr1[i] - arr2[i]);
        }
        return 0;
    }

    static public boolean equal(BigInt a, BigInt b) {
        int cmp = compareDataArray(a.d, b.d);
        return (cmp == 0);
    }

    static public boolean greater(BigInt a, BigInt b) {
        int cmp = compareDataArray(a.d, b.d);
        return (cmp > 0);
    }

    static public boolean geq(BigInt a, BigInt b) {
        int cmp = compareDataArray(a.d, b.d);
        return (cmp >= 0);
    }

    static public boolean less(BigInt a, BigInt b) {
        int cmp = compareDataArray(a.d, b.d);
        return (cmp < 0);
    }

    static public boolean leq(BigInt a, BigInt b) {
        int cmp = compareDataArray(a.d, b.d);
        return (cmp <= 0);
    }

    //----------------------------------Four operations----------------------------------//
    static public BigInt add(BigInt a, BigInt b) {
        long time1 = System.currentTimeMillis();
        BigInt c = new BigInt();
        c.len = Math.max(a.len, b.len);
        c.d = new int[c.len + 1];
        int carry = 0;
        long time2 = System.currentTimeMillis();
        for (int i = 0; i < c.len; i++) {
            int numA = (i < a.len ? a.d[i] : 0);
            int numB = (i < b.len ? b.d[i] : 0);
            carry = carry + numA + numB;
            //use direct index to speedup
            if (carry >= BASE) {
                c.d[i] = carry - BASE;
                carry = 1;
            } else {
                c.d[i] = carry;
                carry = 0;
            }
        }
        long time3 = System.currentTimeMillis();
        while (carry > 0) {
            c.setDigit(c.len, carry % BASE);
            c.len++;
            carry /= BASE;
        }
        long time4 = System.currentTimeMillis();
        System.out.println("A BigInt add pass: " + time1 + " " + time2 + " " + time3 + " " + time4);
        return c;
    }

    static public BigInt minus(BigInt a, BigInt b) {
        BigInt c = new BigInt();
        c.len = Math.max(a.len, b.len);
        c.d = new int[c.len];
        int carry = 0;
        for (int i = 0; i < c.len; i++) {
            int numA = (i < a.len ? a.d[i] : 0);
            int numB = (i < b.len ? b.d[i] : 0);
            carry = carry + a.d[i] - b.d[i];
            if (carry < 0) {
                c.setDigit(i, carry + BASE);
                carry = -1;
            } else {
                c.setDigit(i, carry);
                carry = 0;
            }
        }
        c.removePrefixZero();
        return c;
    }

    static public BigInt mul(BigInt a, BigInt b) {
        BigInt c = new BigInt();
        c.len = a.len + b.len;
        c.d = new int[c.len + 1];
        for (int i = 0; i < a.len; i++) {
            int carry = 0;
            for (int j = 0; j < b.len; j++) {
                carry = carry + a.d[i] * b.d[j] + c.d[i + j];
                c.setDigit(i + j, carry % BASE);
                carry /= BASE;
            }
            int j = i + b.len;
            while (carry > 0) {
                carry = carry + c.d[j];
                c.setDigit(j, carry % BASE);
                carry /= BASE;
            }
        }
        c.removePrefixZero();
        return c;
    }

    static public BigInt div(BigInt a, BigInt b) {
        BigInt c, d;
        d = new BigInt(a);
        c = new BigInt();

        BigInt[] bExp = new BigInt[BASE_BIN_INDEX + 1];
        bExp[0] = new BigInt(b);
        for (int i = 1; i <= BASE_BIN_INDEX; i++)
            bExp[i] = mul(bExp[i - 1], new BigInt(2));

        c.len = Math.max(1, a.len - b.len + 1);
        c.d = new int[c.len + 1];

        if (a.len >= b.len) {
            for (int i = a.len - b.len; i >= 0; i--) {
                for (int j = BASE_BIN_INDEX, bitNum = BASE_BIN_EXP; j >= 0; j--, bitNum /= 2) {
                    if (shiftLeq(bExp[j], d, i)) {
                        shiftMinus(d, bExp[j], i);
                        c.setDigit(i, c.d[i] + bitNum);
                    }
                }
            }
        }

        c.removePrefixZero();
        return c;
    }

    static private boolean shiftLeq(BigInt a, BigInt b, int offset) {
        if (a.len + offset != b.len) return a.len + offset < b.len;
        for (int i = a.len - 1; i >= 0; i--) {
            int wordA = a.d[i], wordB = b.d[i + offset];
            if (wordA != wordB) return wordA < wordB;
        }
        return true;
    }

    static private void shiftMinus(BigInt a, BigInt b, int offset) {
        int carry = 0;
        for (int i = 0; i < a.len - offset; i++) {
            carry = carry + a.d[i + offset] - (i < b.len ? b.d[i] : 0);
            if (carry < 0) {
                a.setDigit(i + offset, carry + BASE);
                carry = -1;
            } else {
                a.setDigit(i + offset, carry);
                carry = 0;
            }
        }
        a.removePrefixZero();
    }
}
