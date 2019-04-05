package tasks.first;

import tasks.Method;
import tasks.MethodMatrix;
import tasks.first.helpers.Matrix;

public class Gauss implements MethodMatrix {
    private Matrix A, B;

    public Gauss(Matrix a, Matrix b) {
        A = a;
        B = b;
    }

    @Override
    public Matrix solve() {
        if (A == null || B == null || !Matrix.isMulable(A, B))
            throw new IllegalArgumentException("Error in matrix A and B");
        if (B.getCols() != 1)
            throw new IllegalArgumentException("B cols count must be 1");

        int n = B.getRows();
        for (int p = 0; p < n; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A.getValue(i, p)) > Math.abs(A.getValue(max, p))) {
                    max = i;
                }
            }

            double[] temp = A.getRow(p);
            A.setRow(p, A.getRow(max));
            A.setRow(max, temp);

            double t = B.getValue(p, 0);
            B.setValue(p, 0, B.getValue(max, 0));
            B.setValue(max, 0, t);

            // singular or nearly singular
            if (Math.abs(A.getValue(p, p)) <= EPSILON) {
                //throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < n; i++) {
                double alpha = A.getValue(i, p) / A.getValue(p, p);
                B.setValue(i, 0, B.getValue(i, 0) - alpha * B.getValue(p, 0));
                for (int j = p; j < n; j++) {
                    A.setValue(i, j, A.getValue(i, j) - alpha * A.getValue(p, j));
                }
            }
        }

        // back substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A.getValue(i, j) * x[j];
            }
            x[i] = (B.getValue(i, 0) - sum) / A.getValue(i, i);
        }
        return Matrix.Generator.getFromValueCol(x);
    }

    public static class Builder implements javafx.util.Builder<Gauss> {
        private  Matrix A, B;

        public Builder setA(Matrix a) {
            A = a;
            return this;
        }

        public Builder setB(Matrix b) {
            B = b;
            return this;
        }

        @Override
        public Gauss build() {
            return new Gauss(A,B);
        }
    }
}
