package main.model;

import java.util.StringTokenizer;

/**
 * @author Dmitriy Albot
 */
public class MACCommandPointParser implements Parser {
    @Override
    public Command parseCommand(String command) { //todo
        StringTokenizer parser = new StringTokenizer(command, ";");
        try {
            String mac = parser.nextToken();
            analyzeMAC(mac);
            CommandType commandType = determineCommandType(parser.nextToken());
            Point point = new Point(new Float(parser.nextToken()), new Float(parser.nextToken()), new Integer(parser.nextToken()));
            return new Command(mac, commandType, point);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new WrongParserCommandException("Can\'t parse this command");
    }

    private CommandType determineCommandType(String command) {
        switch (command) {
            case "start":
                return CommandType.START;
            case "move":
                return CommandType.MOVE;
            default:
                throw new NoSuchCommandException("Parser can\'t recognize this command");
        }
    }

    private void analyzeMAC(String mac) {

    }
}
