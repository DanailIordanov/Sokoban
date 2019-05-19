package fields;

import interfaces.Displayable;
import interfaces.Movable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Field {

    private Displayable[][] field;
    private BufferField buffer;

    protected Field() {
        this.buffer = new BufferField();

        this.fill();
    }

    public Displayable getEntity(int row, int col) {
        return this.field[row][col];
    }

    public void setEntity(int row, int col, Displayable value) {
        this.field[row][col] = value;
    }

    public <TEntity extends Movable> ArrayList<TEntity> getEntities(Class<TEntity> type) {
        var entities = new ArrayList<TEntity>();

        for (Displayable[] row : this.field) {
            for (Displayable entity : row) {
                if (entity != null && entity.getClass().equals(type)) {
                    entities.add((TEntity)entity);
                }
            }
        }

        return entities;
    }

    public int getRowsCount() {
        return this.buffer.getLength() / (this.getColumnsCount() + System.lineSeparator().length());
    }

    public int getColumnsCount() {
        return this.buffer.toString().indexOf(System.lineSeparator());
    }

    protected BufferField getBuffer() {
        return this.buffer;
    }

    protected <T extends Displayable> void initialize(Class<T> type) {
        this.field = (T[][])Array.newInstance(type, this.getRowsCount(), this.getColumnsCount());
    }

    protected abstract void fill();

}
