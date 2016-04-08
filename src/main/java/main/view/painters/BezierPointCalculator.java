package main.view.painters;

import main.model.*;
import main.model.Point;
import main.view.painters.helpers.BinomialCoefficientCalculator;

import java.math.BigDecimal;
import java.util.List;
import java.util.*;

/**
 * @author Dmitriy Albot
 */
public class BezierPointCalculator {
    private List<Command> currentCommands;
    private List<Point> transformedPoints;
    private List<List<Point>> picture;
    private float accuracy;

    public BezierPointCalculator() {
        currentCommands = new LinkedList<>();
        transformedPoints = new LinkedList<>();
        picture = new LinkedList<>();
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public List<List<Point>> getPicture() {
        return picture;
    }

    public List<Point> transformToBezierPoints(Deque<Command> commands) {
        Command currentCommand = commands.peekLast();
        if (currentCommand.getType() == CommandType.START || commands.size() == 1) {
            saveTransformedPoints();
            currentCommands = new ArrayList<>();
        }
        currentCommands.add(currentCommand);
        transformToBezierPoints();
        return transformedPoints;
    }

    private void saveTransformedPoints() {
        if (transformedPoints.size() != 0) {
            picture.add(transformedPoints);
        }
    }

    private void transformToBezierPoints() {
        int size = currentCommands.size();
        if (size == 1) {
            Point point = currentCommands.get(0).getPoint();
            point.setCommand(CommandType.START);
            transformedPoints.add(point);
            return;
        }
        if (size <= BinomialCoefficientCalculator.getMaxLongCoef()) {
            fastTransform();
        } else {
            slowTransform();
        }
    }

    private void fastTransform() {
        int size = currentCommands.size();
        transformedPoints = new LinkedList<>();
        for (float t = 0; t <= 1; t += 1f / (accuracy * size)) {
            float xCoord = 0;
            float yCoord = 0;
            Point point = null;
            for (int j = 0; j < size; j++) {
                point = currentCommands.get(j).getPoint();
                float currentX = point.getX();
                float currentY = point.getY();
                double tInPower = Math.pow(t, j);
                double oneMinusTInPower = Math.pow(1 - t, size - 1 - j);
                xCoord += BinomialCoefficientCalculator.getLongCoef(size - 1, j) * currentX * tInPower * oneMinusTInPower;
                yCoord += BinomialCoefficientCalculator.getLongCoef(size - 1, j) * currentY * tInPower * oneMinusTInPower;
            }
            Point bezierPoint = formAnewPoint(transformedPoints, xCoord, yCoord, point);
            transformedPoints.add(bezierPoint);
        }
    }

    private void slowTransform() {
        int size = currentCommands.size();
        transformedPoints = new LinkedList<>();
        for (float t = 0; t <= 1; t += 1f / (accuracy * size)) {
            float xCoord = 0;
            float yCoord = 0;
            Point point = null;
            for (int j = 0; j < size; j++) {
                point = currentCommands.get(j).getPoint();
                float currentX = point.getX();
                float currentY = point.getY();
                double tInPower = Math.pow(t, j);
                double oneMinusTInPower = Math.pow(1 - t, size - 1 - j);
                xCoord += Float.valueOf(String.valueOf(BinomialCoefficientCalculator.getBigIntCoef(size-1, j).multiply(new BigDecimal(currentX * tInPower * oneMinusTInPower))));
                yCoord += Float.valueOf(String.valueOf(BinomialCoefficientCalculator.getBigIntCoef(size-1, j).multiply(new BigDecimal(currentY * tInPower * oneMinusTInPower))));
            }
            Point bezierPoint = formAnewPoint(transformedPoints, xCoord, yCoord, point);
            transformedPoints.add(bezierPoint);
        }
    }

    private Point formAnewPoint(List<Point> transformedPoints, float xCoord, float yCoord, Point point) {
        if (transformedPoints.size() == 0) {
            return new Point(xCoord, yCoord, point.getColor(), CommandType.START);
        }
        return new Point(xCoord, yCoord, point.getColor(), CommandType.MOVE);
    }

    public void clearScreen() {
        picture = new LinkedList<>();
        currentCommands = new LinkedList<>();
    }
}
