package tasks.realSecond;

import functions.Function;
import lib.com.polynom.polynom.Polynom;

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
                getA0(moments),
                getA1(moments),
                1
        });

        double[] t = omega.solve();
        System.out.println(omega);
        System.out.println(Arrays.toString(t));
        double A1 = (moments[1] - moments[0] * t[1]) / (t[0] - t[1]);
        double A2 = (-moments[1] + moments[0] * t[0]) / (t[0] - t[1]);

        //System.out.println("moments are " + Arrays.toString(moments));
        //System.out.println("omega is " + omega);
        //System.out.println("t_j are " + Arrays.toString(t));
        //System.out.println("A-shki " + A1 + " " + A2);
        return A1 * f.f(points[0]) + A2 * f.f(points[1]);
    }

    private double getA0(double... moments) {
        return (moments[2] * moments[2] - moments[1] * moments[3]) / (moments[1] * moments[1] - moments[0] * moments[2]);
    }

    private double getA1(double... moments) {
        return (moments[0] * moments[3] - moments[1] * moments[2]) / (moments[1] * moments[1] - moments[0] * moments[2]);
    }

    private double moment(int j) {
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

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }
}
