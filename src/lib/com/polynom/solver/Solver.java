package lib.com.polynom.solver;

import lib.com.polynom.polynom.Polynom;
import lib.com.polynom.shturm.Shturm;

public class Solver {
    private Shturm shturm;

    public Solver() {
        shturm = new Shturm();
    }

    public Solver(double eps) {
        shturm = new Shturm(eps);
    }

    public double[] solve(Polynom polynom) {

        if (polynom.degree() == 0) {
            return new double[]{};
        } else if (polynom.degree() == 1) {
            double x;
            double[] c = polynom.getCoeffs();

            x = -c[1] / c[0];

            return new double[]{x};
        } else if (polynom.degree() == 2) {
            double d;
            double[] c = polynom.getCoeffs();

            d = Math.pow(c[1], 2) - 4 * c[2] * c[0];

            if (d == 0) {
                double x;

                x = -c[1] / (2 * c[2]);
                return new double[]{x};
            } else if (d > 0) {
                double x1, x2;

                x1 = (-c[1] + Math.sqrt(d)) / (2 * c[2]);
                x2 = (-c[1] - Math.sqrt(d)) / (2 * c[2]);

                return new double[]{x1, x2};
            } else if (d < 0) {
                return new double[]{};
            }
        } else if (polynom.degree() > 2) {
            Polynom q = polynom.gcd(polynom.differentiate());

            if (q.equals(1.0)) {
                return shturm.solve(polynom);
            } else {
                Polynom p = polynom.mod(q)[0];
                return shturm.solve(p);
            }
        }

        return new double[]{};
    }
}
