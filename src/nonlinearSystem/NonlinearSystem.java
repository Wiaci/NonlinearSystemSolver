package nonlinearSystem;

import expressionParser.estimator.ExpressionEstimator;
import nonlinearEquation.Borders;

import java.util.List;

public class NonlinearSystem {

    private List<Equation> equations;
    private final double[] initialApproximation;
    private double accuracy;

    public NonlinearSystem(List<Equation> equations, double[] initialApproximation, double accuracy) {
        this.equations = equations;
        this.initialApproximation = initialApproximation;
        this.accuracy = accuracy;
    }

    public double[] getInitialApproximation() {
        return initialApproximation;
    }

    public List<Equation> getEquations() {
        return equations;
    }

    public Equation getEquation(int i) {
        return equations.get(i);
    }

    public void setEquations(List<Equation> equations) {
        this.equations = equations;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
