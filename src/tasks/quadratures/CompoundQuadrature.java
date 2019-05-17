package tasks.quadratures;

import functions.Function;

public class CompoundQuadrature {
    private Function f;
    private double[] points;

    public CompoundQuadrature(Function f) {
        this.f = f;
    }

    public CompoundQuadrature setPoints(int n) {
        points = setDistributedPoints(n);
        return this;
    }

    private double[] setDistributedPoints(int count) {
        double[] points = new double[count];
        double add = (f.getB() - f.getA()) / (count - 1);
        points[0] = f.getA();
        for (int i = 1; i < points.length; i++) {
            points[i] = points[i - 1] + add;
        }

        return points;
    }

    public double solve(Class x) {
        if (x.isAssignableFrom(GaussCots.class))
            System.out.println("GC");
        if (x.isAssignableFrom(NewtonCots.class))
            System.out.println("NC");

        return 0;
    }
}
