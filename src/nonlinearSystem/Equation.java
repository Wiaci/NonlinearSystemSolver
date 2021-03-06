package nonlinearSystem;

import expressionParser.estimator.ExpressionEstimator;
import nonlinearEquation.Borders;

import java.util.Arrays;

public class Equation {

    private final ExpressionEstimator estimator;
    private final double DELTA = 0.001;

    public Equation(String equation) throws Exception {
        estimator = new ExpressionEstimator();
        estimator.compile(equation);
    }

    public double calculateInVector(double[] vector) throws Exception {
        return estimator.calculate(vector);
    }

    public double calculatePartialDerivativeInVector(double[] vector, int varIndex) throws Exception {
        double[] newVector = Arrays.copyOf(vector, vector.length);
        newVector[varIndex] += DELTA;
        return (calculateInVector(newVector) - calculateInVector(vector)) / DELTA;
    }
}
