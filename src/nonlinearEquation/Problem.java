package nonlinearEquation;

import expressionParser.estimator.ExpressionEstimator;

public class Problem {

    private ExpressionEstimator estimator;
    private Borders borders;
    private double accuracy;
    private final double DELTA = 0.001;

    public Problem(String expression, Borders borders, double accuracy) throws Exception {

        this.borders = borders;
        this.accuracy = accuracy;

        estimator = new ExpressionEstimator();
        estimator.compile(expression);
    }

    public double calculateIn(double x) {
        try {
            double[] val = new double[] {x};
            return estimator.calculate(val);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double calculateDerivativeIn(double x) {
        return (calculateIn(x + DELTA) - calculateIn(x - DELTA)) / DELTA / 2;
    }

    public double calculateSecondDerivativeIn(double x) {
        return (calculateIn(x + DELTA) - 2 * calculateIn(x)
                + calculateIn(x - DELTA)) / (DELTA * DELTA);
    }

    public Borders getBorders() {
        return borders;
    }

    public double getAccuracy() {
        return accuracy;
    }
}
