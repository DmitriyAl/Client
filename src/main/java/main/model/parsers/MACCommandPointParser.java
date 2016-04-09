package main.model.parsers;

import main.model.*;
import main.model.parsers.exceptions.WrongMACAddressException;
import main.model.parsers.exceptions.WrongParserCommandException;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import java.util.StringTokenizer;

/**
 * @author Dmitriy Albot
 */
public class MACCommandPointParser implements Parser {
    private static Logger log = Logger.getLogger(MACCommandPointParser.class);

    @Override
    public Command parseCommand(String command) throws WrongParserCommandException {
        StringTokenizer parser = new StringTokenizer(command, ";");
        if (parser.countTokens() != 5) {
            throw new WrongParserCommandException("Wrong command format");
        }
        String mac = parser.nextToken();
        analyzeMAC(mac);
        CommandType commandType = determineCommandType(parser.nextToken());
        try {
            float x = Float.parseFloat(parser.nextToken());
            float y = Float.parseFloat(parser.nextToken());
            int colour = Integer.parseInt(parser.nextToken());
            Point point = new Point(x, y, colour);
            return new Command(mac, commandType, point);
        } catch (NumberFormatException e) {
            throw new WrongParserCommandException("Wrong coordinates values");
        } finally {
            log.trace(command);
        }
    }

    private CommandType determineCommandType(String command) {
        switch (command) {
            case "start":
                return CommandType.START;
            case "move":
                return CommandType.MOVE;
            default:
                throw new WrongParserCommandException("Parser can\'t recognize this command");
        }
    }

    private void analyzeMAC(String mac) throws WrongMACAddressException {
        StringTokenizer parser = new StringTokenizer(mac, ":");
        if (parser.countTokens() != 6) {
            throw new WrongMACAddressException("Wrong MAC address");
        }
        for (int i = 0; i < parser.countTokens(); i++) {
            String currentNumber = parser.nextToken();
            if (currentNumber.length() != 2) {
                throw new WrongMACAddressException("Wrong MAC address");
            }
            try {
                Integer.parseInt(currentNumber, 16);
            } catch (NumberFormatException e) {
                throw new WrongMACAddressException("Wrong MAC address");
            }
        }
    }
}
