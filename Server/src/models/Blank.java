package models;

import interfaces.Displayable;

public class Blank implements Displayable {

    @Override
    public char getDisplayChar() {
        return ' ';
    }

}
