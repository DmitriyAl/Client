package main.model;

import main.model.parsers.Parser;
import main.model.parsers.exceptions.WrongParserCommandException;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dmitriy Albot
 */
public class Model implements IModel {
    private Parser parser;
    private List<GraphicsObserver> graphicsObservers;
    private List<ModelObserver> modelObservers;
    private Deque<Command> commandPool;
    private String host;
    private int port;
    private BufferedReader bufferedReader;
    private Socket socket;
    private ServerStatus status;
    private volatile boolean isPaused;
    private volatile boolean isStopped;
    private final Object lock = new Object();
    private static Logger log = Logger.getLogger(Model.class);

    public Model() {
        this.status = ServerStatus.SERVER_IS_UNAVAILABLE;
        graphicsObservers = new ArrayList<>();
        modelObservers = new ArrayList<>();
        commandPool = new LinkedList<>();
    }

    public Model(Parser parser) {
        this();
        this.parser = parser;
    }

    @Override
    public void setParser(Parser parser) {
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
        isStopped = false;
        boolean isConnectedToServer = establishConnection();
        if (isConnectedToServer) {
            startReceiveingMessage();
        }
    }

    private boolean establishConnection() {
        try {
            socket = new Socket(host, port);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            status = ServerStatus.SERVER_IS_AVAILABLE;
            notifyModelObservers();
            log.info("Connection to server " + socket.getRemoteSocketAddress() + " is established");
            return true;
        } catch (IOException e) {
            status = ServerStatus.SERVER_IS_UNAVAILABLE;
            notifyModelObservers();
            log.info("Server " + host + ":" + port + " is unavailable");
            return false;
        }
    }

    private void startReceiveingMessage() {
        try {
            while (!isStopped) {
                synchronized (lock) {
                    if (isPaused) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            log.warn("Connection to server is not paused", e);
                        }
                    }
                }
                String textCommand;
                try {
                    textCommand = bufferedReader.readLine();
                } catch (IOException e) {
                    status = ServerStatus.SERVER_IS_UNAVAILABLE;
                    notifyModelObservers();
                    break;
                }
                try {
                    if (!textCommand.isEmpty()) {
                        Command currentCommand = parser.parseCommand(textCommand);
                        commandPool.add(currentCommand);
                        notifyGraphicsObservers();
                    }
                } catch (WrongParserCommandException e) {
                    log.info("Incorrect command from server");
                }
            }
        } finally {
            try {
                bufferedReader.close();
                socket.close();
            } catch (IOException e) {
                log.error("Failure to close connection to server", e);
            }
            status = ServerStatus.SERVER_IS_UNAVAILABLE;
            notifyModelObservers();
        }
    }

    @Override
    public void pauseConnection() {
        isPaused = true;
        log.info("Connection to server is paused");
    }

    @Override
    public void resumeConnection() {
        synchronized (lock) {
            isPaused = false;
            lock.notifyAll();
            log.info("Connection to server is resumed");
        }
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public ServerStatus getServerStatus() {
        return status;
    }

    @Override
    public void stopConnection() {
        try {
            isStopped = true;
            socket.getInputStream().close();
            log.info("Connection to server " + socket.getRemoteSocketAddress().toString() + " is stopped");
        } catch (IOException e) {
            log.error("Failure to stop the connection to server");
        }
    }

}
