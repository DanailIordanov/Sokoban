package fields;

import interfaces.Displayable;
import interfaces.Movable;
import models.Box;
import models.Player;

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

                switch (super.getBuffer().getChar(index)) {
                    case '$':
                        super.setEntity(i, j, new Box(i, j));
                        index++;
                        break;
                    case '1':
                        if(!playerIsMarked) {
                            super.setEntity(i, j, new Player(i, j, true));
                            playerIsMarked = true;
                        } else {
                            super.setEntity(i, j, new Player(i, j, false));
                        }
                        index++;
                        break;
                    case '\r':
                    case '\n':
                        index++;
                        j--;
                        break;
                    default:
                        index++;
                        super.setEntity(i, j, null);
                        break;
                }
            }
        }
    }

}
