package main.model;

/**
 * @author Dmitriy Albot
 */
public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
