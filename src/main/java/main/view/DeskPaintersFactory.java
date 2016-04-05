package main.view;

/**
 * @author Dmitriy Albot
 */
public class DeskPaintersFactory {
    public static DeskPainter getPainter(DrawingType type) {
        switch (type) {
            case BY_DOTES:
                return new DotToDotDeskPainter();
            case BEZIER:
                return new BezierDeskPainter();
            default:
                throw new NoSuchPainterException("Client doesn\'t have this painter");
        }
    }
}
