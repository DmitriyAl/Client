package main;

import main.controller.Controller;
import main.controller.IController;
import main.model.parsers.MACCommandPointParser;
import main.model.Model;
import main.model.parsers.Parser;
import main.view.IView;
import main.view.View;

/**
 * @author Dmitriy Albot
 */
public class Main {
    public static void main(String[] args) {
        Parser parser = new MACCommandPointParser();
        Model model = new Model(parser);
        IView view = View.getInstance(model);
        IController controller = new Controller(model, view);
        view.setController(controller);
    }
}
