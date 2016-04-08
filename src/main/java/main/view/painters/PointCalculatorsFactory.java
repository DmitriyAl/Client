package main.view.painters;

import main.view.painters.DrawingType;
import main.view.painters.exceptions.NoSuchPointCalculatorException;
import main.view.painters.point_calculators.BezierPointCalculator;
import main.view.painters.point_calculators.LinePointCalculator;
import main.view.painters.point_calculators.PointCalculator;

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
