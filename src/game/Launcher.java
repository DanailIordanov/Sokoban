package game;

import common.Direction;
import fields.DynamicField;
import fields.StaticField;
import managers.FieldManager;
import managers.ValidationManager;
import models.Player;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {
        var sfield = new StaticField();
        var dfield = new DynamicField();
        var manager = new FieldManager(sfield, dfield);
        var validation = new ValidationManager(sfield, dfield);
        var player = dfield.getEntity(Player.class);

        manager.hide();
        System.out.print(manager.show());

        new Scanner(System.in).next();
        manager.hide();
        if (!validation.willCollideIn(Direction.Right, player)) {
            var targetLocation = player.getManipulatedLocation(Direction.Right);

            if (validation.boxShouldBeMovedIn(Direction.Right)) {
                var box = dfield.getEntity(targetLocation.getRow(), targetLocation.getColumn());
                if(!validation.willCollideIn(Direction.Right, box)) {
                    manager.moveIn(Direction.Right, box);
                } else {
                    return;
                }
            }

            manager.moveIn(Direction.Right, player);
        }
        System.out.print(manager.show());

        new Scanner(System.in).next();
        manager.hide();
        if (!validation.willCollideIn(Direction.Down, player)) {
            var targetLocation = player.getManipulatedLocation(Direction.Down);

            if (validation.boxShouldBeMovedIn(Direction.Down)) {
                var box = dfield.getEntity(targetLocation.getRow(), targetLocation.getColumn());
                if(!validation.willCollideIn(Direction.Down, box)) {
                    manager.moveIn(Direction.Down, box);
                } else {
                    manager.moveIn(Direction.Up, player);
                }
            }

            manager.moveIn(Direction.Down, player);
        }
        System.out.print(manager.show());

        new Scanner(System.in).next();
        manager.hide();
        if (!validation.willCollideIn(Direction.Left, player)) {
            var targetLocation = player.getManipulatedLocation(Direction.Left);

            if (validation.boxShouldBeMovedIn(Direction.Left)) {
                var box = dfield.getEntity(targetLocation.getRow(), targetLocation.getColumn());
                if(!validation.willCollideIn(Direction.Left, box)) {
                    manager.moveIn(Direction.Left, box);
                } else {
                    return;
                }
            }

            manager.moveIn(Direction.Left, player);
        }
        System.out.print(manager.show());
    }

}
