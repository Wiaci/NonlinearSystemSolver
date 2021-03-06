package nonlinearSystem;

import nonlinearEquation.Borders;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NonlinearSystemGetter {

    public static NonlinearSystem getSystem() {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите количество уравнений:");
        int numOfEquations = getNumOfEquations(in);

        System.out.println("Введите уравнения в формате: f(x0, x1, ..., xn)=0");
        List<Equation> equations = getEquations(numOfEquations, in);

        List<Borders> borders = getBorders(numOfEquations, in);

        System.out.println("Введите точность вычисления корней");
        double accuracy = getAccuracy(in);

        return new NonlinearSystem(equations, borders, accuracy);
    }

    private static int getNumOfEquations(Scanner in) {
        String isNum = in.nextLine();
        if (isNum.matches("^\\d+$")) {
            return Integer.parseInt(isNum);
        } else abort("Неверный формат ввода!");
        return 0;
    }

    private static List<Equation> getEquations(int numOfEquations, Scanner in) {
        List<Equation> equations = new ArrayList<>();
        for (int i = 0; i < numOfEquations; i++) {
            String[] equation = in.nextLine().trim().split("=");
            if (equation.length == 2) {
                try {
                    equations.add(new Equation(equation[0]));
                } catch (Exception e) {
                    abort("Неверный формат ввода! " + e.getMessage());
                }
            } else abort("Неверный формат ввода уравнения!");
        }
        return equations;
    }

    private static List<Borders> getBorders(int numOfEquations, Scanner in) {
        List<Borders> borders = new ArrayList<>();

        for (int i = 0; i < numOfEquations; i++) {
            System.out.print("Границы для x" + i + ": ");
            String[] isBorders = in.nextLine().trim().split(" ");
            if (isBorders.length == 2) {
                if (isDouble(isBorders[0]) && isDouble(isBorders[1])) {
                    borders.add(new Borders(Double.parseDouble(isBorders[0]),
                            Double.parseDouble(isBorders[1])));
                } else abort("Неверный формат ввода!");
            } else abort("Границ должно быть 2!");
        }
        return borders;
    }

    private static double getAccuracy(Scanner in) {
        String isAccuracy = in.nextLine().trim();
        if (isAccuracy.matches("\\d*(.\\d+)?")) {
            return Double.parseDouble(isAccuracy);
        } else abort("Неверный формат ввода точности");
        return 0;
    }

    private static boolean isDouble(String x) {
        return x.matches("^-?\\d*(.\\d+)?$");
    }

    private static void abort(String message) {
        System.out.println(message);
        System.exit(-1);
    }


}
