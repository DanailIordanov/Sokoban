package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Type;

public class Player extends DynamicEntity {

    private static final String FIELD_FILE_PATH = "/purple_isometric";
    private static final String FIELD_FILE_EXTENSION = ".png";
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

    @Override
    public ImageView getDisplayImage() {
        return super.processImage(new Image(getClass()
                .getResourceAsStream(FIELD_FILE_PATH + FIELD_FILE_EXTENSION)));
    }

    public boolean isServerPlayer() {
        return serverPlayer;
    }
}
