package core;

import IO.ConsoleReader;
import IO.ConsoleWriter;
import fields.DynamicField;
import fields.StaticField;
import handlers.*;

import java.util.Scanner;

public class ServerLauncher {

    public static void main(String[] args) {
        var staticField = new StaticField();
        var dynamicField = new DynamicField();

        var position = new PositionHandler(staticField, dynamicField);
        var validation = new ValidationHandler(staticField, dynamicField);
        var display = new DisplayHandler(staticField, dynamicField);

        var reader = new ConsoleReader(new Scanner(System.in));
        var commandHandler = new CommandHandler(reader);
        var writer = new ConsoleWriter();

        var game = new GameHandler(dynamicField, position, validation, display, commandHandler);

        var status = new StatusHandler(staticField, dynamicField);
        var connection = new ConnectionHandler(reader);

        var server = new Server(display, reader, writer, game, status, connection);

        server.initialize();

        server.run();
    }

}
