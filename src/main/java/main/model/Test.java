package main.model;

/**
 * @author Dmitriy Albot
 */
public class Test {
    public static void main(String[] args) {
        MACCommandPointParser parser = new MACCommandPointParser();
        Model model = new Model(parser);
        parser.parseCommand("60:21:C0:2A:E0:33;move;0.596326;0.44516215;-16777216");
    }
}
