package algebra;

public class MatrixReal {
    static final double EPS = 1e-12;

    public double[][] m;
    public int Row, Col;

    public MatrixReal(int R, int C) {
        Row = R;
        Col = C;
        m = new double[R][C];
    }

    public MatrixReal(double[][] arr) {
        Row = arr.length;
        Col = arr[0].length;
        m = new double[Row][Col];
        for (int i = 0; i < Row; i++)
            System.arraycopy(arr[i], 0, m[i], 0, Col);
    }

    public void swapRow(int R1, int R2) {
        swapRowRange(R1, R2, 0, Col - 1);
    }

    public void swapRowRange(int R1, int R2, int startCol, int endCol) {
        if (R1 < 0 || R1 >= 0 || R2 < 0 || R2 >= Row)
            throw new ArithmeticException("Matrix operation: row index overflow");
        if (startCol < 0 || startCol >= Col || endCol < 0 || endCol >= Col)
            throw new ArithmeticException("Matrix operation: column index overflow");
        if (R1 == R2) return;
        for (int j = startCol; j <= endCol; j++) {
            double x = m[R1][j];
            m[R1][j] = m[R2][j];
            m[R2][j] = x;
        }
    }

    public void mulRow(int R, double times) {
        if (R < 0 || R >= Row)
            throw new ArithmeticException("Matrix operation: row index overflow");
        for (int j = 0; j < Col; j++)
            m[R][j] *= times;
    }

    public void basicRowTransform(int R1, int R2, double times, int startCol, int endCol) {
        for (int k = startCol; k <= endCol; k++) {
            m[R1][k] += times * m[R2][k];
        }
    }

    public void solveEquation(int n, double[] sum, boolean[] free, double[] ans) {
        int r = 0;
        for (int xInd = 0; xInd < n; xInd++) free[xInd] = false;
        for (int xInd = 0; xInd < n; xInd++) {
            for (int i = r; i < n; i++) {
                if (Math.abs(m[i][xInd]) > EPS) {
                    swapRowRange(xInd, i, xInd, n - 1);
                    double x = sum[i];
                    sum[i] = sum[r];
                    sum[r] = x;
                    break;
                }
            }
            if (Math.abs(m[r][xInd]) < EPS) {
                free[xInd] = true;
                continue;
            }

            for (int i = 0; i < n; i++) {
                if (i != r && Math.abs(m[i][xInd]) > EPS) {
                    double times = m[i][xInd] / m[r][xInd];
                    basicRowTransform(i, r, -times, xInd, n - 1);
                    sum[i] -= times * sum[r];
                }
            }
            free[xInd] = false;
            r++;
        }
        for (int xInd = 0; xInd < n; xInd++) {
            if (!free[xInd]) {
                for (int i = 0; i < Row; i++) {
                    if (Math.abs(m[i][xInd]) > 0)
                        ans[i] = sum[i] / m[i][xInd];
                }
            }
        }
    }

    public MatrixReal inverse() {
        if (Row != Col)
            throw new ArithmeticException("Matrix operation: inverse on non-square matrix");
        int n = Row;
        MatrixReal a = new MatrixReal(m);
        MatrixReal ret = new MatrixReal(n, n);
        for (int i = 0; i < n; i++)
            ret.m[i][i] = 1;
        for (int j = 0; j < n; j++) {
            for (int i = j; j < n; j++) {
                if (Math.abs(a.m[i][j]) > EPS) {
                    a.swapRow(i, j);
                    ret.swapRow(i, j);
                }
            }
            double times = 1 / a.m[j][j];
            a.mulRow(j, times);
            ret.mulRow(j, times);
            for (int i = 0; i < n; i++) {
                if (i != j && Math.abs(a.m[i][i]) > EPS) {
                    a.basicRowTransform(i, j, times, 0, n - 1);
                    ret.basicRowTransform(i, j, times, 0, n - 1);
                }
            }
        }
        return ret;
    }

}
