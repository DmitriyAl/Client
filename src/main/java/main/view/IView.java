package main.view;

import main.controller.IController;
import main.model.Command;

/**
 * @author Dmitriy Albot
 */
public interface IView {
    void setController(IController controller);

    void setDeskPainter(DeskPainter deskPainter);
}
