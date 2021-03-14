import nonlinearEquation.*;
import nonlinearEquation.solvers.*;
import nonlinearSystem.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Что вы хотите решить?");
        System.out.println("1 - нелинейное уранение");
        System.out.println("2 - систему нелинейных уравнение");

        switch (new Scanner(System.in).nextLine()) {
            case "1": {
                Problem problem = ProblemGetter.getNonlinearEquation();

                Solver tangentsMethodSolver = new TangentsMethodSolver(problem);
                List<Root> tangentsMethodRoots = tangentsMethodSolver.solve();

                Solver simpleIterationSolver = new SimpleIterationMethodSolver(problem);
                List<Root> simpleIterationRoots = simpleIterationSolver.solve();

                System.out.println("Метод касательных:");
                printRoots(tangentsMethodRoots);

                System.out.println("\nМетод простых итераций:");
                printRoots(simpleIterationRoots);
                printPlotWithRoots(problem, tangentsMethodRoots, simpleIterationRoots);
                break;
            }
            case "2": {
                NonlinearSystem system = NonlinearSystemGetter.getSystem();
                NonlinearSystemSolver solver = new NonlinearSystemSolver(system);
                try {
                    double[] solution = solver.solve();
                    Arrays.stream(solution)
                            .forEach(System.out::println);
                } catch (InvalidInitialApproximationException e) {
                    System.out.println("Неверно указано начальное приближение");
                }
                break;
            }
        }
    }

    public static void printPlotWithRoots(Problem problem, List<Root> roots1, List<Root> roots2) {
        SwingUtilities.invokeLater(() -> {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension size = toolkit.getScreenSize();

            JFrame frame = new JFrame("Решение");
            frame.setSize(size);

            JComponent component = new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    final int width = size.width;
                    final int height = size.height;
                    Graphics2D graphics2D = (Graphics2D) g;
                    graphics2D.drawLine(0, height / 2, width, height / 2);
                    graphics2D.drawLine(width, height / 2, width - 25, height / 2 - 8);
                    graphics2D.drawLine(width, height / 2, width - 25, height / 2 + 8);

                    final double left = problem.getBorders().left() - 1;
                    final double right = problem.getBorders().right() + 1;

                    List<Point2D> points = new ArrayList<>();
                    final double step =  (width / (right - left));
                    for (int i = 0; i < width; i += 2) {
                        double x = i / step + left;
                        double y = problem.calculateIn(x);
                        points.add(new Point2D.Double(i, 384 - (64 * y)));
                    }
                    for (int i = 0; i < points.size() - 1; i++) {
                        Point2D point1 = points.get(i);
                        Point2D point2 = points.get(i + 1);
                        Line2D line = new Line2D.Double(point1.getX(), point1.getY(),
                                point2.getX(), point2.getY());
                        graphics2D.draw(line);
                    }
                    for (int i = (int) left - 1; i < right; i++) {
                        int x = (int) ((i - left) * step);
                        graphics2D.drawLine(x, 380, x, 388);
                        graphics2D.drawString(Integer.toString(i), x + 2, 395);
                        if (i == 0) {
                            graphics2D.drawLine(x, 0, x, height);
                            graphics2D.drawLine(x, 0, x + 8, 25);
                            graphics2D.drawLine(x, 0, x - 8, 25);
                            int val = 5;
                            for (int j = 64; j < height; j += 64) {
                                if (val != 0) {
                                    graphics2D.drawLine(x - 4, j, x + 4, j);
                                    graphics2D.drawString(Integer.toString(val), x + 6, j + 2);
                                }
                                val--;
                            }
                        }
                    }
                    for (Root root : roots2) {
                        int x = (int) ((root.getValue() - left) * step);
                        graphics2D.setColor(Color.GREEN);
                        graphics2D.fillOval(x - 2, height / 2 - 2, 4, 4);
                    }
                    for (Root root : roots1) {
                        int x = (int) (root.getValue() / step + left);
                        graphics2D.setColor(Color.RED);
                        graphics2D.drawOval(x - 2, height / 2 - 2, 4, 4);
                    }
                }
            };

            frame.add(component);
            frame.setVisible(true);
        });

    }

    public static void printRoots(List<Root> roots) {
        roots.stream()
                .filter(Root::isCalculated)
                .forEach(root -> System.out.printf("[%.2f; %.2f] -> %f\n",
                        root.getBorders().left(), root.getBorders().right(), root.getValue()));
        System.out.println("На интервалах");
        roots.stream()
                .filter(root -> !root.isCalculated())
                .forEach(root -> {
                    System.out.printf("[%.2f; %.2f]\n", root.getBorders().left(), root.getBorders().right());
                });
        System.out.println("корни найти не удалось");
    }


}