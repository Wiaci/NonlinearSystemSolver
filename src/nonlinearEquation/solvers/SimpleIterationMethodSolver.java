package nonlinearEquation.solvers;

import nonlinearEquation.Borders;
import nonlinearEquation.Problem;
import nonlinearEquation.Root;

import java.util.ArrayList;
import java.util.List;

public class SimpleIterationMethodSolver extends Solver {

    double maxDerivativeValue;

    public SimpleIterationMethodSolver(Problem problem) {
        super(problem);
    }

    private boolean isConvergesIn(Borders interval, double multiplier) throws Exception {
        double maxDerivativeValue = -1;
        for (double i = interval.left(); i < interval.right(); i += 0.001) {
            double value = i - multiplier * problem.calculateIn(i);
            if (value < interval.left() || value > interval.right()) return false;

            double derivative = Math.abs(1 - multiplier * problem.calculateDerivativeIn(i));
            if (derivative > maxDerivativeValue) maxDerivativeValue = derivative;
        }
        if (maxDerivativeValue < 1) {
            this.maxDerivativeValue = maxDerivativeValue;
            return true;
        } else return false;
    }

    @Override
    public List<Root> solve() throws Exception {
        List<Root> roots = new ArrayList<>();
        List<Borders> rootIntervals = getRootIntervals();
        for (Borders interval : rootIntervals) {
            double multiplier = 0;
            for (double m = 0.01; m < 10; m += 0.01) {
                if (isConvergesIn(interval, m)) {
                    multiplier = m;
                    break;
                } else if (isConvergesIn(interval, -m)) {
                    multiplier = -m;
                    break;
                }
            }
            if (multiplier != 0) {
                double previousIteration = (interval.left() + interval.right()) / 2;
                double currentIteration;
                while (true) {
                    currentIteration = previousIteration -
                            multiplier * problem.calculateIn(previousIteration);
                    if (maxDerivativeValue / (1 - maxDerivativeValue)
                            * Math.abs(currentIteration - previousIteration) > problem.getAccuracy()) {
                        previousIteration = currentIteration;
                    } else break;
                }
                roots.add(new Root(currentIteration, interval, true));
            } else {
                roots.add(new Root(interval, false));
            }
        }
        return roots;
    }
}
