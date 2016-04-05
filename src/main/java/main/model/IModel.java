package main.model;

/**
 * @author Dmitriy Albot
 */
public interface IModel {
    void addGraphicsObserver(GraphicsObserver graphicsObserver);
    void addModelObserver(ModelObserver modelObserver);

    void notifyGraphicsObservers();

    void notifyModelObservers();

    Command getCurrentCommand();

    void startClient();

    void pauseConnection();

    void resumeConnection();

    void setParser(Parser parser);

    ServerStatus getServerStatus();
}
