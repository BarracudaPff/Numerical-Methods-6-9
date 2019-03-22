package tasks.second;

import functions.Function;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.pow;

public class NewtonCots {
    public Function f;

    private Double[] z;

    public NewtonCots(Function f) {
        this.f = f;
    }

    public NewtonCots setPoints(int count) {
        z = setDistributedPoints(count + 1);
        return this;
    }

    public NewtonCots setH(double h) {
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
        z = points.toArray(new Double[0]);
        return this;
    }

    public Double[] setDistributedPoints(int count) {
        Double[] points = new Double[count];
        double add = (f.getB() - f.getA()) / (count - 1);
        points[0] = f.getA();
        for (int i = 1; i < points.length; i++) {
            points[i] = points[i - 1] + add;
        }

        return points;
    }

    public double solve() {
        if (z == null) {
            throw new RuntimeException("No points setup");
        }

        double sum = 0;
//        System.out.println(Arrays.toString(z));
        for (int i = 0; i < z.length - 1; i++) {
            double z0 = z[i];
            double zHalf = (z[i + 1] - z[i]) / 2;
            double z1 = z[i + 1];
            //System.out.println("================");
            //System.out.println(z0 + " " + zHalf + " " + z1);
            //System.out.println(getA1(z0, zHalf, z1));
            //System.out.println(getA2(z0, zHalf, z1));
            //System.out.println(getA3(z0, zHalf, z1));
            sum -= getA1(z0, zHalf, z1) * f.f(z0)
                    + getA2(z0, zHalf, z1) * f.f(zHalf)
                    + getA3(z0, zHalf, z1) * f.f(z1);
        }
        return sum;
    }

    public double getMoment0(double zi, double zi2) {
        return (pow(zi - getA(), 1 - f.getAlpha()) - pow(zi2 - getA(), 1 - f.getAlpha()))
                / (1 - f.getAlpha());
    }

    public double getMoment1(double zi, double zi2) {
        return (pow(zi - getA(), 2 - f.getAlpha()) - pow(zi2 - getA(), 2 - f.getAlpha()))
                / (2 - f.getAlpha())
                + f.getA() * getMoment0(zi, zi2);
    }

    public double getMoment2(double zi, double zi2) {
        return (pow(zi - getA(), 3 - f.getAlpha()) - pow(zi2 - getA(), 3 - f.getAlpha()))
                / (3 - f.getAlpha())
                + 2 * f.getA() * getMoment1(zi, zi2) - pow(f.getA(), 2) * getMoment0(zi, zi2);
    }

    public double getA1(double zi, double ziHalf, double zi2) {
        return (getMoment2(zi, zi2) - getMoment1(zi, zi2) * (ziHalf + zi) + getMoment0(zi, zi2) * ziHalf * zi)
                / ((ziHalf - zi2) * (zi - zi2));
    }

    public double getA2(double zi, double ziHalf, double zi2) {
        return (getMoment2(zi, zi2) - getMoment1(zi, zi2) * (zi2 + zi) + getMoment0(zi, zi2) * zi2 * zi)
                / ((ziHalf - zi2) * (zi - ziHalf));
    }

    public double getA3(double zi, double ziHalf, double zi2) {
        return (getMoment2(zi, zi2) - getMoment1(zi, zi2) * (ziHalf + zi2) + getMoment0(zi, zi2) * ziHalf * zi2)
                / ((zi - ziHalf) * (zi - zi2));
    }

    public double getA() {
        return f.getA();
    }
}
