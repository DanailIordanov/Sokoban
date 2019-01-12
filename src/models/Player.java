package models;

public class Player extends DynamicEntity {

    public Player(int row, int col) {
        super(row, col);
    }

    @Override
    public char getDisplayChar() {
        return '1';
    }

}
