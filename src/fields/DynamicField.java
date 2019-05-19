package fields;

import interfaces.Movable;
import models.Box;
import models.Player;

public class DynamicField extends Field {

    public DynamicField() {
        super();
    }

    @Override
    public Movable getEntity(int row, int col) {
        var entity = super.getEntity(row, col);
        if (entity instanceof Movable) {
            return (Movable)entity;
        } else {
            return null;
        }
    }

    @Override
    protected void fill() {
        super.initialize(Movable.class);

        var index = 0;
        for (int i = 0; i < super.getRowsCount(); i++) {
            for (int j = 0; j < super.getColumnsCount(); j++) {
                if(index >= super.getBuffer().getLength()) {
                    break;
                }

                switch (super.getBuffer().getChar(index)) {
                    case '$':
                        super.setEntity(i, j, new Box(i, j));
                        index++;
                        break;
                    case '1':
                        super.setEntity(i, j, new Player(i, j));
                        index++;
                        break;
                    case '\r':
                    case '\n':
                        index++;
                        j--;
                        break;
                    default:
                        index++;
                        super.setEntity(i, j, null);
                        break;
                }
            }
        }
    }

}
