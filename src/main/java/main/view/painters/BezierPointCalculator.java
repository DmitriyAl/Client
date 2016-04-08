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
    private List<Command> transformedPoints;
    private List<List<Command>> picture;
    private float accuracy;

    public BezierPointCalculator() {
        accuracy = 100;
        currentCommands = new LinkedList<>();
        transformedPoints = new LinkedList<>();
        picture = new LinkedList<>();
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public List<List<Command>> getPicture() {
        return picture;
    }

    public List<Command> transformToBezierPoints(Deque<Command> commands) {
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
            transformedPoints.add(new Command("", CommandType.START, currentCommands.get(0).getPoint()));
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
            Command currentCommand = formACommand(transformedPoints, xCoord, yCoord, point.getColor());
            transformedPoints.add(currentCommand);
        }
    }

    private List<Command> slowTransform() {
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
            Command currentCommand = formACommand(transformedPoints, xCoord, yCoord, point.getColor());
            transformedPoints.add(currentCommand);
        }
        return transformedPoints;
    }

    private Command formACommand(List<Command> commands, float xCoord, float yCoord, int color) {
        if (commands.size() == 0) {
            return new Command("", CommandType.START, new Point(xCoord, yCoord, color));
        }
        return new Command("", CommandType.MOVE, new Point(xCoord, yCoord, color));
    }

    public void clearScreen() {
        picture = new LinkedList<>();
        currentCommands = new LinkedList<>();
    }
}
