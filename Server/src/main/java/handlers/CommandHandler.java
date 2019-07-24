package handlers;

import IO.contracts.UserReader;
import common.Direction;
import javafx.scene.input.KeyEvent;

public class CommandHandler {

    private UserReader reader;

    public CommandHandler(UserReader reader) {
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

    public Direction parseKey(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
            case W:
                return Direction.Up;
            case DOWN:
            case S:
                return Direction.Down;
            case RIGHT:
            case D:
                return Direction.Right;
            case LEFT:
            case A:
                return Direction.Left;
            case ESCAPE:
            case Q:
                System.exit(0);
            default: return null;
        }
    }
}
