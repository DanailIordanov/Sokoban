package handlers;

import common.Direction;
import fields.DynamicField;
import fields.StaticField;
import interfaces.Movable;
import models.Box;
import models.Player;

public class ValidationHandler {

    private StaticField staticField;
    private DynamicField dynamicField;

    public ValidationHandler(StaticField staticField, DynamicField dynamicField) {
        this.staticField = staticField;
        this.dynamicField = dynamicField;
    }

    public boolean willCollideIn(Direction direction, Movable entity) {
        var targetLocation = entity.getManipulatedLocation(direction);
        var targetRow = targetLocation.getRow();
        var targetCol = targetLocation.getColumn();

        for (int i = 0; i < entity.getCollisionTypes().length; i++) {
            var targetDynamicEntity = this.dynamicField.getEntity(targetRow, targetCol);
            var targetStaticEntity = this.staticField.getEntity(targetRow, targetCol);

            if (targetDynamicEntity != null
                    && targetDynamicEntity.getClass().equals(entity.getCollisionTypes()[i])) {
                return true;
            } else if (targetStaticEntity != null
                    && targetStaticEntity.getClass().equals(entity.getCollisionTypes()[i])) {
                return true;
            }
        }

        return false;
    }

    public boolean boxShouldBeMovedIn(Direction direction) {
        var player = this.dynamicField.getEntities(Player.class).get(0);

        var targetEntityLocation = player.getManipulatedLocation(direction);
        var targetEntityRow = targetEntityLocation.getRow();
        var targetEntityCol = targetEntityLocation.getColumn();

        var targetEntity = this.dynamicField.getEntity(targetEntityRow, targetEntityCol);

        if (targetEntity != null) {
            var targetEntityClass = targetEntity.getClass();
            if (targetEntityClass.equals(Box.class)) {
                return true;
            }
        }

        return false;
    }

    public static void checkInput(String input) {
        if (input.length() != 1) {
            throw new IllegalArgumentException("Your input should be just a single letter!");
        } else if (!input.equals("w") && !input.equals("s") && !input.equals("d") && !input.equals("a")) {
            throw new IllegalArgumentException("The key you have pressed is not a valid command!");
        }
    }

}
