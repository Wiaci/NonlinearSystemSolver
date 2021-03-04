package nonlinearEquation.solvers;

import nonlinearEquation.Borders;
import nonlinearEquation.Problem;
import nonlinearEquation.Root;

import java.util.ArrayList;
import java.util.List;

public class TangentsMethodSolver extends Solver {

    public TangentsMethodSolver(Problem problem) {
        super(problem);
    }

    private boolean isConvergesIn(double x) throws Exception {
        return problem.calculateIn(x) * problem.calculateSecondDerivativeIn(x) > 0;
    }

    public List<Root> solve() throws Exception {
        List<Borders> rootIntervals = getRootIntervals();
        List<Root> roots = new ArrayList<>();
        for (Borders interval : rootIntervals) {
            double previousApproximation = -1;
            if (isConvergesIn(interval.left())) {
                previousApproximation = interval.left();
            } else if (isConvergesIn(interval.right())) {
                previousApproximation = interval.right();
            } else {
                for (double i = interval.left() + 0.0001; i < interval.right(); i += 0.0001) {
                    if (isConvergesIn(i)) {
                        previousApproximation = i;
                    }
                }
                if (previousApproximation == -1) {
                    roots.add(new Root(interval, false));
                    continue;
                }
            }

            double currentApproximation;
            while (true) {
                currentApproximation = previousApproximation -
                        problem.calculateIn(previousApproximation) /
                                problem.calculateDerivativeIn(previousApproximation);
                if (Math.abs(currentApproximation - previousApproximation) > problem.getAccuracy()) {
                    previousApproximation = currentApproximation;
                } else break;
            }
            roots.add(new Root(currentApproximation, interval, true));
        }
        return roots;
    }
}
