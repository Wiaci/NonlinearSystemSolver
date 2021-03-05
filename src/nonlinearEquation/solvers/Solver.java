package nonlinearEquation.solvers;

import nonlinearEquation.Borders;
import nonlinearEquation.Problem;
import nonlinearEquation.Root;

import java.util.ArrayList;
import java.util.List;

public abstract class Solver {

    protected final Problem problem;

    public Solver(Problem problem) {
        this.problem = problem;
    }

    protected List<Borders> getRootIntervals() throws Exception {
        List<Borders> rootIntervals = new ArrayList<>();
        double previousX = problem.getBorders().left();
        for (double x = problem.getBorders().left() + 0.01; x < problem.getBorders().right(); x += 0.01) {
            if (problem.calculateIn(x) * problem.calculateIn(previousX) < 0) {
                rootIntervals.add(new Borders(previousX, x));
            }
            previousX = x;
        }
        return rootIntervals;
    }

    public List<Root> solve() throws Exception {
        return new ArrayList<>();
    }
}
