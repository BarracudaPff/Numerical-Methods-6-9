package tasks.realSecond;

import functions.FunctionSet;
import tasks.quadratures.GaussCots;
import tasks.second.NewtonCots;

public class CompoundCots {
    private double h;
    private FunctionSet f;

    private boolean isGauss;

    public CompoundCots(FunctionSet f, boolean isGauss) {
        this.f = f;
        this.isGauss = isGauss;
    }

    public CompoundCots setH(double h) {
        this.h = h;
        return this;
    }

    public double solve() {
        double start = f.getA();
        double end = f.getB();
        double sum = 0;

        for (double i = start; i < end; i += h) {
            f.setA(i);
            f.setB(i + h);
            if (isGauss) {
                sum += (new GaussCots(f).setPoints(2).solve());
            } else {
                sum += (new NewtonCots(f).setPoints(2).solve());
            }
        }

        f.setA(start);
        f.setB(end);

        return sum;
    }
}
