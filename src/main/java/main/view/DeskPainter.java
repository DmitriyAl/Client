package main.view;

import main.model.Command;
import main.model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * @author Albot Dmitriy
 */
public interface DeskPainter {
    Graphics draw(JPanel panel, Deque<Command> commands);
}
