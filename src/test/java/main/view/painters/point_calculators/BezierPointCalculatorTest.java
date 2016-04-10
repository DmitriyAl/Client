package main.view.painters.point_calculators;

import main.model.Command;
import main.model.Point;
import main.model.parsers.MACCommandPointParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitriy Albot
 */
public class BezierPointCalculatorTest {
    PointCalculator painter;
    Deque<Command> commands;
    MACCommandPointParser parser;

    @Before
    public void init() {
        painter = new BezierPointCalculator();
        commands = new LinkedList<>();
        parser = new MACCommandPointParser();
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.56640625;0.4779661;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.6238175;0.42488006;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.673257;0.38544124;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.596326;0.44516215;-16777216"));
    }

    @Test
    public void coordinateTransformationTest() {
        painter.setAccuracy(10);
        List<Point> list = new LinkedList<>();
        Deque<Command> currentCommand = new LinkedList<>();
        while (commands.size() > 0) {
            currentCommand.add(commands.pollFirst());
            list = painter.transformToDrawingPoints(currentCommand);
        }
        Assert.assertEquals(30, list.size());
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.673257;0.38544124;-16777216"));
        list = painter.transformToDrawingPoints(currentCommand);
        Assert.assertEquals(41, list.size());
    }
}
