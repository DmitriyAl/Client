package main.view.painters.point_calculators;

import main.view.painters.DrawingType;
import main.view.painters.exceptions.NoSuchPointCalculatorException;

/**
 * @author Dmitriy Albot
 */
public class PointCalculatorFactory {
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
