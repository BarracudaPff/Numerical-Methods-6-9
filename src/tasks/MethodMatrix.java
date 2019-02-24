package tasks;

import tasks.first.helpers.Matrix;

public interface MethodMatrix {
    double EPSILON = 10e-8;
    Matrix solve();
}
