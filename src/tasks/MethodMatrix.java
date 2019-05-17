package tasks;

import lib.matrix.Matrix;

public interface MethodMatrix {
    double EPSILON = 10e-8;
    Matrix solve();
}
