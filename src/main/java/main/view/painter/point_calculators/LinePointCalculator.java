package main.view.painter.point_calculators;

import main.model.CommandType;
import main.model.Point;

import java.util.ArrayList;

/**
 * @author Dmitriy Albot
 */
public class LinePointCalculator extends BezierPointCalculator {
    @Override
    protected void transformToBezierPoints() {
        int size = currentCommands.size();
        Point point = currentCommands.get(size - 1).getPoint();
        if (size == 1) {
            point.setCommand(CommandType.START);
            transformedPoints = new ArrayList<>();
        } else {
            point.setCommand(CommandType.MOVE);
        }
        transformedPoints.add(point);
    }
}

