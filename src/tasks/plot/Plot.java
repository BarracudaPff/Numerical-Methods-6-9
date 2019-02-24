package tasks.plot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import javafx.util.Pair;


import javax.swing.*;

public class Plot extends JFrame {
    private double y;
    private final ArrayList<Pair<Double, Double>> points;

    public Plot() {
        points = new ArrayList<>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);

        /*DataTable data = new DataTable(Double.class, Double.class);
        for (double x = -5.0; x <= 5.0; x += 0.25) {
            double y = 5.0 * Math.sin(x);
            data.add(x, y);
        }
        XYPlot plot = new XYPlot(data);
        plot.add();
        getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(data, lines);
        Color color = new Color(0.0f, 0.3f, 1.0f);
        plot.getPointRenderers(data).get(0).setColor(color);
        plot.getLineRenderers(data).get(0).setColor(color);*/
    }

    public Plot setTrueLine(double y) {
        this.y = y;
        return this;
    }

    public Plot addPoint(double x, double y) {
        points.add(new Pair<>(x, y));
        return this;
    }

    public void build() {
        points.sort(Comparator.comparingDouble(Pair::getKey));
        DataTable tableY = new DataTable(Double.class, Double.class);
        for (Pair<Double, Double> point : points) {
            tableY.add(point.getKey()*100, y*1000);
        }
        DataTable tableX = new DataTable(Double.class, Double.class);
        for (Pair<Double, Double> point : points) {
            tableX.add(point.getKey()*100, point.getValue()*1000);
        }
        DataTable tableScale = new DataTable(Double.class, Double.class);
        tableScale.add(points.get(0).getKey()*100, 0d);
        XYPlot plot = new XYPlot();
        plot.add(tableY);
        plot.add(tableX);
        plot.add(tableScale);
        getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(tableY, lines);
        plot.setLineRenderers(tableX, lines);
        plot.setLineRenderers(tableScale, lines);
        Color color = new Color(0.0f, 0.3f, 1.0f);
        //plot.getLineRenderers(tableX).get(0).setColor(color);
        setVisible(true);
    }

    public static void main(String[] args) {
        Plot frame = new Plot();
        frame.setVisible(true);
    }
}
