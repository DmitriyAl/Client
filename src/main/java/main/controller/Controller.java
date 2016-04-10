package main.controller;

import main.controller.exceptions.InvalidHostException;
import main.model.IModel;
import main.view.IView;

import java.util.StringTokenizer;

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

    @Override
    public boolean setHost(String host) {
        try {
            parseHost(host);
            model.setHost(host);
            return true;
        } catch (InvalidHostException e) {
            return false;
        }
    }

    private void parseHost(String host) {
        StringTokenizer parser = new StringTokenizer(host, ".");
        if (parser.countTokens() != 4) {
            throw new InvalidHostException();
        }
        while (parser.hasMoreTokens()) {
            try {
                int value = Integer.parseInt(parser.nextToken());
                if (value > 255 || value < 0) {
                    throw new InvalidHostException();
                }
            } catch (NumberFormatException e) {
                throw new InvalidHostException();
            }
        }
    }

    @Override
    public boolean setPort(String port) {
        int value;
        try {
            value = Integer.parseInt(port);
            if (value < 0 || value > 65535) {
                return false;
            }
            model.setPort(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void clearScreen() {
        Thread clearScreen = new Thread(new Runnable() {
            @Override
            public void run() {
                view.clearScreen();
            }
        });
        clearScreen.start();
    }

    @Override
    public void stopConnection() {
        Thread stopConnectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                model.stopConnection();
            }
        });
        stopConnectionThread.start();
    }
}
