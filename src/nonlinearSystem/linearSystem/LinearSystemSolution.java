package nonlinearSystem.linearSystem;

import nonlinearSystem.Matrix;

public class LinearSystemSolution {

    private final double determinant;
    private final Matrix triangleMatrix;
    private final double[] unknowns;
    private final double[] residuals;

    public LinearSystemSolution(double determinant, Matrix triangleMatrix, double[] unknowns, double[] residuals) {
        this.determinant = determinant;
        this.triangleMatrix = triangleMatrix;
        this.unknowns = unknowns;
        this.residuals = residuals;
    }

    public double getDeterminant() {
        return determinant;
    }

    public Matrix getTriangleMatrix() {
        return triangleMatrix;
    }

    public double[] getUnknowns() {
        return unknowns;
    }

    public double[] getResiduals() {
        return residuals;
    }
}
