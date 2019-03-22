package tasks.second;

import functions.Function;

import java.util.Arrays;

import static java.lang.Math.pow;

public class NewtonCots {
    public double[] points;
    public Function f;
    double a;
    double b;

    private double[] z;

    public NewtonCots(Function f) {
        this.f = f;
        a = f.getA();
        b = f.getB();
    }

    @Override
    public NewtonCots setPoints(int count) {
        z = setDistributedPoints(count + 1);
        return this;
    }

    @Override
    public double solve() {
        if (z == null) {
            throw new RuntimeException("No points setup");
        }

        System.out.println(Arrays.toString(z));
        System.out.println("===================");
        double sum = 0;
        for (int i = 0; i < z.length - 1; i++) {
            setBorderA(z[i]);
            setBorderB(z[i + 1]);
            //System.out.println(getA() + " " + z[i]);
            //System.out.println(getB() + " " + z[i + 1]);
            super.setPoints(3);
            System.out.println("===================");
            System.out.println(Arrays.toString(super.points));
            System.out.println(getMatrixX());
            System.out.println(getMatrixMoment());
            System.out.println(getResults());
            double s = super.solve();
            System.out.println(s);
            System.out.println("===================");
            sum += s;
        }
        return sum;
    }

    public double getMoment0(double zi, double zi2) {
        return (pow(zi - getBorderA(), 1 - f.getAlpha()) - pow(zi2 - f.getAlpha(), 1 - f.getAlpha())) / (1 - f.getAlpha());
    }

    public double getMoment1(double zi, double zi2) {
        return (pow(zi - getBorderA(), 2 - f.getAlpha()) - pow(zi2 - f.getAlpha(), 2 - f.getAlpha())) / (2 - f.getAlpha())
                + f.getAlpha() * getMoment0(zi, zi2);
    }

    public double getMoment2(double zi, double zi2) {
        return (pow(zi - getBorderA(), 3 - f.getAlpha()) - pow(zi2 - f.getAlpha(), 3 - f.getAlpha())) / (3 - f.getAlpha())
                + 2 * f.getAlpha() * getMoment1(zi, zi2) - pow(f.getAlpha(), 2) * getMoment0(zi, zi2);
    }

    public double getA1(double zi, double zi2, double ziHalf) {
        return (getMoment2(zi,zi2)-getMoment1(zi,zi2)*(ziHalf-zi)+getMoment0(zi,zi2)*ziHalf*zi)
                /((ziHalf-zi2)*(zi-zi2));
    }

    public double getA2(double zi, double zi2, double ziHalf) {
        return -(getMoment2(zi,zi2)-getMoment1(zi,zi2)*(zi2-zi)+getMoment0(zi,zi2)*zi2*zi)
                /((ziHalf-zi2)*(zi-ziHalf));
    }

    public double getA3(double zi, double zi2, double ziHalf) {
        return (getMoment2(zi,zi2)-getMoment1(zi,zi2)*(ziHalf-zi2)+getMoment0(zi,zi2)*ziHalf*zi2)
                /((zi-ziHalf)*(zi-zi2));
    }

    public double getBorderA() {
        return a;
    }

    public double getBorderB() {
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
}
