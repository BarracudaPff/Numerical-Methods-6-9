package tasks.first;

import functions.*;
import tasks.plot.Plot;
import tasks.wolfram.WolframTask;

import static java.lang.Math.abs;

public class Main {
    private static int count = 50;

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Plot plot = new Plot();
        Function f = new Function5();
        NewtonCots newtonCots = new NewtonCots(f);
        System.out.println("Calculating " + f);
        WolframTask task = new WolframTask(f);
        double yy = 0;

        try {
            //yy = task.sendGet();
            //plot.setTrueLine(yy);change
            //System.out.println("The real answer is " + yy);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with internet connection.");
        }
        System.out.println(newtonCots.setDistributedPoints(3).solve());

        /*for (int i = 1; i < count; i++) {
            try {
                double xx = newtonCots.setDistributedPoints(i).solve();
                plot.addPoint(i, xx);
                System.out.println("Answer with " + i +
                        " points is: " + xx +
                        "\t difference is: " + abs(xx - yy));
            } catch (Exception e) {
                System.out.println("Skipped iteration " + i);
            }
        }*/
        //plot.build();
    }


}
