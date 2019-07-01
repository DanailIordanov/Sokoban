package models;

import interfaces.Displayable;

public class Wall implements Displayable {

    @Override
    public char getDisplayChar() {
        return '#';
    }

}
