package nonlinearEquation;

import expressionParser.estimator.ExpressionEstimator;

import java.util.Scanner;

public class ProblemGetter {

    public static Problem getNonlinearEquation() {
        Scanner in = new Scanner(System.in);

        System.out.println("Введите уравнение вида: f(x) = 0");
        String expression = getExpression(in).replaceAll("x", "x0");
        try {
            new ExpressionEstimator().compile(expression);
        } catch (Exception e) {
            abort("Неверный фомат ввода уравнения");
        }

        System.out.println("Введите границы поиска в формате: левая_граница правая_граница");
        Borders borders = getBorders(in);

        System.out.println("Введите точность вычисления корней");
        double accuracy = getAccuracy(in);
        try {
            return new Problem(expression, borders, accuracy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getExpression(Scanner in){
        String[] equationParts = in.nextLine().trim().split("=");
        if (equationParts.length == 2) {
            return equationParts[0];
        } else abort("Неверный формат ввода уравнения");
        return null;
    }

    private static Borders getBorders(Scanner in) {
        String[] isBorders = in.nextLine().trim().split(" ");
        if (isBorders.length == 2 &&
                isBorders[0].matches("-?\\d*(.\\d+)?") &&
                isBorders[1].matches("-?\\d*(.\\d+)?")) {
            return new Borders(Double.parseDouble(isBorders[0]),
                    Double.parseDouble(isBorders[1]));
        } else abort("Неверный формат ввода границ");
        return null;
    }

    private static double getAccuracy(Scanner in) {
        String isAccuracy = in.nextLine().trim();
        if (isAccuracy.matches("\\d*(.\\d+)?")) {
            return Double.parseDouble(isAccuracy);
        } else abort("Неверный формат ввода точности");
        return 0;
    }

    private static void abort(String message) {
        System.out.println(message);
        System.exit(-1);
    }

}
