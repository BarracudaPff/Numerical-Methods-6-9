package tasks.first;

import functions.Function;
import javafx.util.Pair;
import lib.com.polynom.polynom.Polynom;
import tasks.Method;
import tasks.first.helpers.Matrix;

import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class NewtonCots implements Method {
    public double[] points;
    public Function f;

    double a;
    double b;

    public NewtonCots(Function f) {
        this.f = f;
        a = f.getA();
        b = f.getB();
    }

    public NewtonCots setPoints(int count) {
        points = setDistributedPoints(count);
        return this;
    }

    public double[] setDistributedPoints(int count) {
        double[] points = new double[count];
        double add = (getB() - getA()) / (count - 1);
        points[0] = getA();
        for (int i = 1; i < points.length; i++) {
            points[i] = points[i - 1] + add;
        }

        return points;
    }

    public double solve() {
        moment(0);
        if (points == null) {
            throw new RuntimeException("No points setup");
        }
        Matrix A = new Gauss.Builder().setA(getMatrixX()).setB(getMatrixMoment()).build().solve();
        return Matrix.scalMul(Matrix.trans(getMatrixFX()), Matrix.trans(A));
    }

    public Matrix getResults() {
        if (points == null) {
            throw new RuntimeException("No points setup");
        }
        return new Gauss.Builder().setA(getMatrixX()).setB(getMatrixMoment()).build().solve();
    }

    public Matrix getMatrixMoment() {
        double[] moments = new double[points.length];
        for (int i = 0; i < moments.length; i++) {
            if (f.getBeta() == 0)
                moments[i] = moment(i);
            else
                moments[i] = moment(i);
            //if (abs(moment(i) - newMoment(i)) > 10e-4)
            //    throw new RuntimeException("Use new moment! " + moment(i) + "\t" + newMoment(i));
        }
        return Matrix.Generator.getFromValueCol(moments);
    }

    public Matrix getMatrixX() {
        double[][] X = new double[points.length][points.length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[i].length; j++) {
                X[i][j] = pow(points[j], i);
            }
        }

        return Matrix.Generator.getFromValue(X);
    }

    public Matrix getMatrixFX() {
        double[] fX = new double[points.length];
        for (int i = 0; i < fX.length; i++) {
            fX[i] = f.f(points[i]);
        }

        return Matrix.Generator.getFromValueCol(fX);
    }

    private double moment(int j) {
        Polynom momentPolynom;
        if (f.getBeta() == 0) {
            momentPolynom = new Polynom(new double[]{getA(), 1}).power(j);
        } else if (f.getAlpha() == 0) {
            momentPolynom = new Polynom(new double[]{getB(), -1}).power(j);
        } else
            throw new RuntimeException("Wrong alpha = " + f.getAlpha() + " or wrong beta = " + f.getBeta());
        double sum = 0;
        for (int k = 0; k < momentPolynom.getCoeffs().length; k++) {
            double end, start;
            if (f.getBeta() == 0) {
                end = momentPolynom.getCoeffs()[k] * pow(-getA() + getB(), k - f.getAlpha() + 1) / (k - f.getAlpha() + 1);
                start = momentPolynom.getCoeffs()[k] * pow(-getB() + getB(), k - f.getAlpha() + 1) / (k - f.getAlpha() + 1);
            } else if (f.getAlpha() == 0) {
                start = 0;
                end = momentPolynom.getCoeffs()[k] * pow(getB() - getA(), k - f.getBeta() + 1) / (k - f.getBeta() + 1);
            } else
                throw new RuntimeException("Wrong alpha = " + f.getAlpha() + " or wrong beta = " + f.getBeta());
            sum += end - start;
            System.out.println("j: " + j + "\t" + sum);
        }
        System.out.println("I is " + j + "\tSum: " + sum);
        return sum;
    }

    private double newMoment(int j) {
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
            //System.out.println(1 + j - i - f.getBeta());//+
            //System.out.println(f.getB() - f.getA());//+
            //System.out.println(pow(f.getB(), i));
            //System.out.println("Data" + (-end));
        }
        System.out.println("I is " + j + "\tSum: " + sum);
        System.out.println();
        return sum;
    }


    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public NewtonCots setBorderA(double a) {
        this.a = a;
        return this;
    }

    public NewtonCots setBorderB(double b) {
        this.b = b;
        return this;
    }

    @Override
    public Pair<Double, Double> solve(double yy) {
        return null;
    }
}
