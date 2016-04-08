package main.view.painters;

import main.model.*;
import main.model.Point;
import main.view.DrawingBoard;
import main.view.painters.exceptions.NoSuchColorInLibraryException;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Albot Dmitriy
 */
public class BezierDeskPainter implements DeskPainter {
    private BezierPointCalculator calculator;

    public BezierDeskPainter() {
        calculator = new BezierPointCalculator(200);
    }

    public BezierDeskPainter(BezierPointCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void draw(JPanel desk, Deque<Command> incomingCommands) {
        List<Command> bezierCommands = calculator.transformToBezierPoints(incomingCommands);
        DrawingBoard drawingBoard = (DrawingBoard) desk;
        drawingBoard.setCurrentDrawingPicture(bezierCommands);
        drawingBoard.setSavedPictures(calculator.getPicture());
    }
}
