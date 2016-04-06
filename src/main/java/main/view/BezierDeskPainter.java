package main.view;

import main.model.*;
import main.model.Point;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author Albot Dmitriy
 */
public class BezierDeskPainter implements DeskPainter {
    @Override
    public Graphics draw(JPanel panel, Deque<Command> commands) {
        Graphics graphics = panel.getGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics;
        Deque<Deque<Command>> dividedFigures = divideToDifferentFigures(commands);
        bezierPainter(graphics2D, dividedFigures);

        return graphics2D;
    }

    public Deque<Deque<Command>> divideToDifferentFigures(Deque<Command> commands) {
        Deque<Deque<Command>> figures = new LinkedList<>();
        Deque<Command> figure = null;
        for (Command command : commands) {
            if (command.getType() == CommandType.START || figure == null) {
                figure = new LinkedList<>();
                figures.add(figure);
            }
            figure.add(command);
        }
        return figures;
    }

    private Graphics2D bezierPainter(final Graphics2D graphics2D, Deque<Deque<Command>> dividedFigures) {
        float accuracy = 0.01f;
        for (Deque<Command> figure : dividedFigures) {
            for (int i = 0; i < figure.size(); i++) {
                Deque<Point> bezierApproximatedPoints = bezierApproximation(figure, accuracy);
            }
        }
        return graphics2D;
    }

    private Deque<Point> bezierApproximation(Deque<Command> figure, float accuracy) {
        int size = figure.size();
        Queue<BigInteger> binomials = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            binomials.add(LocalMath.binomial(i, size));
        }
        for (int i = 0; i < size; i++) {

        }
        return null;
    }

    public static class LocalMath {
        public static BigInteger binomial(int k, int n) {
            return fact(n).divide(fact(k).multiply(fact(n - k)));
        }

        public static BigInteger fact(int number) {
            BigInteger fact = new BigInteger("1");
            for (int i = 1; i <= number; i++) {
                fact = fact.multiply(new BigInteger(String.valueOf(i)));
            }
            return fact;
        }
    }
}
