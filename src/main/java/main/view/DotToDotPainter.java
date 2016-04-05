package main.view;

import main.model.Command;
import main.model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * @author Albot Dmitriy
 */
public class DotToDotPainter implements Painter {

    @Override
    public Graphics draw(final JPanel panel, Command command) {
        Point point = command.getPoint();
        Graphics graphics = panel.getGraphics();
        int width = panel.getWidth();
        int height = panel.getHeight();
        graphics.drawOval((int) (point.getX() * width), (int) (point.getY() * height), 5, 5);
        return graphics;
    }
}
