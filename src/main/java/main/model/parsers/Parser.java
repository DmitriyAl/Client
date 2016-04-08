package main.model.parsers;

import main.model.Command;
import main.model.parsers.exceptions.WrongParserCommandException;

/**
 * @author Dmitriy Albot
 */
public interface Parser {
    Command parseCommand(String command) throws WrongParserCommandException;
}
