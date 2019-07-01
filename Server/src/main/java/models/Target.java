package models;

import interfaces.Displayable;

public class Target implements Displayable {

    @Override
    public char getDisplayChar() {
        return '.';
    }

}
