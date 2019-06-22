package models;

import java.lang.reflect.Type;

public class Player extends DynamicEntity {

    private boolean serverPlayer;

    public Player(int row, int col, boolean serverPlayer) {
        super(row, col);

        this.serverPlayer = serverPlayer;
    }

    @Override
    public Type[] getCollisionTypes() {
        return new Type[]{ Wall.class, Player.class };
    }

    @Override
    public char getDisplayChar() {
        return '1';
    }

    public boolean isServerPlayer() {
        return serverPlayer;
    }
}
