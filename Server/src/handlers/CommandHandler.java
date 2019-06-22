package handlers;

import IO.ConsoleReader;
import common.Direction;

public class CommandHandler {

    private ConsoleReader reader;

    public CommandHandler(ConsoleReader reader) {
        this.reader = reader;
    }

    public Direction parse(String command) {
        switch (command.toLowerCase()) {
            case "w": return Direction.Up;
            case "s": return Direction.Down;
            case "a": return Direction.Left;
            case "d": return Direction.Right;
            default: return null;
        }
    }

}
