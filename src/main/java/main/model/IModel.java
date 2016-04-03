package main.model;

/**
 * @author Dmitriy Albot
 */
public interface IModel {
    void addObservers();

    void notifyObservers();

    void startClient();
}
