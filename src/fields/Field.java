package fields;

import interfaces.Displayable;

import java.lang.reflect.Array;

public abstract class Field {

    private Displayable[][] field;
    private BufferField buffer;

    protected Field() {
        this.buffer = new BufferField();

        this.load();
    }

    public Displayable getEntity(int row, int col) {
        return this.field[row][col];
    }

    public void setEntity(int row, int col, Displayable value) {
        this.field[row][col] = value;
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

    protected abstract void load();

}
