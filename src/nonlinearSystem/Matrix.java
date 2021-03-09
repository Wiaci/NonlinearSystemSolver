package nonlinearSystem;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Matrix implements Cloneable {

    private final List<List<Double>> values;
    private final int rows, cols;

    public Matrix(List<List<Double>> values) {
        this.values = values;
        rows = values.size();
        cols = values.get(0).size();
    }

    public double getElement(int row, int col) {
        if (row > rows || col > cols) throw new InvalidParameterException();
        return values.get(row).get(col);
    }

    public void swapRows(int row1, int row2) {
        List<Double> temp = values.get(row1);
        values.set(row1, values.get(row2));
        values.set(row2, temp);
    }

    public void multiplyRowByNumber(int row, double number) {
        List<Double> multipliedRow = new ArrayList<>();
        List<Double> currentRow = values.get(row);
        for (int i = 0; i < cols; i++) {
            multipliedRow.add(currentRow.get(i) * number);
        }
        values.set(row, multipliedRow);
    }

    public void subtractMultipliedRowFromRow(int rowToSubtract, double multiplier, int row) {
        List<Double> multipliedRow = new ArrayList<>();
        List<Double> currentRow = values.get(rowToSubtract);
        for (int i = 0; i < cols; i++) {
            multipliedRow.add(currentRow.get(i) * multiplier);
        }
        for (int i = 0; i < cols; i++) {
            values.get(row).set(i, values.get(row).get(i) - multipliedRow.get(i));
        }
    }

    private double getDeterminant() {
        double determinant = 1;
        List<List<Double>> copy = getValuesCopy();
        Matrix matrixCopy = new Matrix(copy);

        for (int i = 0; i < matrixCopy.getNumOfRows(); i++) {
            boolean colHasNonzero = false;
            for (int j = i; j < matrixCopy.getNumOfRows(); j++) {
                double element = matrixCopy.getElement(j, i);
                if (element != 0) {
                    colHasNonzero = true;
                    matrixCopy.multiplyRowByNumber(j, 1 / element);
                    determinant *= element;
                    if (i != j) {
                        determinant *= -1;
                        matrixCopy.swapRows(i, j);
                    }
                    break;
                }
            }
            if (!colHasNonzero) return 0;

            for (int j = i + 1; j < matrixCopy.getNumOfRows(); j++) {
                matrixCopy.subtractMultipliedRowFromRow(i, matrixCopy.getElement(j, i), j);
            }
        }
        return determinant;
    }

    private List<List<Double>> getValuesCopy() {
        List<List<Double>> copy = new ArrayList<>();
        for (List<Double> matrixRow : values) {
            copy.add(new ArrayList<>(matrixRow));
        }
        return copy;
    }

    public double getAlgebraicAdjunct(int row, int col) {
        int sign = ((row + col) % 2 == 0) ? 1 : -1;
        List<List<Double>> copy = getValuesCopy();
        copy.remove(row);
        for (List<Double> matrixRow : copy) {
            matrixRow.remove(col);
        }
        return sign * new Matrix(copy).getDeterminant();
    }

    private Matrix getTransposed() {
        List<List<Double>> transposedMatrix = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < rows; j++)
                row.add(values.get(j).get(i));
            transposedMatrix.add(row);
        }
        return new Matrix(transposedMatrix);
    }

    public Matrix getInverseMatrix() {
        List<List<Double>> inverseMatrix = new ArrayList<>();
        double determinant = getDeterminant();
        for (int i = 0; i < rows; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(getAlgebraicAdjunct(i, j) / determinant);
            }
            inverseMatrix.add(row);
        }
        return new Matrix(inverseMatrix).getTransposed();
     }

     public List<Double> multiplyByVector(List<Double> vector) {
         List<Double> multiplied = new ArrayList<>();
         for (int i = 0; i < rows; i++) {
             double sum = 0;
             for (int j = 0; j < vector.size(); j++) {
                 sum += getElement(i, j) * vector.get(j);
             }
             multiplied.add(sum);
         }
         return multiplied;
     }

    public int getNumOfRows() {
        return rows;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "values=" + values +
                '}';
    }
}
