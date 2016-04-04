package main.view;

import main.model.Point;

import javax.swing.*;

/**
 * @author Albot Dmitriy
 */
public interface Painter {
    void paint(JPanel panel, Point point);
}
