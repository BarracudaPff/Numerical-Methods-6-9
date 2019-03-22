package tasks.second;

import functions.*;
import tasks.plot.Plot;
import tasks.wolfram.WolframTask;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Main {
    private static int count = 1000;

    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        Plot plot = new Plot();
        Function f = new Function5();
        System.out.println("Calculating " + f);
        WolframTask task = new WolframTask(f);
        double yy = 0;

        try {
            yy = task.sendGet();
            System.out.println("The real answer is " + yy);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with internet connection.");
        }

        for (int i = 1; i < count; i++) {
            try {
                //if (i==11)
                //    continue;
                double xx = new NewtonCots(f).setPoints(i).solve();
                plot.addPoint(i, xx);
                System.out.println("Answer with " + i +
                        " points is: " + xx +
                        "\t difference is: " + abs(xx - yy));
                if (abs(xx - yy) <= 10e-4) {
                    System.out.println("Found");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Skipped iteration " + i + " " + e.getMessage() + " " + e.getClass());
            }
        }
        plot.build();
    }

    private static void test2() {
        Plot plot = new Plot();
        Function f = new Function5();
        WolframTask task = new WolframTask(f);

        double h = (f.getB() - f.getA()) / 3;
        double L = 2;
        double m = 2;

        double eps = 10e-4;

        double yy = 0;
        double xx = 0;

        try {
            yy = task.sendGet();
            System.out.println("The real answer is " + yy);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with internet connection.");
        }
        //System.out.println(new NewtonCots(f).setH(h).solve());
        int i = 0;
        do {
            h = h * pow(eps / abs(getRh1(h, L, m, f)), 1 / m);
            xx = new NewtonCots(f).setH(h).solve();
            plot.addPoint(i, xx);
            System.out.println("Answer with " + i +
                    " points is: " + xx +
                    "\t difference is: " + abs(xx - yy));
            i++;
            break;
        } while (abs(xx - yy) > 10e-4);
    }

    private static double getRh1(double h1, double L, double m, Function f) {
        double Sh1 = new NewtonCots(f).setH(h1).solve();
        double Sh2 = new NewtonCots(f).setH(h1 / L).solve();

        return (Sh2 - Sh1) / (1 - pow(L, -m));
    }
}
