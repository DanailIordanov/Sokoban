package models;

import java.lang.reflect.Type;

public class Box extends DynamicEntity {

    public Box(int row, int col) {
        super(row, col);
    }

    @Override
    public Type[] getCollisionTypes() {
        return new Type[]{ Box.class, Wall.class };
    }

    @Override
    public char getDisplayChar() {
        return '$';
    }

}
