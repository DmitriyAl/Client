package main.model;

import java.io.IOException;

/**
 * @author Dmitriy Albot
 */
public class WrongParserCommandException extends RuntimeException {
    public WrongParserCommandException(String message) {
        super(message);
    }
}
