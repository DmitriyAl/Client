package main.model;

/**
 * @author Dmitriy Albot
 */
public class Point {
    private float x;
    private float y;
    private int colour;
    private CommandType command;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(float x, float y, int colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    public Point(float x, float y, int colour, CommandType command) {
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.command = command;
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

    public CommandType getCommand() {
        return command;
    }

    public void setCommand(CommandType command) {
        this.command = command;
    }
}