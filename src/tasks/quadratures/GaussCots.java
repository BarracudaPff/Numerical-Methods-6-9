package tasks.quadratures;

import functions.Function;
import lib.com.polynom.polynom.Polynom;
import tasks.first.Gauss;
import tasks.first.helpers.Matrix;

import java.util.Arrays;

public class GaussCots extends QuadratureFormula{

    public GaussCots(Function f) {
        this.f = f;
    }

    @Override
    public double solve() {
        System.out.println(Arrays.toString(points));
        double[] moments = new double[points.length * 2];
        for (int i = 0; i < moments.length; i++) {
            moments[i] = moment(i);
        }

        double[][] momentsBig = new double[moments.length / 2][moments.length / 2];
        double[] momentsHalf = new double[moments.length / 2];

        for (int i = 0; i < momentsBig.length; i++) {
            for (int j = 0; j < momentsBig[i].length; j++) {
                momentsBig[i][j] = moments[i + j];
            }
        }

        for (int i = 0; i < momentsHalf.length; i++) {
            momentsHalf[i] = -moments[i + moments.length / 2];
        }

        Matrix A = new Gauss.Builder()
                .setA(Matrix.Generator.getFromValue(momentsBig))
                .setB(Matrix.Generator.getFromValueCol(momentsHalf))
                .build().solve();

        double[] a = Matrix.trans(A).getRow(0);
        double[] aWithLead = new double[a.length + 1];
        System.arraycopy(a, 0, aWithLead, 0, a.length);
        aWithLead[a.length] = 1;

        Polynom omega = new Polynom(aWithLead);
        double[] x = omega.solve();
        System.out.println(omega);
        System.out.println(Arrays.toString(x));

        Matrix AKoef = new Gauss.Builder().setA(getMatrixX(x)).setB(getMatrixMoment()).build().solve();
        return Matrix.scalMul(Matrix.trans(getMatrixFX()), Matrix.trans(AKoef));
    }
}
