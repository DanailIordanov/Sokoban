package fields;

import interfaces.Displayable;
import interfaces.Movable;
import javafx.scene.layout.GridPane;
import models.Box;
import models.Player;
import models.Transparent;

import java.util.ArrayList;

public class DynamicField extends Field {

    public DynamicField() {
        super();
    }

    public <TEntity extends Movable> ArrayList<TEntity> getEntities(Class<TEntity> type) {
        var entities = new ArrayList<TEntity>();

        for (var row = 0; row < super.getRowsCount(); row++) {
            for (var col = 0; col < super.getColumnsCount(); col++) {
                var entity = super.getEntity(row, col);
                if (entity != null && entity.getClass().equals(type)) {
                    entities.add((TEntity)entity);
                }
            }
        }

        return entities;
    }

    @Override
    public Movable getEntity(int row, int col) {
        var entity = super.getEntity(row, col);
        if (entity instanceof Movable) {
            return (Movable)entity;
        } else {
            return null;
        }
    }

    @Override
    public void setEntity(int row, int col, Displayable entity) {
        if (entity == null || Movable.class.isAssignableFrom(entity.getClass())) {
            super.setEntity(row, col, entity);
            var entityImage = super.getDisplayField()
                    .getChildren().stream()
                    .filter(node -> GridPane.getColumnIndex(node) == col &&
                            GridPane.getRowIndex(node) == row)
                    .findFirst()
                    .orElse(null);
            super.getDisplayField().getChildren().removeAll(entityImage);
            super.getDisplayField().add(entity.getDisplayImage(), col, row);
        } else {
            throw new IllegalArgumentException("A non-movable object cannot be set onto the dynamic field");
        }
    }

    @Override
    protected void load() {
        super.initialize(Movable.class);

        var playerIsMarked = false;
        var index = 0;
        for (int i = 0; i < super.getRowsCount(); i++) {
            for (int j = 0; j < super.getColumnsCount(); j++) {
                if(index >= super.getBuffer().getLength()) {
                    break;
                }
                Displayable entity;
                switch (super.getBuffer().getChar(index)) {
                    case '$':
                        entity = new Box(i, j);
                        super.setEntity(i, j, entity);
                        super.getDisplayField().add(entity.getDisplayImage(), j, i);
                        index++;
                        break;
                    case '1':
                        if(!playerIsMarked) {
                            entity = new Player(i, j, true);
                            super.setEntity(i, j, entity);
                            super.getDisplayField().add(entity.getDisplayImage(), j, i);
                            playerIsMarked = true;
                        } else {
                            entity = new Player(i, j, false);
                            super.setEntity(i, j, entity);
                            super.getDisplayField().add(entity.getDisplayImage(), j, i);
                        }
                        index++;
                        break;
                    case '\r':
                    case '\n':
                        index++;
                        j--;
                        break;
                    default:
                        entity = new Transparent(i, j);
                        super.setEntity(i, j, entity);
                        super.getDisplayField().add(entity.getDisplayImage(), j, i);
                        index++;
                        break;
                }
            }
        }
    }

}
