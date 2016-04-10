package main.controller;

import main.model.Model;
import main.view.View;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Dmitriy Albot
 */
public class ControllerTest {
    @Test
    public void setHostTest() {
        Model model = new Model();
        View view = View.getInstance(model);
        Controller controller = new Controller(model,view);
        Assert.assertTrue(controller.setHost("255.255.255.255"));
        Assert.assertTrue(controller.setHost("0.0.0.0"));
        Assert.assertFalse(controller.setHost("255.255.255.256"));
        Assert.assertFalse(controller.setHost("-1.3.4.5"));
        Assert.assertFalse(controller.setHost("1.3.4.5.7"));
    }

    @Test
    public void setPortTest() {
        Model model = new Model();
        View view = View.getInstance(model);
        Controller controller = new Controller(model,view);
        Assert.assertTrue(controller.setPort("0"));
        Assert.assertTrue(controller.setPort("65535"));
        Assert.assertFalse(controller.setPort("-1"));
        Assert.assertFalse(controller.setPort("65536"));
    }
}
