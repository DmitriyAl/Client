package main.model;

/**
 * @author Dmitriy Albot
 */
public class Command {
    private String mac;
    private CommandType type;
    private Point point;

    public Command(String mac, CommandType type, Point point) {
        this.mac = mac;
        this.type = type;
        this.point = point;
    }

    public String getMac() {
        return mac;
    }

    public CommandType getType() {
        return type;
    }

    public Point getPoint() {
        return point;
    }
}
