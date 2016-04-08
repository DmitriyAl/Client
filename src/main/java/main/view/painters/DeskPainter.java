package main.view.painters;

import main.model.Command;
import main.model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * @author Albot Dmitriy
 */
public interface DeskPainter {
    void draw(JPanel desk,Deque<Command> commands);

    void clearScreen();
}
