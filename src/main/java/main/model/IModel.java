package main.model;

import java.util.Deque;
import java.util.Queue;

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

    void setParser(Parser parser);

    ServerStatus getServerStatus();
}
