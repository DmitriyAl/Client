package main.view.painter.point_calculators;

import main.model.Command;
import main.model.Point;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitriy Albot
 */
public abstract class PointCalculator {
    protected List<Command> currentCommands;
    protected List<Point> transformedPoints;
    protected List<List<Point>> picture;
    protected float accuracy;

    public PointCalculator() {
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

    public List<Point> getTransformedPoints() {
        return transformedPoints;
    }

    public abstract List<Point> transformToDrawingPoints(Deque<Command> commands);

    public void clearScreen() {
        picture = new ArrayList<>();
        currentCommands = new ArrayList<>();
        transformedPoints = new ArrayList<>();
    }
}
