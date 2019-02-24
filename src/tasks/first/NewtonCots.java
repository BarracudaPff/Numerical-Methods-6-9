package tasks.first;

import functions.Function;
import lib.com.polynom.polynom.Polynom;
import tasks.Method;
import tasks.first.helpers.Matrix;

import java.util.Arrays;

import static java.lang.Math.pow;

public class NewtonCots implements Method {
    private double[] points;
    private Function f;

    public NewtonCots(Function f) {
        this.f = f;
    }

    public NewtonCots setDistributedPoints(int count) {
        points = new double[count];
        double add = (f.getB() - f.getA()) / (count - 1);
        points[0] = f.getA();
        for (int i = 1; i < points.length; i++) {
            points[i] = points[i - 1] + add;
        }

        return this;
    }

    public double solve() {
        if (points == null) {
            throw new RuntimeException("No points setup");
        }
        Matrix A = new Gauss.Builder().setA(getMatrixX()).setB(getMatrixMoment()).build().solve();
        return Matrix.scalMul(Matrix.trans(getMatrixFX()), Matrix.trans(A));
    }

    private Matrix getMatrixMoment() {
        double[] moments = new double[points.length];
        for (int i = 0; i < moments.length; i++) {
            moments[i] = moment(i);
        }
        return Matrix.Generator.getFromValueCol(moments);
    }

    private Matrix getMatrixX() {
        double[][] X = new double[points.length][points.length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[i].length; j++) {
                X[i][j] = pow(points[j], i);
            }
        }

        return Matrix.Generator.getFromValue(X);
    }

    private Matrix getMatrixFX() {
        double[] fX = new double[points.length];
        for (int i = 0; i < fX.length; i++) {
            fX[i] = f.f(points[i]);
        }

        return Matrix.Generator.getFromValueCol(fX);
    }

    private double moment(int i) {
        Polynom momentPolynom;
        if (f.getBeta() == 0) {
            momentPolynom = new Polynom(new double[]{f.getA(), 1}).power(i);
        } else if (f.getAlpha() == 0) {
            momentPolynom = new Polynom(new double[]{f.getB(), -1}).power(i);
        } else
            throw new RuntimeException("Wrong alpha = " + f.getAlpha() + " or wrong beta = " + f.getBeta());
        double sum = 0;
        for (int k = 0; k < momentPolynom.getCoeffs().length; k++) {
            double end, start;
            if (f.getBeta() == 0) {
                end = momentPolynom.getCoeffs()[k] * pow(-f.getA() + f.getB(), k - f.getAlpha() + 1) / (k - f.getAlpha() + 1);
                start = 0;
            } else if (f.getAlpha() == 0) {
                start = 0;
                end = momentPolynom.getCoeffs()[k] * pow(f.getB() - f.getA(), k - f.getBeta() + 1) / (k - f.getBeta() + 1);
            } else
                throw new RuntimeException("Wrong alpha = " + f.getAlpha() + " or wrong beta = " + f.getBeta());
            sum += end - start;
        }
        return sum;
    }
}
