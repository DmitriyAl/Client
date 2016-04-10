package main.view.painters.point_calculators;

import main.model.Command;
import main.model.CommandType;
import main.model.Point;
import main.view.libraries.binomials.BinomialCoefficientCalculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitriy Albot
 */
public class BezierPointCalculator extends PointCalculator {
    @Override
    public List<Point> transformToDrawingPoints(Deque<Command> commands) {
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

    protected void transformToBezierPoints() {
        int size = currentCommands.size();
        if (size == 1) {
            Point point = currentCommands.get(0).getPoint();
            point.setCommand(CommandType.START);
            transformedPoints.add(point);
            return;
        }
        if (size <= BinomialCoefficientCalculator.getMaxLongCoefficient()) {
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
                xCoord += BinomialCoefficientCalculator.getLongCoefficient(size - 1, j) * currentX * tInPower * oneMinusTInPower;
                yCoord += BinomialCoefficientCalculator.getLongCoefficient(size - 1, j) * currentY * tInPower * oneMinusTInPower;
            }
            Point bezierPoint = formAnewPoint(transformedPoints, xCoord, yCoord, point);
            transformedPoints.add(bezierPoint);
        }
    }

    private void slowTransform() {
        int size = currentCommands.size();
        transformedPoints = new ArrayList<>();
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
                xCoord += Float.valueOf(String.valueOf(BinomialCoefficientCalculator.getBigDecimalCoefficient(size - 1, j).multiply(new BigDecimal(currentX * tInPower * oneMinusTInPower))));
                yCoord += Float.valueOf(String.valueOf(BinomialCoefficientCalculator.getBigDecimalCoefficient(size - 1, j).multiply(new BigDecimal(currentY * tInPower * oneMinusTInPower))));
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
}
