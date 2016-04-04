package main.view;

import main.model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * @author Albot Dmitriy
 */
public class DotToDotPainter implements Painter {

    @Override
    public Graphics draw(final JPanel panel, Point point) {
        Graphics graphics = panel.getGraphics();
        int width = panel.getWidth();
        int height = panel.getHeight();
        Path2D path2D = new Path2D.Double();
        graphics.drawOval((int)(point.getX() * width), (int)(point.getY() * height),5,5);
        return graphics;
    }
}
