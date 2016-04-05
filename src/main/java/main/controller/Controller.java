package main.controller;

import main.model.IModel;
import main.view.IView;

/**
 * @author Dmitriy Albot
 */
public class Controller implements IController {
    private IModel model;
    private IView view;

    public Controller(IModel model, IView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void startConnection() {
        Thread modelThread = new Thread(new Runnable() {
            @Override
            public void run() {
                model.startClient();
            }
        });
        modelThread.start();
    }

    @Override
    public void pauseConnection() {
        model.pauseConnection();
    }

    @Override
    public void resumeConnection() {
        Thread resumeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                model.resumeConnection();
            }
        });
        resumeThread.start();
    }
}
