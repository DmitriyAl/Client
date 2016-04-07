package main.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

/**
 * @author Dmitriy Albot
 */
public class Model implements IModel {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 29228;
    private Parser parser;
    private List<GraphicsObserver> graphicsObservers;
    private List<ModelObserver> modelObservers;
    private Deque<Command> commandPool;
    private BufferedReader bufferedReader;
    private Socket socket;
    private final Object lock = new Object();
    private volatile boolean isPaused;
    private ServerStatus status;
    private int maxAttenptToConnect;

    public Model() {
        this.status = ServerStatus.SERVER_IS_UNAVAILABLE;
        graphicsObservers = new ArrayList<>();
        modelObservers = new ArrayList<>();
        commandPool = new LinkedList<>();
        maxAttenptToConnect = 5;
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
    public Deque<Command> getCommandPool() {
        return commandPool;
    }

    @Override
    public void startClient() {
        boolean isConnectedToServer = establishConnection();
        if (isConnectedToServer) {
            startReceiveingMessage();
        }
    }

    private boolean establishConnection() {
        try {
            socket = new Socket(HOST, PORT);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            status = ServerStatus.SERVER_IS_AVAILABLE;
            notifyModelObservers();
            return true;
        } catch (IOException e) {
            status = ServerStatus.SERVER_IS_UNAVAILABLE;
            notifyModelObservers();
            return false;
        }
    }

    private void startReceiveingMessage() {
        int attempt = 0;
        while (attempt < maxAttenptToConnect) {
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
                textCommand = bufferedReader.readLine(); //todo waiting time
            } catch (IOException e) {
                System.out.println("Connection reset"); //todo delete
                attempt++;
                continue;
//                e.printStackTrace();
            }
            try {
                Command currentCommand = parser.parseCommand(textCommand);
                commandPool.add(currentCommand);
                notifyGraphicsObservers();
                System.out.println(textCommand);
            } catch (WrongParserCommandException e) {
                attempt++;
                System.out.println("Incorrect command"); //todo delete
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        status = ServerStatus.SERVER_IS_UNAVAILABLE;
        notifyModelObservers();
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
