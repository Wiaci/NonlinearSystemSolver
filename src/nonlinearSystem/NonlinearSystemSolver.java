package nonlinearSystem;

import nonlinearSystem.linearSystem.LinearSystemSolution;
import nonlinearSystem.linearSystem.LinearSystemSolver;

import java.util.ArrayList;
import java.util.List;

public class NonlinearSystemSolver {

    private final NonlinearSystem system;
    private double difference = Double.MAX_VALUE;

    public NonlinearSystemSolver(NonlinearSystem system) {
        this.system = system;
    }

    private boolean checkIfNeedToStop(double[] previousApproximation,
                                      double[] currentApproximation) throws InvalidInitialApproximationException {
        for (int i = 0; i < previousApproximation.length; i++) {
            double difference = Math.abs(currentApproximation[i] -
                    previousApproximation[i]);
            if (difference < this.difference) this.difference = difference;
            else throw new InvalidInitialApproximationException();
            if (difference > system.getAccuracy()) return false;
        }
        return true;
    }

    private double[] getApproximation(double[] previousApproximation,
                                      double[] increment) {
        double[] approximation = new double[increment.length];
        for (int i = 0; i < increment.length; i++) {
            approximation[i] = previousApproximation[i] + increment[i];
        }
        return approximation;
    }

    public double[] solve() throws Exception {
        double[] previousApproximation = system.getInitialApproximation();
        double[] currentApproximation;

        while (true) {
            List<List<Double>> matrix = new ArrayList<>();
            for (Equation equation : system.getEquations()) {
                List<Double> row = new ArrayList<>();
                for (int i = 0; i < system.getEquations().size(); i++) {
                    row.add(equation.calculatePartialDerivativeInVector(previousApproximation, i));
                }
                row.add(-equation.calculateInVector(previousApproximation));
                matrix.add(row);
            }
            LinearSystemSolution solution = LinearSystemSolver.solveSystem(new Matrix(matrix));
            double[] increment = solution.getUnknowns();
            currentApproximation = getApproximation(previousApproximation, increment);

            if (checkIfNeedToStop(previousApproximation, currentApproximation)) break;
            previousApproximation = currentApproximation;
        }
        return currentApproximation;
    }
}
