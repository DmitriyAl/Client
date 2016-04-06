package main.view;

import main.model.Command;
import main.model.MACCommandPointParser;
import main.model.Point;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitriy Albot
 */
public class BezierDeskPainterTest {
    @Test
    public void divideToDifferentFiguresTestFromStartCoordinate() {
        BezierDeskPainter painter = new BezierDeskPainter();
        Deque<Command> commands = new LinkedList<>();
        MACCommandPointParser parser = new MACCommandPointParser();
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.56640625;0.4779661;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.596326;0.44516215;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.6238175;0.42488006;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.673257;0.38544124;-16777216"));
        Deque<List<Command>> lists = painter.divideToDifferentFigures(commands);
        Assert.assertEquals(2,lists.size());
    }

    @Test
    public void divideToDifferentFiguresTestFromMove() {
        BezierDeskPainter painter = new BezierDeskPainter();
        Deque<Command> commands = new LinkedList<>();
        MACCommandPointParser parser = new MACCommandPointParser();
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.56640625;0.4779661;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.596326;0.44516215;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.6238175;0.42488006;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.673257;0.38544124;-16777216"));
        Deque<List<Command>> lists = painter.divideToDifferentFigures(commands);
        Assert.assertEquals(3,lists.size());
    }

//    @Test
//    public void factTest() {
//        BigDecimal fact = BezierDeskPainter.LocalMath.fact(4);
//        Assert.assertEquals(new BigInteger("24"), fact);
//        fact = BezierDeskPainter.LocalMath.fact(0);
//        Assert.assertEquals(new BigInteger("1"), fact);
//        fact = BezierDeskPainter.LocalMath.fact(1);
//        Assert.assertEquals(new BigInteger("1"), fact);
//        fact = BezierDeskPainter.LocalMath.fact(5);
//        Assert.assertEquals(new BigInteger("120"), fact);
//    }

    @Test
    public void approximatedPointsListSize() {
        BezierDeskPainter painter = new BezierDeskPainter();
        Deque<Command> commands = new LinkedList<>();
        MACCommandPointParser parser = new MACCommandPointParser();
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.56640625;0.4779661;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.6238175;0.42488006;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.596326;0.44516215;-16777216"));
        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.673257;0.38544124;-16777216"));
        Deque<List<Command>> lists = painter.divideToDifferentFigures(commands);
        Deque<Point> points = painter.bezierApproximation(lists.peekLast(), 5f);
        System.out.println(points);
    }
}
