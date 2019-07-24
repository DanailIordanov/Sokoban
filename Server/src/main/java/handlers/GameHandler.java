package handlers;

import fields.DynamicField;
import javafx.scene.input.KeyEvent;
import models.Player;

public class GameHandler {

    private DynamicField dynamicField;
    private PositionHandler position;
    private ValidationHandler validation;
    private CommandHandler command;
    private DisplayHandler display;

    public GameHandler(DynamicField dynamicField, PositionHandler position,
                       ValidationHandler validation, DisplayHandler display,
                       CommandHandler command) {
        this.dynamicField = dynamicField;
        this.command = command;
        this.position = position;
        this.validation = validation;
        this.display = display;
    }

    public String play(KeyEvent command, boolean fromServer) {
        Player player = dynamicField
                .getEntities(Player.class).stream()
                .filter(p -> p.isServerPlayer() == fromServer)
                .findFirst()
                .orElse(null);

        var direction = this.command.parseKey(command);

        if (!validation.willCollideIn(direction, player)) {
            var targetLocation = player.getManipulatedLocation(direction);

            if (validation.boxShouldBeMovedIn(direction, fromServer)) {
                var box = dynamicField.getEntity(targetLocation.getRow(), targetLocation.getColumn());
                if(!validation.willCollideIn(direction, box)) {
                    position.moveIn(direction, box);
                } else {
                    return display.show();
                }
            }

            position.moveIn(direction, player);
        }

        return display.show();
    }

}
