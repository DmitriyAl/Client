package main.view.painters;

import main.model.Command;
import main.model.Point;
import main.view.DrawingBoard;
import main.view.painters.point_calculators.PointCalculator;

import javax.swing.*;
import java.util.*;
import java.util.List;

/**
 * @author Albot Dmitriy
 */
public class DeskPainter {
    private PointCalculator calculator;

    public DeskPainter(PointCalculator calculator) {
        this.calculator = calculator;
    }

    public void draw(JPanel desk, Deque<Command> incomingCommands) {
        List<Point> transformedPoints = calculator.transformToDrawingPoints(incomingCommands);
        DrawingBoard drawingBoard = (DrawingBoard) desk;
        drawingBoard.setCurrentDrawingPicture(transformedPoints);
        drawingBoard.setSavedPictures(calculator.getPicture());
    }
    public void clearScreen() {
        calculator.clearScreen();
    }

    public void setAccuracy(int value) {
        calculator.setAccuracy(value);
    }

    public void redraw(JPanel desk) {
        DrawingBoard drawingBoard = (DrawingBoard) desk;
        drawingBoard.setCurrentDrawingPicture(calculator.getTransformedPoints());
        drawingBoard.setSavedPictures(calculator.getPicture());
    }
}
