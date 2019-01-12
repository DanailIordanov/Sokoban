package models;

import common.Coordinates;
import interfaces.Movable;

public abstract class DynamicEntity implements Movable {

    private Coordinates coordinates;

    public DynamicEntity(int row, int col) {
        this.coordinates = new Coordinates(row, col);
    }

    @Override
    public Coordinates getLocation() {
        return coordinates;
    }

    @Override
    public abstract char getDisplayChar();

}
