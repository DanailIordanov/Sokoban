package handlers;

import fields.DynamicField;
import infrastrucutre.ConsoleCleaner;
import models.Player;

public class GameHandler {

    private DynamicField dynamicField;
    private PositionHandler position;
    private ValidationHandler validation;
    private CommandHandler command;
    private DisplayHandler display;

    public GameHandler(DynamicField dynamicField, CommandHandler command, PositionHandler position,
                       ValidationHandler validation, DisplayHandler display) {
        this.dynamicField = dynamicField;
        this.command = command;
        this.position = position;
        this.validation = validation;
        this.display = display;
    }

    public String play(String command, boolean fromServer) {
        Player player = this.dynamicField.getEntities(Player.class).get(0);

        ConsoleCleaner.clear();

        var direction = this.command.parse(command);

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
