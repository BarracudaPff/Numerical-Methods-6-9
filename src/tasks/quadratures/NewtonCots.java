package tasks.quadratures;

import functions.Function;
import javafx.util.Pair;
import lib.com.polynom.polynom.Polynom;
import tasks.Method;
import tasks.first.Gauss;
import tasks.first.helpers.Matrix;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class NewtonCots extends QuadratureFormula {
    public NewtonCots(Function f) {
        this.f = f;
    }

    @Override
    public double solve() {
        moment(0);
        if (points == null) {
            throw new RuntimeException("No points setup");
        }
        Matrix A = new Gauss.Builder().setA(getMatrixX()).setB(getMatrixMoment()).build().solve();
        return Matrix.scalMul(Matrix.trans(getMatrixFX()), Matrix.trans(A));
    }
}
