package main.view.painters;

import main.model.*;
import main.model.Point;
import main.view.BinomialCoefficientCalculator;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.*;

/**
 * @author Dmitriy Albot
 */
public class ImprovedBezierDeckPainter implements DeskPainter {

    public ImprovedBezierDeckPainter() {
    }

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
        Deque<java.util.List<Command>> figures = new LinkedList<>();
        java.util.List<Command> figure = null;
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
            Deque<main.model.Point> bezierApproximatedPoints = null;
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
        for (float i = 0; i <= 1; i += 1 / accuracy) {
            float xCoord = 0;
            float yCoord = 0;
            for (int j = 0; j < size; j++) {
                Point point =
                xCoord += BinomialCoefficientCalculator.getCoef(size,j).multiply()
                BigDecimal multipliedByAccuracyY = coefficientsY.get(j).multiply(new BigDecimal(Math.pow(i, j)));
                yCoord += Float.valueOf(String.valueOf(multipliedByAccuracyY.multiply(new BigDecimal(Math.pow((1 - i), (size - 1) - j)))));
            }
            Point point = new Point(xCoord, yCoord);
            bezierApproximatedPoints.add(point);
        }
        return bezierApproximatedPoints;
    }
}
