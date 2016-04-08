package main.view;

import main.model.*;
import main.model.Point;
import main.view.painters.ColorLibrary;
import main.view.painters.exceptions.NoSuchColorInLibraryException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy Albot
 */
public class DrawingBoard extends JPanel {
    private List<List<Command>> savedPictures;
    private List<Command> currentDrawingPicture;

    public DrawingBoard() {
        savedPictures = new ArrayList<>();
        currentDrawingPicture = new ArrayList<>();
    }

    public void setSavedPictures(List<List<Command>> savedPictures) {
        this.savedPictures = savedPictures;
    }

    public void setCurrentDrawingPicture(List<Command> currentDrawingPicture) {
        this.currentDrawingPicture = currentDrawingPicture;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, getWidth(), getHeight());
        if (currentDrawingPicture.size() != 0 || savedPictures.size() != 0) {
            if (savedPictures.size() > 0) {
                for (int i = 0; i < savedPictures.size(); i++) {
                    draw(g, savedPictures.get(i));
                }
            }
            draw(g, currentDrawingPicture);
        }
    }

    private void draw(final Graphics graphics, List<Command> picture) {
        Command command = picture.get(0); //todo check last or first
        Color color;
        try {
            color = ColorLibrary.getColor(command.getPoint().getColor());
        } catch (NoSuchColorInLibraryException e) {
            color = new Color(0, 0, 0);
        }
        graphics.setColor(color);
        for (int i = 0; i < picture.size() - 1; i++) {
            Point origin = picture.get(i).getPoint();
            Point next = picture.get(i + 1).getPoint();
            if (picture.get(i + 1).getType() == CommandType.START) {
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

