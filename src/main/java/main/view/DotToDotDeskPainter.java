package main.view;

import main.model.Command;
import main.model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * @author Albot Dmitriy
 */
public class DotToDotDeskPainter implements DeskPainter {

    @Override
    public Graphics draw(final JPanel panel, Deque<Command> commands) {
        Point point = commands.poll().getPoint();
        Graphics graphics = panel.getGraphics();
        int width = panel.getWidth();
        int height = panel.getHeight();
        graphics.drawOval((int) (point.getX() * width), (int) (point.getY() * height), 5, 5);
        return graphics;
    }
}
