package functions;

public interface Function {
    double f(double x);
    double p(double x);

    double getA();
    double getB();
    double getAlpha();
    double getBeta();
    String toWolframString();
}
