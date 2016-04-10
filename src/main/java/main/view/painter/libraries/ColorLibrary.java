package main.view.painter.libraries;

import main.view.painter.exceptions.NoSuchColorInLibraryException;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitriy Albot
 */
public class ColorLibrary {
    private static Map<Integer, Color> library;

    static {
        library = new HashMap<>();
        initLibrary();
    }

    private static void initLibrary() {
        library.put(-16777216, new Color(0, 0, 0));
    }

    public static void addColorToLibrary(int number, Color color) {
        library.put(number, color);
    }

    public static Color getColor(int key) {
        Color color = library.get(key);
        if (color == null) {
            throw new NoSuchColorInLibraryException("Library doesn\'t have this color");
        }
        return color;
    }
}
