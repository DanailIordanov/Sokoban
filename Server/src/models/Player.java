package models;

import java.lang.reflect.Type;

public class Player extends DynamicEntity {

    public Player(int row, int col) {
        super(row, col);
    }

    @Override
    public Type[] getCollisionTypes() {
        return new Type[]{ Wall.class };
    }

    @Override
    public char getDisplayChar() {
        return '1';
    }

}
