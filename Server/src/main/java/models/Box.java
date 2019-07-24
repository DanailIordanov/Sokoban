package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Type;

public class Box extends DynamicEntity {

    private static final String FIELD_FILE_PATH = "/purple_box";
    private static final String FIELD_FILE_EXTENSION = ".png";

    public Box(int row, int col) {
        super(row, col);
    }

    @Override
    public Type[] getCollisionTypes() {
        return new Type[]{ Box.class, Wall.class, Player.class };
    }

    @Override
    public char getDisplayChar() {
        return '$';
    }

    @Override
    public ImageView getDisplayImage() {
        return super.processImage(new Image(getClass()
                .getResourceAsStream(FIELD_FILE_PATH + FIELD_FILE_EXTENSION)));
    }

}
