package tasks.realSecond;

import functions.Function;
import lib.com.polynom.polynom.Polynom;
import tasks.first.Gauss;
import tasks.first.helpers.Matrix;
import tasks.second.NewtonCots;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.pow;

public class GausCots {
    public Double[] points;
    public Function f;

    double a;
    double b;

    public GausCots(Function f) {
        this.f = f;
        a = f.getA();
        b = f.getB();
    }

    public GausCots setPoints(int count) {
        points = setDistributedPoints(count);
        return this;
    }

    public GausCots setH(double h) {
        ArrayList<Double> points = new ArrayList<>();
        points.add(f.getA());
        //System.out.println(h);
        int i = 0;
        while (points.get(points.size() - 1) < f.getB()) {
            points.add(points.get(points.size() - 1) + h);
            i++;
//            points[i] = points[i - 1] + add;
        }
        points.set(points.size() - 1, f.getB());
        //System.out.println(points);
        this.points = points.toArray(new Double[0]);
        return this;
    }

    public Double[] setDistributedPoints(int count) {
        Double[] points = new Double[count];
        double add = (getB() - getA()) / (count - 1);
        points[0] = getA();
        for (int i = 1; i < points.length; i++) {
            points[i] = points[i - 1] + add;
        }

        return points;
    }

    public double solve() {
        double[] moments = new double[4];
        for (int i = 0; i < moments.length; i++) {
            moments[i] = moment(i);
        }
        Polynom omega = new Polynom(new double[]{
                getA0(moments[0], moments[1], moments[2], moments[3]),
                getA1(moments[0], moments[1], moments[2], moments[3]),
                1
        });

        double[] x = omega.solve();
        double A2 = (moments[1] - moments[0] * x[0]) / (x[1] - x[0]);
        double A1 = moments[0] - A2;/*
        System.out.println(omega);
        System.out.println("A-shki " + A1 + " " + A2);*/
        return A1 * f.f(points[0]) + A2 * f.f(points[1]);
    }

    private double getA0(double moment0, double moment1, double moment2, double moment3) {
        return (moment2 * moment2 - moment1 * moment3) / (moment1 * moment1 - moment0 * moment2);
    }

    private double getA1(double moment0, double moment1, double moment2, double moment3) {
        return (moment0 * moment3 - moment1 * moment2) / (moment1 * moment1 - moment0 * moment2);
    }

    private Matrix getMatrixX(double[] solve) {
        double[][] X = new double[points.length][points.length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[i].length; j++) {
                X[i][j] = pow(solve[j], i);
            }
        }

        return Matrix.Generator.getFromValue(X);
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

    public Matrix getMatrixFX() {
        double[] fX = new double[points.length];
        for (int i = 0; i < fX.length; i++) {
            fX[i] = f.f(points[i]);
        }

        return Matrix.Generator.getFromValueCol(fX);
    }

    private Matrix getMatrixMomentB() {
        double[] X = new double[points.length];
        for (int i = 0; i < X.length; i++) {
            X[i] = moment(points.length + i);
        }
        return Matrix.Generator.getFromValueCol(X);
    }

    private Matrix getMatrixMomentA() {
        double[][] X = new double[points.length][points.length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[i].length; j++) {
                X[i][j] = moment(i + j);
            }
        }
        return Matrix.Generator.getFromValue(X);
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
        }
        //System.out.println("I is " + j + "\tSum: " + sum);
        return sum;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public GausCots setBorderA(double a) {
        this.a = a;
        return this;
    }

    public GausCots setBorderB(double b) {
        this.b = b;
        return this;
    }

    private double[] reverse(double[] a) {
        int n = a.length;
        double[] b = new double[n];
        int j = n;
        for (int i = 0; i < n; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }

        return b;
    }
}
