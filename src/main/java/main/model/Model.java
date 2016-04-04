package main.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    private List<Observer> observers;
    private final Object lock = new Object();
    private volatile Command currentCommand;
    private volatile boolean isStopped;


    public Model() {
        observers = new ArrayList<>();
    }

    public Model(Parser parser) {
        this();
        this.parser = parser;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public Command getCurrentCommand() {
        return currentCommand;
    }

    @Override
    public void startClient() {
        isStopped = false;
        BufferedReader br = null;
        try {
            while (!isStopped) {
                Socket socket = new Socket(HOST, PORT);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String textCommand = br.readLine();
                synchronized (lock) {
                    try {
                        currentCommand = parser.parseCommand(textCommand);
                        notifyObservers();
                        System.out.println(textCommand);
                    } catch (WrongParserCommandException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseConnection() {
        isStopped = true;
    }

    @Override
    public void resumeConnection() {
        isStopped = false;
        startClient();
    }

    @Override
    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
