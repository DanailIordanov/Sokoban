package game;

import IO.ConsoleReader;
import fields.DynamicField;
import fields.StaticField;
import managers.CommandManager;
import managers.FieldManager;
import managers.GameManager;
import managers.ValidationManager;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {
        var staticField = new StaticField();
        var dynamicField = new DynamicField();
        var field = new FieldManager(staticField, dynamicField);
        var validation = new ValidationManager(staticField, dynamicField);
        var game = new GameManager(staticField, dynamicField);

        var scanner = new Scanner(System.in);
        var reader = new ConsoleReader(scanner);
        var commandManager = new CommandManager(reader);

        var engine = new Engine(dynamicField, field, validation, game, commandManager);
        engine.run();

        scanner.close();
    }

}
