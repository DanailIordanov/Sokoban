package game;

import common.Direction;
import fields.DynamicField;
import managers.FieldManager;
import managers.GameManager;
import managers.ValidationManager;
import models.Player;

import java.util.Scanner;

public class Engine {

    private DynamicField dynamicField;
    private FieldManager field;
    private ValidationManager validation;
    private GameManager game;
    private Scanner scanner;

    public Engine(DynamicField dynamicField,
                  FieldManager field, ValidationManager validation,
                  GameManager game, Scanner scanner) {
        this.dynamicField = dynamicField;
        this.field = field;
        this.validation = validation;
        this.game = game;
        this.scanner = scanner;
    }

    public void run() {
        var player = dynamicField.getEntities(Player.class).get(0);

        while (true) {
            field.hide();

            System.out.print(field.show());

            if(game.isWon()) {
                break;
            }

            var direction = this.parseDirection();

            if (!validation.willCollideIn(direction, player)) {
                var targetLocation = player.getManipulatedLocation(direction);

                if (validation.boxShouldBeMovedIn(direction)) {
                    var box = dynamicField.getEntity(targetLocation.getRow(), targetLocation.getColumn());
                    if(!validation.willCollideIn(direction, box)) {
                        field.moveIn(direction, box);
                    } else {
                        continue;
                    }
                }

                field.moveIn(direction, player);
            }
        }

        System.out.println("Congratulations! You have won the game!");
    }

    private Direction parseDirection() {
        String command = "";

        var commandIsValid = false;
        while (!commandIsValid) {
            try {
                command = this.scanner.next();
                this.validation.checkInput(command);
                commandIsValid = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        switch (command) {
            case "w": return Direction.Up;
            case "s": return Direction.Down;
            case "d": return Direction.Right;
            case "a": return Direction.Left;
        }

        return null;
    }

}
