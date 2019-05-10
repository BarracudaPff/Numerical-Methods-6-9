package functions;

import static java.lang.Math.*;

public class Function15 implements FunctionSet {
    private double a = 1.2;
    private double b = 2.3;

    private double alpha = 4d / 5d;
    private double beta = 0;

    @Override
    public double f(double x) {
        return 3.5 *cos(0.7*x)* exp(-5*x/3) + 2.4* sin(5.5*x) *exp(-3*x/4) + 5 ;
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
    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

}
