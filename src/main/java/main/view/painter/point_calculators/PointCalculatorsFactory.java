package main.view.painter.point_calculators;

import main.view.painter.DrawingType;
import main.view.painter.exceptions.NoSuchPointCalculatorException;

/**
 * @author Dmitriy Albot
 */
public class PointCalculatorsFactory {
    public static PointCalculator getPainter(DrawingType type) {
        switch (type) {
            case BEZIER:
                return new BezierPointCalculator();
            case LINES:
                return new LinePointCalculator();
            default:
                throw new NoSuchPointCalculatorException("Client doesn\'t have this painter");
        }
    }
}
