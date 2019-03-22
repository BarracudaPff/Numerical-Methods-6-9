package tasks.second;

import functions.*;
import tasks.plot.Plot;
import tasks.wolfram.WolframTask;

import static java.lang.Math.abs;
import static java.lang.StrictMath.pow;

public class Main {
    private static int count = 10;

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Plot plot = new Plot();
        Function f = new Function3();
        NewtonCots newtonCots = new NewtonCots(f);
        System.out.println("Calculating " + f);
        WolframTask task = new WolframTask(f);
        double yy = 0;

        try {
            //yy = task.sendGet();
            //plot.setTrueLine(yy);
            //System.out.println("The real answer is " + yy);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with internet connection.");
        }
        //System.out.println(newtonCots.setPoints(3).solve());

        for (int i = 1; i < count; i++) {
            try {
                double xx = new NewtonCots(f).setPoints(i).solve();
                plot.addPoint(i, xx);
                System.out.println("Answer with " + i +
                        " points is: " + xx +
                        "\t difference is: " + abs(xx - yy));
            } catch (Exception e) {
                System.out.println("Skipped iteration " + i +" "+e.getMessage() +" "+e.getClass());
            }
        }
        //plot.build();
    }


}