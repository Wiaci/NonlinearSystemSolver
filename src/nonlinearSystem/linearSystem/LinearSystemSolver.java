package nonlinearSystem.linearSystem;

import nonlinearSystem.Matrix;

public class LinearSystemSolver {
    private static double triangulateMatrix(Matrix system) {
        double determinant = 1;

        for (int i = 0; i < system.getNumOfRows(); i++) {
            boolean colHasNonzero = false;
            for (int j = i; j < system.getNumOfRows(); j++) {
                double element = system.getElement(j, i);
                if (element != 0) {
                    colHasNonzero = true;
                    system.multiplyRowByNumber(j, 1 / element);
                    determinant *= element;
                    if (i != j) {
                        determinant *= -1;
                        system.swapRows(i, j);
                    }
                    break;
                }
            }
            if (!colHasNonzero) return 0;

            for (int j = i + 1; j < system.getNumOfRows(); j++) {
                system.subtractMultipliedRowFromRow(i, system.getElement(j, i), j);
            }
        }
        return determinant;
    }

    private static double[] getUnknowns(Matrix triangleMatrix) {
        double[] unknowns = new double[triangleMatrix.getNumOfRows()];
        for (int i = triangleMatrix.getNumOfRows() - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < triangleMatrix.getNumOfRows(); j++) {
                sum += triangleMatrix.getElement(i, j) * unknowns[j];
            }
            unknowns[i] = triangleMatrix.getElement(i, triangleMatrix.getNumOfRows()) - sum;
        }
        return unknowns;
    }

    private static double[] getResiduals(Matrix initSystem, Matrix triangleMatrix, double[] unknowns) {
        double[] residuals = new double[unknowns.length];
        for (int i = 0; i < triangleMatrix.getNumOfRows(); i++) {
            double result = 0;
            for (int j = 0; j < triangleMatrix.getNumOfRows(); j++) {
                result += unknowns[j] * initSystem.getElement(i, j);
            }
            residuals[i] = initSystem.getElement(i, triangleMatrix.getNumOfRows()) - result;
        }
        return residuals;
    }

    public static LinearSystemSolution solveSystem(Matrix system) {
        Matrix initSystem = (Matrix) system.clone();
        double determinant = triangulateMatrix(system);
        double[] unknowns = getUnknowns(system);
        double[] residuals = getResiduals(initSystem, system, unknowns);
        return new LinearSystemSolution(determinant, system, unknowns, residuals);
    }
}
