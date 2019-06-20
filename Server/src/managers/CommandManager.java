package managers;

import IO.Reader;
import common.Direction;

public class CommandManager {

    private Reader reader;

    public CommandManager(Reader reader) {
        this.reader = reader;
    }

    public Direction parse() {
        String command = null;

        var commandIsValid = false;
        while (!commandIsValid) {
            try {
                command = this.reader.getInput();
                ValidationManager.checkInput(command);
                commandIsValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        switch (command.toLowerCase()) {
            case "w": return Direction.Up;
            case "s": return Direction.Down;
            case "a": return Direction.Left;
            case "d": return Direction.Right;
            default: return null;
        }
    }

}
