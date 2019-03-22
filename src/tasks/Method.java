package tasks;

import javafx.util.Pair;

public interface Method {
    double EPSILON = 10e-8;
    Pair<Double, Double> solve(double yy);
}
