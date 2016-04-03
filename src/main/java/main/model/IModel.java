package main.model;

/**
 * @author Dmitriy Albot
 */
public interface IModel {
    void addObserver(Observer observer);

    void notifyObservers();

    Command getCurrentCommand();

    void startClient();

    void pauseConnection();

    void resumeConnection();

    void setParser(Parser parser);
}
