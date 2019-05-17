package tasks.second;

import functions.Function;
import functions.Function5;
import tasks.quadratures.CompoundQuadrature;
import tasks.quadratures.GaussCots;
import tasks.quadratures.NewtonCots;

public class Main {
    private static int count = 1000;
    public static final double EPS_MAX = 10e-3;
    public static final double EPS_MIN = 10e-10;

    public static void main(String[] args) {
        Function f = new Function5();
        CompoundQuadrature compoundQuadrature = new CompoundQuadrature(f);
        System.out.println(compoundQuadrature.solve(GaussCots.class));
        System.out.println(compoundQuadrature.solve(NewtonCots.class));
    }
}
