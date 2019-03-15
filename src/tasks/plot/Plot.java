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
        tableY.add(points.get(0).getKey() * 100, y * 1000);
        tableY.add(points.get(points.size()-1).getKey() * 100, y * 1000);

        DataTable tableX = new DataTable(Double.class, Double.class);
        for (Pair<Double, Double> point : points) {
            tableX.add(point.getKey() * 100, point.getValue() * 1000);
        }
        DataTable tableScale = new DataTable(Double.class, Double.class);
        tableScale.add(points.get(0).getKey() * 100, 0d);

        XYPlot plot = new XYPlot();
        //plot.add(tableY);
        plot.add(tableX);
        plot.add(tableScale);
        getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(tableY, lines);
        plot.setLineRenderers(tableX, lines);
        plot.setLineRenderers(tableScale, lines);
        Color colorY = new Color(0.0f, 0.3f, 1.0f);
        plot.getLineRenderers(tableY).get(0).setColor(colorY);
        Color colorX = new Color(1.0f, 0.0f, 0.0f);
        plot.getLineRenderers(tableX).get(0).setColor(colorX);
        setVisible(true);
    }

    public static void main(String[] args) {
        Plot frame = new Plot();
        frame.setVisible(true);
    }
}
