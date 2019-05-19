package lib.plot;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Plot1 plot1 = new Plot1();

        ArrayList<Pair<Double, Double>> arrGauss = getGauss();
        ArrayList<Pair<Double, Double>> arrNewt = getNewt();
        arrGauss.forEach(v -> plot1.addPointFor1(v.getKey(), v.getValue()));
        arrNewt.forEach(v -> plot1.addPointFor2(v.getKey(), v.getValue()));

        plot1.build();
    }

    static ArrayList<Pair<Double, Double>> getNewt() {
        ArrayList<Pair<Double, Double>> arr = new ArrayList<>();
        arr.add(new Pair<>(4d,0.02709));
        arr.add(new Pair<>(5d,0.012578));
        arr.add(new Pair<>(6d,0.00583));
        arr.add(new Pair<>(7d,0.0027099));
        arr.add(new Pair<>(8d,0.0012578));
        arr.add(new Pair<>(9d,5.8383415e-04));
        arr.add(new Pair<>(10d,2.709918e-04));
        arr.add(new Pair<>(11d,1.257832e-04));
        return arr;
    }

    static ArrayList<Pair<Double, Double>> getGauss() {
        ArrayList<Pair<Double, Double>> arr = new ArrayList<>();
        //arr.add(new Pair<>(4d, 1.5008909));
        //arr.add(new Pair<>(5d, 0.4746233));
        arr.add(new Pair<>(6d, 0.1500890));
        arr.add(new Pair<>(7d, 0.0474623));
        arr.add(new Pair<>(8d, 0.0150089));
        arr.add(new Pair<>(9d, 0.0047462));
        arr.add(new Pair<>(10d,0.0015008));
        arr.add(new Pair<>(11d,4.7362e-04));
        return arr;
    }

}

class Plot1 extends JFrame {
    private final ArrayList<Pair<Double, Double>> points1;
    private final ArrayList<Pair<Double, Double>> points2;

    public Plot1() {
        points1 = new ArrayList<>();
        points2 = new ArrayList<>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    public Plot1 addPointFor1(double x, double y) {
        points1.add(new Pair<>(x, y));
        return this;
    }

    public Plot1 addPointFor2(double x, double y) {
        points2.add(new Pair<>(x, y));
        return this;
    }

    public void build() {
        XYPlot plot = new XYPlot();

        points1.sort(Comparator.comparingDouble(Pair::getKey));
        points2.sort(Comparator.comparingDouble(Pair::getKey));

        DataTable table1 = new DataTable(Double.class, Double.class);
        DataTable table2 = new DataTable(Double.class, Double.class);
        for (Pair<Double, Double> point : points1) {
            table1.add(point.getKey(), point.getValue());
        }
        for (Pair<Double, Double> point : points2) {
            table2.add(point.getKey(), point.getValue());
        }


        plot.add(table1);
        plot.add(table2);

        getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();

        plot.setLineRenderers(table1, lines);
        plot.setLineRenderers(table2, lines);

        LineRenderer lineRenderer1 = new DefaultLineRenderer2D();
        lineRenderer1.setColor(new Color(200, 170, 10));
        plot.setLineRenderers(table1, lineRenderer1);
        LineRenderer lineRenderer2 = new DefaultLineRenderer2D();
        lineRenderer2.setColor(new Color(55, 170, 200));
        plot.setLineRenderers(table2, lineRenderer2);

        setVisible(true);
    }
}