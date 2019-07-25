package handlers;

import common.Direction;
import fields.DynamicField;
import fields.StaticField;
import interfaces.Movable;
import models.Transparent;

public class PositionHandler {

    private DynamicField dynamicField;
    private StaticField staticField;

    public PositionHandler(StaticField staticField, DynamicField dynamicField) {
        this.staticField = staticField;
        this.dynamicField = dynamicField;
    }

    public void moveIn(Direction direction, Movable entity) {
        var entityRow = entity.getLocation().getRow();
        var entityCol = entity.getLocation().getColumn();
        this.dynamicField.setEntity(entityRow, entityCol, new Transparent(entityRow, entityCol));

        var targetLocation = entity.getManipulatedLocation(direction);
        entity.setLocation(targetLocation);
        this.dynamicField.setEntity(targetLocation.getRow(), targetLocation.getColumn(), entity);
    }

}
