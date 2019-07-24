package models;

import common.Coordinates;
import common.Direction;
import interfaces.Movable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Type;

public abstract class DynamicEntity implements Movable {

    static final int HEIGHT = 60;
    static final int WIDTH = 60;

    private Coordinates coordinates;

    protected DynamicEntity(int row, int col) {
        this.coordinates = new Coordinates(row, col);
    }

    @Override
    public Coordinates getLocation() {
        return coordinates;
    }

    @Override
    public void setLocation(Coordinates value) {
        this.coordinates = value;
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

    @Override
    public abstract ImageView getDisplayImage();

    protected ImageView processImage(Image image) {
        var view = new ImageView(image);
        view.setFitHeight(HEIGHT);
        view.setFitWidth(WIDTH);
        view.setPreserveRatio(true);

        return view;
    }

}
