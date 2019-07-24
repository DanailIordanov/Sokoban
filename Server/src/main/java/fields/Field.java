package fields;

import interfaces.Displayable;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Array;

public abstract class Field {

    private Displayable[][] field;
    private BufferField buffer;
    private GridPane displayField;

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

    public GridPane getDisplayField() {
        return this.displayField;
    }

    protected BufferField getBuffer() {
        return this.buffer;
    }

    protected <T extends Displayable> void initialize(Class<T> type) {
        this.field = (T[][])Array.newInstance(type, this.getRowsCount(), this.getColumnsCount());
        this.displayField = new GridPane();
    }

    protected abstract void load();

}
