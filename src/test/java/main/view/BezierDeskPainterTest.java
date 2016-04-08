package main.view;

/**
 * @author Dmitriy Albot
 */
public class BezierDeskPainterTest {
//    @Test
//    public void divideToDifferentFiguresTestFromStartCoordinate() {
//        BezierDeskPainter painter = new BezierDeskPainter();
//        Deque<Command> commands = new LinkedList<>();
//        MACCommandPointParser parser = new MACCommandPointParser();
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.56640625;0.4779661;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.596326;0.44516215;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.6238175;0.42488006;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.673257;0.38544124;-16777216"));
//        Deque<List<Command>> lists = painter.divideToDifferentFigures(commands);
//        Assert.assertEquals(2,lists.size());
//    }

//    @Test
//    public void divideToDifferentFiguresTestFromMove() {
//        BezierDeskPainter painter = new BezierDeskPainter();
//        Deque<Command> commands = new LinkedList<>();
//        MACCommandPointParser parser = new MACCommandPointParser();
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.56640625;0.4779661;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.596326;0.44516215;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.6238175;0.42488006;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.673257;0.38544124;-16777216"));
//        Deque<List<Command>> lists = painter.divideToDifferentFigures(commands);
//        Assert.assertEquals(3,lists.size());
//    }

//    @Test
//    public void approximatedPointsListSize() {
//        BezierDeskPainter painter = new BezierDeskPainter();
//        Deque<Command> commands = new LinkedList<>();
//        MACCommandPointParser parser = new MACCommandPointParser();
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.56640625;0.4779661;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.6238175;0.42488006;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.596326;0.44516215;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.673257;0.38544124;-16777216"));
//        Deque<List<Command>> lists = painter.divideToDifferentFigures(commands);
//        Deque<Point> points = painter.bezierApproximation(lists.peekLast(), 5f);
//        System.out.println(points);
//    }

//    @Test
//    public void transformToBezierCoordinatesTest() {
//        BezierDeskPainter painter = new BezierDeskPainter();
//        Deque<Command> commands = new LinkedList<>();
//        MACCommandPointParser parser = new MACCommandPointParser();
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.56640625;0.4779661;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.5703401;0.4717115;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;start;0.6238175;0.42488006;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.596326;0.44516215;-16777216"));
//        commands.add(parser.parseCommand("60:21:C0:2A:E0:33;move;0.673257;0.38544124;-16777216"));
//        Deque<List<Command>> lists = painter.divideToDifferentFigures(commands);
//        System.out.println(lists.size());
//        Deque<Command> transformToDrawingPoints = painter.transformToDrawingPoints(lists);
//        System.out.println(transformToDrawingPoints.size());
//        for (int i = 0; i < transformToDrawingPoints.size(); i++) {
//            System.out.println(transformToDrawingPoints.pollLast());
//        }
//    }
}
