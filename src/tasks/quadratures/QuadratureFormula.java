package tasks.quadratures;

import functions.Function;
import lib.com.polynom.polynom.Polynom;
import tasks.first.helpers.Matrix;

import java.util.ArrayList;

import static java.lang.Math.pow;

public abstract class QuadratureFormula {
    protected double[] points;
    protected Function f;

    public abstract double solve();

    public QuadratureFormula setPoints(int n) {
        points = setDistributedPoints(n);
        return this;
    }

    private double[] setDistributedPoints(int count) {
        double[] points = new double[count];
        double add = (getB() - getA()) / (count - 1);
        points[0] = getA();
        for (int i = 1; i < points.length; i++) {
            points[i] = points[i - 1] + add;
        }

        return points;
    }

    public QuadratureFormula setH(double h) {
        ArrayList<Double> points = new ArrayList<>();
        points.add(f.getA());
        while (points.get(points.size() - 1) < f.getB()) {
            points.add(points.get(points.size() - 1) + h);
        }
        points.set(points.size() - 1, f.getB());
        for (int i = 0; i < this.points.length; i++) {
            this.points[i] = points.get(i);
        }
        return this;
    }

    protected double moment(int j) {
        Polynom momentPolynom;
        if (f.getBeta() == 0) {
            momentPolynom = new Polynom(new double[]{1, 1}).power(j);
        } else if (f.getAlpha() == 0) {
            momentPolynom = new Polynom(new double[]{1, -1}).power(j);
        } else
            throw new RuntimeException("Wrong alpha = " + f.getAlpha() + " or wrong beta = " + f.getBeta());

        double sum = 0;
        for (int i = 0; i < momentPolynom.getCoeffs().length; i++) {
            double end, start;
            if (f.getBeta() == 0) {
                start = momentPolynom.getCoeffs()[i]
                        * pow(getA(), i)
                        * pow(getB() - getA(), (1 + j - i - f.getAlpha()))
                        / (1 + j - i - f.getAlpha());
                end = 0;
            } else if (f.getAlpha() == 0) {
                end = momentPolynom.getCoeffs()[i]
                        * pow(getB(), i)
                        * pow(getB() - getA(), (1 + j - i - f.getBeta()))
                        / (1 + j - i - f.getBeta());
                start = 0;
            } else
                throw new RuntimeException("Wrong alpha = " + f.getAlpha() + " or wrong beta = " + f.getBeta());
            sum += start - end;
        }
        return sum;
    }

    public Matrix getMatrixX(double[] x) {
        double[][] X = new double[x.length][x.length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[i].length; j++) {
                X[i][j] = pow(x[j], i);
            }
        }

        return Matrix.Generator.getFromValue(X);
    }

    public Matrix getMatrixX() {
        return getMatrixX(points);
    }

    public Matrix getMatrixMoment() {
        double[] moments = new double[points.length];
        for (int i = 0; i < moments.length; i++) {
            moments[i] = moment(i);
        }
        return Matrix.Generator.getFromValueCol(moments);
    }

    public Matrix getMatrixFX() {
        double[] fX = new double[points.length];
        for (int i = 0; i < fX.length; i++) {
            fX[i] = f.f(points[i]);
        }

        return Matrix.Generator.getFromValueCol(fX);
    }

    protected double getA() {
        return f.getA();
    }

    protected double getB() {
        return f.getB();
    }
}
