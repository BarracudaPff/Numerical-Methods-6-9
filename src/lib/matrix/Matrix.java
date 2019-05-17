package lib.matrix;

import java.util.concurrent.ThreadLocalRandom;

public class Matrix {
    private static final int MAX_RANDOM_SIZE = 10;
    private static final int MIN_RANDOM_SIZE = 3;

    public static final int MAX_RANDOM_VALUE = 10;
    public static final int MIN_RANDOM_VALUE = -10;

    private static final String FORMAT = "%.2f\t";

    public final double values[][];

    private Matrix(double[][] values) {
        this.values = values;
    }

    public static boolean isMulable(Matrix a, Matrix b) {
        return a.getCols() == b.getRows();
    }

    public static boolean isMinusable(Matrix a, Matrix b) {
        return a.getCols() == b.getCols() && b.getRows() == b.getRows();
    }

    public static Matrix mul(Matrix a, Matrix b) {
        if (!isMulable(a, b)) {
            throw new IllegalArgumentException("A:Rows: " + a.getCols() + " did not match B:Columns " + b.getRows() + ".");
        }

        double[][] c = new double[a.getRows()][b.getCols()];
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
                c[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < a.getRows(); i++) { // aRow
            for (int j = 0; j < b.getCols(); j++) { // bColumn
                for (int k = 0; k < a.getCols(); k++) { // aColumn
                    c[i][j] += a.values[i][k] * b.values[k][j];
                }
            }
        }

        return new Matrix(c);
    }

    public static Matrix minus(Matrix a, Matrix b) {
        return Matrix.add(a, Matrix.mul(b, -1));
    }

    public static Matrix add(Matrix a, Matrix b) {
        if (!isMinusable(a, b)) {
            throw new IllegalArgumentException("A.Rows or B.Cols: " + a.getCols() + " did not match A.Rows or B.Cols" + b.getRows() + ".");
        }

        double[][] c = new double[a.getRows()][b.getCols()];
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
                c[i][j] = a.getValue(i, j) + b.getValue(i, j);
            }
        }

        return new Matrix(c);
    }

    public static double oneNorm(Matrix m) {
        double sum = 0;
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                sum += m.getValue(i, j) * m.getValue(i, j); // compute the column sum
            }
        }

        return Math.sqrt(sum);
    }

    public static double twoNorm(Matrix m) {
        double sum = 0;
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols(); j++) {
                sum += m.getValue(i, j) * m.getValue(i, j); // compute the column sum
            }
        }

        return Math.sqrt(sum);
    }

    public static Matrix mul(Matrix a, double v) {
        for (int i = 0; i < a.values.length; i++) {
            for (int j = 0; j < a.values[i].length; j++) {
                a.values[i][j] *= v;
            }
        }
        return a;
    }

    public static Matrix trans(Matrix a) {
        double[][] temp = new double[a.getCols()][a.getRows()];

        for (int i = 0; i < a.getRows(); i++)
            for (int j = 0; j < a.getCols(); j++)
                temp[j][i] = a.values[i][j];

        return new Matrix(temp);
    }

    public static double scalMul(Matrix a, Matrix b) {
        if (a.getRows() != 1 || b.getRows() != 1 || a.getCols() != b.getCols()) {
            throw new IllegalArgumentException("A B Matrix with: " + a.getRows() + " and " + a.getCols()
                    + " did not match (1,n) vector dimention.");
        }
        double value = 0;

        for (int i = 0; i < a.getCols(); i++) {
            value += a.getValue(0, i) * b.getValue(0, i);
        }

        return value;
    }

    public double[] getRow(int p) {
        return values[p];
    }

    public void setRow(int p, double[] value) {
        values[p] = value;
    }

    public int getCols() {
        return values[0].length;
    }

    public int getRows() {
        return values.length;
    }

    public double getValue(int n, int m) {
        return values[n][m];
    }

    public void setValue(int n, int m, double value) {
        values[n][m] = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Matrix values:\n");
        for (double[] value : values) {
            builder.append("|\t");
            for (int i = 0; i < value.length - 1; i++) {
                builder.append(String.format(FORMAT, value[i]));
            }
            builder.append(String.format(FORMAT + "\t|\n", value[value.length - 1]));
        }

        return builder.toString();
    }

    @Override
    public Matrix clone() {
        return new Matrix(values);
    }

    public static class Generator {


        public static Matrix getFromValue(double[][] values) {
            return new Matrix(values);
        }

        public static Matrix getFromValueCol(double[] x) {
            double[][] values = new double[x.length][1];
            for (int i = 0; i < x.length; i++) {
                values[i][0] = x[i];
            }

            return new Matrix(values);
        }


        public static Matrix getRandom(int n, int m) {
            double[][] generate = new double[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    generate[i][j] = ThreadLocalRandom.current().nextDouble(MIN_RANDOM_VALUE, MAX_RANDOM_VALUE);
                }
            }
            return new Matrix(generate);
        }


        public static Matrix getRandom() {
            return getRandom(ThreadLocalRandom.current().nextInt(MIN_RANDOM_SIZE, MAX_RANDOM_SIZE));
        }


        public static Matrix getRandom(int n) {
            return getRandom(n, n);
        }

        public static Matrix getRandomSymmetric(int n) {
            Matrix A = getRandom(n);
            return Matrix.mul(A, Matrix.trans(A));
        }

        public static Matrix getE() {
            return getE(ThreadLocalRandom.current().nextInt(MIN_RANDOM_SIZE, MAX_RANDOM_SIZE));
        }

        public static Matrix getE(int n) {
            double[][] generate = new double[n][n];
            for (int i = 0; i < n; i++) {
                generate[i][i] = 1;
            }

            return new Matrix(generate);
        }
    }
}




