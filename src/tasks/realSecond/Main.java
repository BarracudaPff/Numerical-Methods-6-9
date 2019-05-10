package tasks.realSecond;

import functions.*;
import tasks.plot.Plot;
import tasks.quadratures.GaussCots;
import tasks.second.NewtonCots;
import tasks.wolfram.WolframTask;

import static java.lang.Math.*;
import static java.lang.Math.abs;

public class Main {
    private static int count = 1000;
    public static final double EPS_MAX = 10e-3;
    public static final double EPS_MIN = 10e-10;

    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        Function f = new Function5();
        System.out.println("Calculating " + f);

        double h = 0.0001;

        //System.out.println(new CompoundCots(f, true).setH(h).solve());
        //System.out.println(new CompoundCots(f, false).setH(h).solve());
        for (int i = 9; i < 10; i++) {
            try {
                System.out.println(
                        new GaussCots(f).setPoints(i).solve()
                                //+ "\t" +
                                //new NewtonCots(f).setPoints(i).solve()
                );
            } catch (Exception e) {
                System.out.println("skipped " + i);
                e.printStackTrace();
            }
        }
    }

    private static void test1() {
        Plot plot = new Plot();
        Function f = new Function5();
        System.out.println("Calculating " + f);
        WolframTask task = new WolframTask(f);

        double h = (f.getB() - f.getA()) / 3;
        double L = 2;
        double m = m(h, L, f);

        double xx = 0;
        double yy = 10.6572290681147619655;

        try {
            yy = task.sendGet();
            System.out.println("The real answer is " + yy);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with internet connection.");
        }

        for (double ep = EPS_MAX; ep >= EPS_MIN; ep = ep / 10) {
            h = h * pow(ep / abs(getRh1(h, L, m, f)), 1 / m);
            xx = new NewtonCots(f).setH(h).solve();
            System.out.println("Points is: " + xx +
                    "\t epsilon is: " + ep +
                    "\t step is: " + h +
                    "\t difference is: " + abs(xx - yy));
            plot.addPoint(ep, h);
        }

        plot.build();

        GaussCots gaussCots = new GaussCots(f);
        gaussCots.setPoints(2);
        double y = gaussCots.solve();
        System.out.println(y);
        plot.build();
    }

    private static double getRh1(double h1, double L, double m, Function f) {
        double Sh1 = new tasks.second.NewtonCots(f).setH(h1).solve();
        double Sh2 = new tasks.second.NewtonCots(f).setH(h1 / L).solve();

        return (Sh2 - Sh1) / (1 - pow(L, -m));
    }

    private static double m(double h1, double L, Function f) {
        double Sh1 = new tasks.second.NewtonCots(f).setH(h1).solve();
        double Sh2 = new tasks.second.NewtonCots(f).setH(h1 / L).solve();
        double Sh3 = new NewtonCots(f).setH(h1 / L / L).solve();

        double m = -log((Sh3 - Sh2) / (Sh2 - Sh1)) / log(L);
        System.out.println("m = " + m);
        return m;
    }
}
