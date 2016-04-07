package main.view.painters.exceptions;

/**
 * @author Dmitriy Albot
 */
public class NoSuchPainterException extends RuntimeException {
    public NoSuchPainterException(String message) {
        super(message);
    }
}
