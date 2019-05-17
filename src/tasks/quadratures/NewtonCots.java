package tasks.quadratures;

import functions.Function;
import tasks.first.Gauss;
import lib.matrix.Matrix;

import static java.lang.Math.abs;

public class NewtonCots extends QuadratureFormula {

    public NewtonCots(Function f) {
        super(f);
    }

    @Override
    public double solve() {
        Matrix A = new Gauss.Builder().setA(getMatrixX()).setB(getMatrixMoment()).build().solve();
        return Matrix.scalMul(Matrix.trans(getMatrixFX()), Matrix.trans(A));
    }
}
