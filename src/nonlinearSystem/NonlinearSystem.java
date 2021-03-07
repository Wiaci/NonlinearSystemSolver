package nonlinearSystem;

import expressionParser.estimator.ExpressionEstimator;
import nonlinearEquation.Borders;

import java.util.List;

public class NonlinearSystem {

    private List<Equation> equations;
    private List<Borders> borders;
    private double accuracy;

    public NonlinearSystem(List<Equation> equations, List<Borders> borders, double accuracy) {
        this.equations = equations;
        this.borders = borders;
        this.accuracy = accuracy;
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

    public List<Borders> getBorders() {
        return borders;
    }

    public void setBorders(List<Borders> borders) {
        this.borders = borders;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
