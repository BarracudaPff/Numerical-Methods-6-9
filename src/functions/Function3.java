package functions;

import static java.lang.Math.*;
import static java.lang.Math.pow;

public class Function3 implements Function {
    private double a = 0.1;
    private double b = 2.3;

    private double alpha = 1d / 5d;
    private double beta = 0;

    @Override
    public double f(double x) {
        return 2.5 * cos(2 * x) * exp(2 * x / 3) + 4 * sin(3.5 * x) * exp(-3 * x) + 3 * x;
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
