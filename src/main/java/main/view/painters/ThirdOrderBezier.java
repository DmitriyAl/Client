//package main.view.painters;
//
//import main.model.Command;
//import main.view.ColorLibrary;
//import main.view.NoSuchColorInLibraryException;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.geom.Path2D;
//import java.util.ArrayList;
//import java.util.Deque;
//
///**
// * @author Dmitriy Albot
// */
//public class ThirdOrderBezier implements DeskPainter {
//    @Override
//    public Graphics draw(JPanel panel, Deque<Command> commands) {
//        Graphics graphics = panel.getGraphics();
//        Graphics2D g2d = (Graphics2D) graphics;
//        Path2D path2D = new Path2D.Double();
//        Command command = commands.pollFirst();
//        Color color;
//        try {
//            color = ColorLibrary.getColor(command.getPoint().getColor());
//        } catch (NoSuchColorInLibraryException e) {
//            color = new Color(0, 0, 0);
//        }
//        graphics.setColor(color);
//        path2D.moveTo(command.getPoint().getX(), command.getPoint().getY());
//        if ((commands.size() - 1) % 3 != 0 || commands.size() < 5) {
//            return g2d;
//        }
//
//
//        ArrayList<Point> points = new ArrayList<>();
//        points.add(new Point(0, 0));
//        for (int i = 0; i < 97; i++) {
//            points.add(new Point(Math.random() * getWidth(), Math.random() * getHeight()));
//        }
//        points.add(new Point(getWidth(), getHeight()));
//
//
//        ArrayList<Point> resultPoints = createBezier(points);
//        path2D.moveTo(0, 0);
//
//
//        for (int i = 0; i < resultPoints.size() - 2; i += 3) {
////                if (i>=resultPoints.size())
//            path2D.curveTo(resultPoints.get(i).getX(), resultPoints.get(i).getY(),
//                    resultPoints.get(i + 1).getX(), resultPoints.get(i + 1).getY(),
//                    resultPoints.get(i + 2).getX(), resultPoints.get(i + 2).getY());
//        }
//        g2d.setColor(new Color(0, 0, 0));
//        g2d.draw(path2D);
//        return null;
//    }
//}
