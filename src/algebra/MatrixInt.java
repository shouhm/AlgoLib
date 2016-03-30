package algebra;

public class MatrixInt {
    public int[][] m;
    public int Row, Col;

    public MatrixInt(int R, int C) {
        Row = R;
        Col = C;
        m = new int[Row][Col];
    }

    public MatrixInt(int[][] arr) {
        Row = arr.length;
        Col = arr[0].length;
        m = new int[Row][Col];
        for (int i = 0; i < Row; i++)
            System.arraycopy(arr[i], 0, m[i], 0, Col);
    }

    public void swapRow(int R1, int R2) {
        if (R1 < 0 || R1 >= Row || R2 < 0 || R2 >= Row)
            throw new ArithmeticException("Matrix operation: row index overflow");
        for (int j = 0; j < Col; j++) {
            int x = m[R1][j];
            m[R1][j] = m[R2][j];
            m[R2][j] = x;
        }
    }

    static public MatrixInt add(MatrixInt a, MatrixInt b) {
        if (a.Row != b.Row || a.Col != b.Col)
            throw new ArithmeticException("Matrix addition: dimension mismatch");
        MatrixInt ret = new MatrixInt(a.Row, a.Col);
        for (int i = 0; i < a.Row; i++) {
            for (int j = 0; j < a.Col; j++) {
                ret.m[i][j] = a.m[i][j] + b.m[i][j];
            }
        }
        return ret;
    }

    static public MatrixInt mul(MatrixInt a, MatrixInt b) {
        if (a.Col != b.Row)
            throw new ArithmeticException("Matrix multiplication: dimension mismatch");
        MatrixInt ret = new MatrixInt(a.Row, a.Col);
        for (int i = 0; i < a.Row; i++) {
            for (int j = 0; j < b.Col; j++) {
                ret.m[i][j] = 0;
                for (int k = 0; k < a.Col; k++) {
                    ret.m[i][j] += a.m[i][k] * b.m[k][j];
                }
            }
        }
        return ret;
    }
}
