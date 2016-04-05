package main.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy Albot
 */
public class Model implements IModel {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 29228;
    private Parser parser;
    private List<GraphicsObserver> graphicsObservers;
    private List<ModelObserver> modelObservers;
    private BufferedReader bufferedReader;
    private final Object lock = new Object();
    private volatile Command currentCommand;
    private volatile boolean isPaused;
    private ServerStatus status;

    public Model() {
        this.status = ServerStatus.SERVER_IS_UNAVAILABLE;
        graphicsObservers = new ArrayList<>();
        modelObservers = new ArrayList<>();
    }

    public Model(Parser parser) {
        this();
        this.parser = parser;
    }

    @Override
    public void addGraphicsObserver(GraphicsObserver graphicsObserver) {
        graphicsObservers.add(graphicsObserver);
    }

    @Override
    public void addModelObserver(ModelObserver modelObserver) {
        modelObservers.add(modelObserver);
    }

    @Override
    public void notifyGraphicsObservers() {
        for (GraphicsObserver graphicsObserver : graphicsObservers) {
            graphicsObserver.updateGraphics();
        }
    }

    @Override
    public void notifyModelObservers() {
        for (ModelObserver modelObserver : modelObservers) {
            modelObserver.updateModelObserver();
        }
    }

    @Override
    public Command getCurrentCommand() {
        return currentCommand;
    }

    @Override
    public void startClient() {
        establishConnection();
        startReceiveingMessage();
    }

    private void establishConnection() {
        try {
            Socket socket = new Socket(HOST, PORT);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            status = ServerStatus.SERVER_IS_AVAILABLE;
            notifyModelObservers();
        } catch (IOException e) {
            status = ServerStatus.SERVER_IS_UNAVAILABLE;
            notifyModelObservers();
        }
    }

    private void startReceiveingMessage() {
        while (true) {
            synchronized (lock) {
                if (isPaused) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            String textCommand = null;
            try {
                textCommand = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                currentCommand = parser.parseCommand(textCommand);
                notifyGraphicsObservers();
                System.out.println(textCommand);
            } catch (WrongParserCommandException e) {
                System.out.println("Incorrect command");
            }
        }
    }

    @Override
    public void pauseConnection() {
        isPaused = true;
    }

    @Override
    public void resumeConnection() {
        synchronized (lock) {
            isPaused = false;
            lock.notifyAll();
        }

    }

    @Override
    public void setParser(Parser parser) {
        this.parser = parser;
    }

    @Override
    public ServerStatus getServerStatus() {
        return status;
    }
}
