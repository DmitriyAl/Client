package main;

import main.controller.Controller;
import main.controller.IController;
import main.model.MACCommandPointParser;
import main.model.Model;
import main.model.Parser;
import main.view.DotToDotDeskPainter;
import main.view.IView;
import main.view.DeskPainter;
import main.view.View;

/**
 * @author Dmitriy Albot
 */
public class Main {
    public static void main(String[] args) {
        Parser parser = new MACCommandPointParser();
        DeskPainter deskPainter = new DotToDotDeskPainter();
        Model model = new Model(parser);
        IView view = View.getInstance(model);
        IController controller = new Controller(model, view);
        view.setController(controller);
        view.setDeskPainter(deskPainter);
    }
}
