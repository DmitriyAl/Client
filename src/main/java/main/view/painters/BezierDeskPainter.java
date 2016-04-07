package main.view.painters;

import main.model.*;
import main.model.Point;
import main.view.ColorLibrary;
import main.view.NoSuchColorInLibraryException;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

/**
 * @author Albot Dmitriy
 */
public class BezierDeskPainter implements DeskPainter {
    private BezierPointCalculator calculator;

    public BezierDeskPainter() {
        calculator = new BezierPointCalculator(100);
    }

    public BezierDeskPainter(BezierPointCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public Graphics draw(JPanel panel, Deque<Command> incomingCommands) {
        Deque<Command> bezierCommands = calculator.transformToBezierPoints(incomingCommands);
        Command command = bezierCommands.peekLast();
        Graphics2D graphics = (Graphics2D) panel.getGraphics();
        Color color;
        try {
            color = ColorLibrary.getColor(command.getPoint().getColor());
        } catch (NoSuchColorInLibraryException e) {
            color = new Color(0, 0, 0);
        }
        if (command.getType() == CommandType.START || bezierCommands.size() == 1) {
            int x = ((int) (command.getPoint().getX() * panel.getWidth()));
            int y = ((int) (command.getPoint().getY() * panel.getHeight()));
            graphics.drawOval(x, y, 1, 1);
            return graphics;
        } else if (command.getType() == CommandType.MOVE) {
            while (bezierCommands.size() > 1) {
                Point origin = bezierCommands.pollLast().getPoint();
                Point next = bezierCommands.peekLast().getPoint();
                int x1 = ((int) (origin.getX() * panel.getWidth()));
                int y1 = ((int) (origin.getY() * panel.getHeight()));
                int x2 = ((int) (next.getX() * panel.getWidth()));
                int y2 = ((int) (next.getY() * panel.getHeight()));
                graphics.drawLine(x1, y1, x2, y2);
            }
            return graphics;
        }
        return graphics;
    }
}
