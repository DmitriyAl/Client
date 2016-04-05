package main.view;

import main.model.Command;
import main.model.Point;

import javax.swing.*;
import java.awt.*;

/**
 * @author Albot Dmitriy
 */
public interface Painter {
    Graphics draw(JPanel panel, Command command);
}
