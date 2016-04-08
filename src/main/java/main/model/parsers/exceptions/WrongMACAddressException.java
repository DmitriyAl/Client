package main.model.parsers.exceptions;

/**
 * @author Dmitriy Albot
 */
public class WrongMACAddressException extends WrongParserCommandException {
    public WrongMACAddressException(String message) {
        super(message);
    }
}
