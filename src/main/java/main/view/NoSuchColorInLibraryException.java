package main.view;

/**
 * @author Dmitriy Albot
 */
public class NoSuchColorInLibraryException extends RuntimeException {
    public NoSuchColorInLibraryException(String message) {
        super(message);
    }
}
