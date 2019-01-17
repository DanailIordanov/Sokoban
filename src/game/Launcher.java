package game;

import fields.DynamicField;
import fields.StaticField;
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

        var engine = new Engine(dynamicField, field, validation, game, scanner);
        engine.run();

        scanner.close();
    }

}
