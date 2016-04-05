package main.model;

/**
 * @author Dmitriy Albot
 */
public class WrongMACAddressException extends WrongParserCommandException {
    public WrongMACAddressException(String message) {
        super(message);
    }
}
