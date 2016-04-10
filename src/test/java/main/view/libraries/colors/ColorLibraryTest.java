package main.view.libraries.colors;

import main.view.libraries.colors.exceptions.NoSuchColorInLibraryException;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

/**
 * @author Dmitriy Albot
 */
public class ColorLibraryTest {
    @Test
    public void getColorTest() {
        Assert.assertEquals(new Color(0, 0, 0), ColorLibrary.getColor(-16777216));
    }

    @Test
    public void addColor() {
        ColorLibrary.addColorToLibrary(0, new Color(255, 255, 255));
        Assert.assertEquals(new Color(255, 255, 255), ColorLibrary.getColor(0));
    }

    @Test(expected = NoSuchColorInLibraryException.class)
    public void noSuchColor() {
        ColorLibrary.getColor(0);
    }
}
