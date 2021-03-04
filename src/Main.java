import nonlinearEquation.Problem;
import nonlinearEquation.ProblemGetter;
import nonlinearEquation.Root;
import nonlinearEquation.solvers.TangentsMethodSolver;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        Problem problem = ProblemGetter.getNonlinearEquation();
        TangentsMethodSolver solver = new TangentsMethodSolver(problem);
        List<Root> roots = solver.solve();
        roots.stream()
                .filter(x -> x.isCalculated())
                .forEach(x -> System.out.println("На интервале [" + x.getBorders().left() + "; " +
                        x.getBorders().right() + "] корень " + x.getValue()));
        System.out.println("\nНа интервалах");
        roots.stream()
                .filter(x -> !x.isCalculated())
                .forEach(x -> System.out.println("[" + x.getBorders().left() + "; " +
                        x.getBorders().right() + "]"));
        System.out.println("корни методом Ньютона найти не удалось");
    }

}
