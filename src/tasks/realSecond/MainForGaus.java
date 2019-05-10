package tasks.realSecond;

import functions.Function;
import functions.Function3;
import functions.Function5;
import tasks.plot.Plot;
import tasks.wolfram.WolframTask;

import java.util.Arrays;

import static java.lang.Math.*;
import static java.lang.Math.log;

public class MainForGaus {
    private static int count = 1000;
    public static final double EPS_MAX = 10e-3;
    public static final double EPS_MIN = 10e-10;

    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        Plot plot = new Plot();
        Function3 f = new Function3();
        WolframTask task = new WolframTask(f);
        System.out.println("Calculating " + f);

        try {
            System.out.println("The real answer is " + task.sendGet());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with internet connection.");
        }

        GausCots gausCots = new GausCots(f);
        gausCots.setPoints(2);
        System.out.println("n is " + 2);
        System.out.println("points are " + Arrays.toString(gausCots.points));
        double y = gausCots.solve();
        System.out.println(y);

        double start = f.getA();
        double end = f.getB();
        int count = 20;
        double add = (end - start) / count;
        double sum = 0;
        for (double i = start; i < end; i += add) {
            f.setA(i);
            f.setB(i + add);
            System.out.println("===");
            System.out.println(i+" "+(i+add));
           double gc = (new GausCots(f).setPoints(2).solve());
            System.out.println(gc);
            sum+=gc;
        }
        System.out.println(sum);
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
            xx = new GausCots(f).setH(h).solve();
            System.out.println("Points is: " + xx +
                    "\t epsilon is: " + ep +
                    "\t step is: " + h +
                    "\t difference is: " + abs(xx - yy));
            plot.addPoint(ep, h);
        }

        plot.build();

        GausCots gausCots = new GausCots(f);
        gausCots.setPoints(2);
        double y = gausCots.solve();
        System.out.println(y);
        plot.build();
    }

    private static double getRh1(double h1, double L, double m, Function f) {
        double Sh1 = new GausCots(f).setH(h1).solve();
        double Sh2 = new GausCots(f).setH(h1 / L).solve();

        return (Sh2 - Sh1) / (1 - pow(L, -m));
    }

    private static double m(double h1, double L, Function f) {
        double Sh1 = new GausCots(f).setH(h1).solve();
        double Sh2 = new GausCots(f).setH(h1 / L).solve();
        double Sh3 = new GausCots(f).setH(h1 / L / L).solve();

        double m = -log((Sh3 - Sh2) / (Sh2 - Sh1)) / log(L);
        System.out.println("m = " + m);
        return m;
    }
}
