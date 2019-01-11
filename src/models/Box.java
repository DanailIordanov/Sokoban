package models;

import interfaces.Displayable;

public class Box implements Displayable {

    @Override
    public char getDisplayChar() {
        return '$';
    }

}
