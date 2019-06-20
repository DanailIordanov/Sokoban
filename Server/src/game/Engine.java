package game;

import fields.DynamicField;
import managers.CommandManager;
import managers.FieldManager;
import managers.GameManager;
import managers.ValidationManager;
import models.Player;

public class Engine {

    private DynamicField dynamicField;
    private FieldManager field;
    private ValidationManager validation;
    private GameManager game;
    private CommandManager commandManager;

    public Engine(DynamicField dynamicField,
                  FieldManager field, ValidationManager validation,
                  GameManager game, CommandManager commandManager) {
        this.dynamicField = dynamicField;
        this.field = field;
        this.validation = validation;
        this.game = game;
        this.commandManager = commandManager;
    }

    public void run() {
        var player = dynamicField.getEntities(Player.class).get(0);

        while (true) {
            field.hide();

            System.out.print(field.show());

            if(game.isWon()) {
                break;
            }

            var direction = this.commandManager.parse();

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

}