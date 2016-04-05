package main.view;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitriy Albot
 */
public class ColorLibrary {
    private Map<Integer, Color> library;

    public ColorLibrary() {
        library = new HashMap<>();
        initLibrary();
    }

    private void initLibrary() {
        library.put(-16777216, new Color(0, 0, 0));
    }

    public void addColorToLibrary(int number, Color color) {
        library.put(number, color);
    }

    public Color getColor(int key) {
        Color color = library.get(key);
        if (color == null) {
            throw new NoSuchColorInLibraryException("Library doesn\'t have this color");
        }
        return color;
    }
}
