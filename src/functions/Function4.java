package functions;

import static java.lang.Math.*;
import static java.lang.Math.pow;

public class Function4 implements Function {
    private double a = 1;
    private double b = 3;

    private double alpha = 0;
    private double beta = 1d/6d;

    @Override
    public double f(double x) {
        return 3*cos(3.5*x)*exp(4*x/3)+2*sin(3.5*x)*exp(-2*x/3)+4*x;
    }

    @Override
    public double p(double x) {
        return 1 / (pow(x - a, alpha) * pow(b - x, beta));
    }

    @Override
    public double getA() {
        return a;
    }

    @Override
    public double getB() {
        return b;
    }

    @Override
    public double getAlpha() {
        return alpha;
    }

    @Override
    public double getBeta() {
        return beta;
    }

    @Override
    public String toWolframString() {
        return null;
    }
}
