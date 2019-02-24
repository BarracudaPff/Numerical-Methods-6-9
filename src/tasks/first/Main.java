package tasks.first;

import functions.*;
import tasks.plot.Plot;
import tasks.wolfram.WolframTask;

import static java.lang.Math.abs;

public class Main {
    private static int count = 10;

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Function f = new Function5();
        System.out.println("Calculating " + f);
        WolframTask task = new WolframTask(f);
        double yy = 0;
        try {
            yy = task.sendGet();
            System.out.println(yy);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with internet connection.");
        }
        NewtonCots newtonCots = new NewtonCots(f);
        for (int i = 1; i < count; i++) {
            double xx = newtonCots.setDistributedPoints(i).solve();
            System.out.println("Answer with " + i +
                    " points is: " + xx +
                    "\t difference is: " + abs(xx - yy));
        }

    }


}
