package main.view.painters;

import main.model.*;
import main.view.ColorLibrary;
import main.view.NoSuchColorInLibraryException;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * @author Dmitriy Albot
 */
public class LineDeskPainter implements DeskPainter {

    public LineDeskPainter() {
    }

    @Override
    public Graphics draw(JPanel panel, Deque<Command> commands) {
        Graphics graphics = panel.getGraphics();
        Graphics2D g2d = (Graphics2D) graphics;
        Command command = commands.peekLast();
        Color color;
        try {
            color = ColorLibrary.getColor(command.getPoint().getColor());
        } catch (NoSuchColorInLibraryException e) {
            color = new Color(0, 0, 0);
        }
        g2d.setColor(color);
        if (command.getType() == CommandType.START || commands.size() == 1) {
            int x = ((int) (command.getPoint().getX() * panel.getWidth()));
            int y = ((int) (command.getPoint().getY() * panel.getHeight()));
            g2d.drawOval(x, y, 1, 1);
            return graphics;
        } else if (command.getType() == CommandType.MOVE) {
            Command tempCommand = commands.pollLast();
            int x2 = ((int) (tempCommand.getPoint().getX() * panel.getWidth()));
            int y2 = ((int) (tempCommand.getPoint().getY() * panel.getHeight()));
            command = commands.peekLast();
            int x1 = ((int) (command.getPoint().getX() * panel.getWidth()));
            int y1 = ((int) (command.getPoint().getY() * panel.getHeight()));
            g2d.drawLine(x1, y1, x2, y2);
            commands.add(tempCommand);
            return graphics;
        }
        return g2d;
    }
}
