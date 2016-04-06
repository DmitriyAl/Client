package main.model;

/**
 * @author Dmitriy Albot
 */
public class Point {
    private float x;
    private float y;
    private int colour;

    public Point() {
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(float x, float y, int colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getColor() {
        return colour;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", colour=" + colour +
                '}';
    }
}
