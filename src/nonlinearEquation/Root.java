package nonlinearEquation;

public class Root {

    private double value;
    private Borders borders;
    private boolean isCalculated;

    public Root(Borders borders, boolean isCalculated) {
        this.borders = borders;
        this.isCalculated = isCalculated;
    }

    public Root(double value, Borders borders, boolean isCalculated) {
        this.value = value;
        this.borders = borders;
        this.isCalculated = isCalculated;
    }

    public double getValue() {
        return value;
    }

    public Borders getBorders() {
        return borders;
    }

    public boolean isCalculated() {
        return isCalculated;
    }
}
