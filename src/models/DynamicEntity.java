package models;

import common.Coordinates;
import common.Direction;
import interfaces.Movable;

import java.lang.reflect.Type;

public abstract class DynamicEntity implements Movable {

    private Coordinates coordinates;

    protected DynamicEntity(int row, int col) {
        this.coordinates = new Coordinates(row, col);
    }

    @Override
    public Coordinates getLocation() {
        return coordinates;
    }

    @Override
    public Coordinates getManipulatedLocation(Direction direction) {
        var entityRow = this.getLocation().getRow();
        var entityCol = this.getLocation().getColumn();

        switch (direction) {
            case Up: entityRow--; break;
            case Down: entityRow++; break;
            case Right: entityCol++; break;
            case Left: entityCol--; break;
        }

        return new Coordinates(entityRow, entityCol);
    }

    @Override
    public abstract Type[] getCollisionTypes();

    @Override
    public abstract char getDisplayChar();

}
