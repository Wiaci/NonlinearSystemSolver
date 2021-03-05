import nonlinearEquation.Problem;
import nonlinearEquation.ProblemGetter;
import nonlinearEquation.Root;
import nonlinearEquation.solvers.SimpleIterationMethodSolver;
import nonlinearEquation.solvers.Solver;
import nonlinearEquation.solvers.TangentsMethodSolver;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Problem problem = ProblemGetter.getNonlinearEquation();

        Solver tangentsMethodSolver = new TangentsMethodSolver(problem);
        List<Root> tangentsMethodRoots = tangentsMethodSolver.solve();

        Solver simpleIterationSolver = new SimpleIterationMethodSolver(problem);
        List<Root> simpleIterationRoots = simpleIterationSolver.solve();

        System.out.println("Метод касательных:");
        printRoots(tangentsMethodRoots);

        System.out.println("\nМетод простых итераций:");
        printRoots(simpleIterationRoots);

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
