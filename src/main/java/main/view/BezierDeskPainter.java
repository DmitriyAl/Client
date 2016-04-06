package main.view;

import main.model.*;
import main.model.Point;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * @author Albot Dmitriy
 */
public class BezierDeskPainter implements DeskPainter {
    private int numberOfApproximatedPoints = 5;

    @Override
    public Graphics draw(JPanel panel, Deque<Command> commands) {
        Graphics graphics = panel.getGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics;
        Deque<List<Command>> dividedFigures = divideToDifferentFigures(commands);
        Deque<Command> transformedCoordinates = transformToBezierPoints(dividedFigures);
        new LineDeskPainter().draw(panel, transformedCoordinates);
        return graphics2D;
    }

    public Deque<List<Command>> divideToDifferentFigures(Deque<Command> commands) {
        Deque<List<Command>> figures = new LinkedList<>();
        List<Command> figure = null;
        for (Command command : commands) {
            if (command.getType() == CommandType.START || figure == null) {
                figure = new ArrayList<>();
                figures.add(figure);
            }
            figure.add(command);
        }
        return figures;
    }

    public Deque<Command> transformToBezierPoints(Deque<List<Command>> dividedFigures) {
        Deque<Command> newPointCommands = new LinkedList<>();
        for (List<Command> figure : dividedFigures) {
            Deque<Point> bezierApproximatedPoints = null;
            for (int i = 0; i < figure.size(); i++) {
                bezierApproximatedPoints = bezierApproximation(figure, numberOfApproximatedPoints);
            }
            if (bezierApproximatedPoints != null) {
                newPointCommands.add(new Command("", CommandType.START, bezierApproximatedPoints.pollFirst()));
                for (int i = 0; i < bezierApproximatedPoints.size(); i++) {
                    newPointCommands.add(new Command("", CommandType.MOVE, bezierApproximatedPoints.pollFirst()));
                }
            }
        }
        return newPointCommands;
    }

    public Deque<Point> bezierApproximation(List<Command> figure, float accuracy) {
        Deque<Point> bezierApproximatedPoints = new LinkedList<>();
        int size = figure.size();
        List<BigDecimal> coefficientsX = new ArrayList<>();
        List<BigDecimal> coefficientsY = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            BigDecimal binomial = LocalMath.binomial(j, size - 1);
            coefficientsX.add(binomial.multiply(new BigDecimal(Float.toString(figure.get(j).getPoint().getX()))));
            coefficientsY.add(binomial.multiply(new BigDecimal(Float.toString(figure.get(j).getPoint().getY()))));
        }
        for (float i = 0; i <= 1; i += 1 / accuracy) {
            float xCoord = 0;
            float yCoord = 0;
            for (int j = 0; j < size; j++) {
                BigDecimal multipliedByAccuracyX = coefficientsX.get(j).multiply(new BigDecimal(Math.pow(i, j)));
                xCoord += Float.valueOf(String.valueOf(multipliedByAccuracyX.multiply(new BigDecimal(Math.pow((1 - i), (size - 1) - j)))));
                BigDecimal multipliedByAccuracyY = coefficientsY.get(j).multiply(new BigDecimal(Math.pow(i, j)));
                yCoord += Float.valueOf(String.valueOf(multipliedByAccuracyY.multiply(new BigDecimal(Math.pow((1 - i), (size - 1) - j)))));
            }
            Point point = new Point(xCoord, yCoord);
            bezierApproximatedPoints.add(point);
        }
        return bezierApproximatedPoints;
    }

    public static class LocalMath {
        public static BigDecimal binomial(int k, int n) {
            return fact(n).divide(fact(k).multiply(fact(n - k)));
        }

        public static BigDecimal fact(int number) {
            BigDecimal fact = new BigDecimal("1");
            for (int i = 1; i <= number; i++) {
                fact = fact.multiply(new BigDecimal(String.valueOf(i)));
            }
            return fact;
        }
    }
}
