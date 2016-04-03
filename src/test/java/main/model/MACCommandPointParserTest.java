package main.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Dmitriy Albot
 */
public class MACCommandPointParserTest {
    @Test
    public void parseCommandTest() {
        MACCommandPointParser parser = new MACCommandPointParser();
        Command command = parser.parseCommand("60:21:C0:2A:E0:33;move;0.15625;0.29208717;-16777216");
        Point point = command.getPoint();
        Assert.assertEquals(0.15625,point.getX(),0.00001);
        Assert.assertEquals(0.29208717,point.getY(),0.00001);
        Assert.assertEquals(-16777216,point.getColour());
    }
}
