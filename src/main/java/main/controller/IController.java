package main.controller;

/**
 * @author Dmitriy Albot
 */
public interface IController {
    void startConnection();
    void pauseConnection();
    void resumeConnection();

    boolean setHost(String text);

    boolean setPort(String text);

    void clearScreen();

    void stopConnection();
}
