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

    static ArrayList<Pair<Double, Double>> getGauss() {
        ArrayList<Pair<Double, Double>> arr = new ArrayList<>();
        arr.add(new Pair(3d, 0.0387));
        arr.add(new Pair(4d, 0.0326));
        arr.add(new Pair(5d, 0.0275));

        arr.add(new Pair(6d, 0.0234));
        arr.add(new Pair(7d, 0.0208));
        arr.add(new Pair(8d, 0.0185));
        arr.add(new Pair(9d, 0.0164));
        arr.add(new Pair(10d, 0.0146));
        arr.add(new Pair(11d, 0.0130));
        arr.add(new Pair(12d, 0.0116));
        arr.add(new Pair(13d, 0.0103));
        arr.add(new Pair(14d, 0.0091));
        arr.add(new Pair(15d, 0.0081));
        arr.add(new Pair(16d, 0.0072));
        return arr;
    }

    static ArrayList<Pair<Double, Double>> getNewt() {
        ArrayList<Pair<Double, Double>> arr = new ArrayList<>();
        arr.add(new Pair(3d, 0.0563));
        arr.add(new Pair(4d, 0.0493));
        arr.add(new Pair(5d, 0.0433));

        arr.add(new Pair(6d, 0.0383));
        arr.add(new Pair(7d, 0.0347));
        arr.add(new Pair(8d, 0.0313));
        arr.add(new Pair(9d, 0.0283));
        arr.add(new Pair(10d, 0.0256));
        arr.add(new Pair(11d, 0.0231));
        arr.add(new Pair(12d, 0.0209));
        arr.add(new Pair(13d, 0.0189));
        arr.add(new Pair(14d, 0.0171));
        arr.add(new Pair(15d, 0.0154));
        arr.add(new Pair(16d, 0.0140));
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