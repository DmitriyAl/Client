package main.view;

import main.model.*;
import main.model.Point;
import main.view.painters.ColorLibrary;
import main.view.painters.exceptions.NoSuchColorInLibraryException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.LinkedList;

/**
 * @author Dmitriy Albot
 */
public class DrawingBoard extends JPanel {
    private List<Deque<Command>> savedPictures;
    private Deque<Command> currentDrawingPicture;

    public DrawingBoard() {
        savedPictures = new ArrayList<>();
        currentDrawingPicture = new LinkedList<>();
    }

    public void setSavedPictures(List<Deque<Command>> savedPictures) {
        this.savedPictures = savedPictures;
    }

    public void setCurrentDrawingPicture(Deque<Command> currentDrawingPicture) {
        this.currentDrawingPicture = currentDrawingPicture;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
        if (currentDrawingPicture.size() != 0 || savedPictures.size() != 0) {
            if (savedPictures.size() > 0) {
                for (Deque<Command> next : savedPictures) {
                    draw(g, next);
                }
            }
            draw(g, currentDrawingPicture);
        }
    }

    private void draw(final Graphics graphics, Deque<Command> picture) {
        Command command = picture.peekFirst(); //todo check last or first
        Color color;
        try {
            color = ColorLibrary.getColor(command.getPoint().getColor());
        } catch (NoSuchColorInLibraryException e) {
            color = new Color(0, 0, 0);
        }
        graphics.setColor(color);
        while (picture.size() > 1) {
            Point origin = picture.pollFirst().getPoint();
            Point next = picture.peekFirst().getPoint();
            if (picture.peekFirst().getType() == CommandType.START) {
                break;
            }
            int x1 = ((int) (origin.getX() * getWidth()));
            int y1 = ((int) (origin.getY() * getHeight()));
            int x2 = ((int) (next.getX() * getWidth()));
            int y2 = ((int) (next.getY() * getHeight()));
            graphics.drawLine(x1, y1, x2, y2);
        }
    }
}

