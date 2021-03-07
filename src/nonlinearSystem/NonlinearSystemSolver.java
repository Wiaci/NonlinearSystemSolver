package nonlinearSystem;

import java.util.ArrayList;
import java.util.List;

public class NonlinearSystemSolver {

    private final NonlinearSystem system;

    public NonlinearSystemSolver(NonlinearSystem system) {
        this.system = system;
    }

    private List<List<Double>> getInitialApproximations() throws Exception {
        List<List<Double>> initialApproximations = new ArrayList<>();
        for (double i = system.getBorders().get(0).left(); i < system.getBorders().get(0).right(); i += 0.01) {
            for (double j = system.getBorders().get(1).left(); j < system.getBorders().get(1).right(); j += 0.01) {
                List<Double> initApprox = new ArrayList<>();
                for (Equation equation : system.getEquations()) {
                    double value = equation.calculateInVector(new double[] {i, j});
                    if (value > 0.1) {
                        initApprox.clear();
                        break;
                    }
                    initApprox.add(value);
                }
                if (initApprox.size() > 0) initialApproximations.add(initApprox);
            }
        }
        return initialApproximations;
    }

    public List<Double[]> solve() throws Exception {
        List<List<Double>> initialApproximations = getInitialApproximations();

        return null;
    }

}
