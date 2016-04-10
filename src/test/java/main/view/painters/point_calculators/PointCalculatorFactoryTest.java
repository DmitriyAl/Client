package main.view.painters.point_calculators;

import main.view.painters.DrawingType;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Dmitriy Albot
 */
public class PointCalculatorFactoryTest {
    @Test
    public void factoryTest() {
        Assert.assertTrue(PointCalculatorFactory.getPainter(DrawingType.BEZIER) instanceof BezierPointCalculator);
        Assert.assertTrue(PointCalculatorFactory.getPainter(DrawingType.LINES) instanceof LinePointCalculator);
    }
}
