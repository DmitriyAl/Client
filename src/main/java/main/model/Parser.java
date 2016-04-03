package main.model;

/**
 * @author Dmitriy Albot
 */
public interface Parser {
    Command parseCommand(String command);
}
