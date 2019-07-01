package core;

import IO.ConsoleReader;
import fields.DynamicField;
import fields.StaticField;
import handlers.*;

import java.util.Scanner;

public class ServerLauncher {

    public static void main(String[] args) {
        var staticField = new StaticField();
        var dynamicField = new DynamicField();

        var reader = new ConsoleReader(new Scanner(System.in));
        var commandHandler = new CommandHandler(reader);

        var position = new PositionHandler(staticField, dynamicField);
        var validation = new ValidationHandler(staticField, dynamicField);
        var display = new DisplayHandler(staticField, dynamicField);

        var status = new StatusHandler(staticField, dynamicField);

        var gameHandler = new GameHandler(dynamicField, commandHandler, position, validation, display);

        var server = new Server(reader, gameHandler, status, display);
        server.initialize();

        server.run();
    }

}
