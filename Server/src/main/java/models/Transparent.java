package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Type;

public class Transparent extends DynamicEntity {

    private static final String FIELD_FILE_PATH = "/transparent";
    private static final String FIELD_FILE_EXTENSION = ".png";

    public Transparent(int row, int col) {
        super(row, col);
    }

    @Override
    public Type[] getCollisionTypes() {
        return new Type[0];
    }

    @Override
    public char getDisplayChar() {
        return 0;
    }

    @Override
    public ImageView getDisplayImage() {
        return super.processImage(new Image(getClass()
                .getResourceAsStream(FIELD_FILE_PATH + FIELD_FILE_EXTENSION)));
    }
}
