package main.model;

import main.model.parsers.Parser;

import java.util.Deque;

/**
 * @author Dmitriy Albot
 */
public interface IModel {
    void addGraphicsObserver(GraphicsObserver graphicsObserver);
    void addModelObserver(ModelObserver modelObserver);
    void notifyGraphicsObservers();
    void notifyModelObservers();
    Deque<Command> getCommandPool();
    void startClient();
    void pauseConnection();
    void resumeConnection();
    void setHost(String host);
    void setPort(int port);
    void setParser(Parser parser);
    ServerStatus getServerStatus();
    void stopConnection();
}
