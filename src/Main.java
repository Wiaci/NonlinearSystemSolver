import nonlinearEquation.Root;
import nonlinearSystem.*;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        /*Problem problem = ProblemGetter.getNonlinearEquation();

        Solver tangentsMethodSolver = new TangentsMethodSolver(problem);
        List<Root> tangentsMethodRoots = tangentsMethodSolver.solve();

        Solver simpleIterationSolver = new SimpleIterationMethodSolver(problem);
        List<Root> simpleIterationRoots = simpleIterationSolver.solve();

        System.out.println("Метод касательных:");
        printRoots(tangentsMethodRoots);

        System.out.println("\nМетод простых итераций:");
        printRoots(simpleIterationRoots);*/

        /*List<List<Double>> matrix = new ArrayList<>();
        List<Double> row1 = new ArrayList<>();
        List<Double> row2 = new ArrayList<>();
        matrix.add(row1);
        matrix.add(row2);
        //List<Double> row3 = new ArrayList<>();
        row1.add((double) 1);
        row1.add((double) 2);
        row2.add((double) 3);
        row2.add((double) 4);

        Matrix a = new Matrix(matrix);
        Matrix in = a.getInverseMatrix();
        System.out.println(a);
        System.out.println(in);

        List<Double> w = a.multiplyByVector(Arrays.asList((double) 5, (double) 6));
        System.out.println(w);*/
        NonlinearSystem system = NonlinearSystemGetter.getSystem();
        NonlinearSystemSolver solver = new NonlinearSystemSolver(system);
        try {
            double[] solution = solver.solve();
            Arrays.stream(solution)
                    .forEach(System.out::println);
        } catch (InvalidInitialApproximationException e) {
            System.out.println("Неверно указано начальное приближение");
        }
    }

    public static void printRoots(List<Root> roots) {
        roots.stream()
                .filter(Root::isCalculated)
                .forEach(root -> System.out.printf("[%.2f; %.2f] -> %f\n",
                        root.getBorders().left(), root.getBorders().right(), root.getValue()));
        roots.stream()
                .filter(root -> !root.isCalculated())
                .forEach(root -> {
                    System.out.println("На интервалах");
                    System.out.printf("[%.2f; %.2f]\n", root.getBorders().left(), root.getBorders().right());
                    System.out.println("корни найти не удалось");
                });
    }


}