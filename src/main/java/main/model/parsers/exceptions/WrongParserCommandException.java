package main.model.parsers.exceptions;

/**
 * @author Dmitriy Albot
 */
public class WrongParserCommandException extends RuntimeException {
    public WrongParserCommandException(String message) {
        super(message);
    }
}
