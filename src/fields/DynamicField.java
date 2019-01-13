package fields;

import interfaces.Displayable;
import interfaces.Movable;
import models.Box;
import models.Player;

public class DynamicField extends Field {

    public DynamicField() {
        super();
    }

    public Movable getEntity(int row, int col) {
        var entity = super.field[row][col];
        if (entity instanceof Movable) {
            return (Movable)entity;
        } else {
            return null;
        }
    }

    public <TEntity extends Movable> TEntity getEntity(Class<TEntity> type) {
        for (Displayable[] row : super.field) {
            for (Displayable entity : row) {
                if (entity != null && entity.getClass().equals(type)) {
                    return (TEntity)entity;
                }
            }
        }
        return null;
    }

    public void setEntity(int row, int col, Movable value) {
        super.field[row][col] = value;
    }

    @Override
    protected void fill() {
        this.field = new Movable[super.getRowsCount()][super.getColumnsCount()];

        var index = 0;
        for (int i = 0; i < super.getRowsCount(); i++) {
            for (int j = 0; j < super.getColumnsCount(); j++) {
                if(index >= super.bufferField.length) {
                    break;
                }

                switch (bufferField[index]) {
                    case '$':
                        super.field[i][j] = new Box(i, j);
                        index++;
                        break;
                    case '1':
                        super.field[i][j] = new Player(i, j);
                        index++;
                        break;
                    case '\r':
                    case '\n':
                        index++;
                        j--;
                        break;
                    default:
                        index++;
                        super.field[i][j] = null;
                        break;
                }
            }
        }
    }

}
