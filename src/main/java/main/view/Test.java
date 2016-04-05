package main.view;

import main.model.Model;

/**
 * @author Dmitriy Albot
 */
public class Test {
    public static void main(String[] args) {
        View view = View.getInstance(new Model());
        view.setPainter(new DotToDotPainter());
        view.updateGraphics();
    }
}
