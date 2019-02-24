package functions;

import static java.lang.Math.*;

public class Function5 implements Function {
    private double a = 2.5;
    private double b = 4.3;

    private double alpha = 2d / 7d;
    private double beta = 0;

    @Override
    public double f(double x) {
        return cos(1.5d * x) * exp(2d * x / 3d) + 3 * sin(5.5d * x) * exp(-2d * x) + 2d;
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
        return "int+(cos(1.5x)*e%5E(2x%2F3)%2B3sin(5.5x)*e%5E(-2x)%2B2+)%2F(x-2.5)%5E(2%2F7)+2.5..4.3";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
