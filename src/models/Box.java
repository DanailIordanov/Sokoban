package models;

public class Box extends DynamicEntity {

    public Box(int row, int col) {
        super(row, col);
    }

    @Override
    public char getDisplayChar() {
        return '$';
    }

}
