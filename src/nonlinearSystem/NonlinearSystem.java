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
}
