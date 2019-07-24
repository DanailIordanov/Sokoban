package models;

import interfaces.Displayable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Blank implements Displayable {

    private static final String FIELD_FILE_PATH = "/purple_blank2";
    private static final String FIELD_FILE_EXTENSION = ".png";

    @Override
    public char getDisplayChar() {
        return ' ';
    }

    @Override
    public ImageView getDisplayImage() {
        var image = new Image(getClass()
                .getResourceAsStream(FIELD_FILE_PATH + FIELD_FILE_EXTENSION));
        var view = new ImageView(image);
        view.setFitHeight(DynamicEntity.HEIGHT);
        view.setFitWidth(DynamicEntity.WIDTH);

        return view;
    }

}
