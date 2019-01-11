package fields;

import interfaces.Displayable;
import models.Box;
import models.Player;

public class DynamicField extends Field {

    public DynamicField() {
        super();
    }

    @Override
    public Displayable getEntity(int row, int col) {
        return super.field[row][col];
    }

    public void setEntity(int row, int col, Displayable value) {
        super.field[row][col] = value;
    }

    @Override
    protected void fill() {
        super.field = new Displayable[super.getRowsCount()][super.getColumnsCount()];

        var index = 0;
        for (int i = 0; i < super.getRowsCount(); i++) {
            for (int j = 0; j < super.getColumnsCount(); j++) {
                if(index >= super.bufferField.length) {
                    break;
                }

                switch (bufferField[index]) {
                    case '$':
                        super.field[i][j] = new Box();
                        index++;
                        break;
                    case '1':
                        super.field[i][j] = new Player();
                        index++;
                        break;
                    case '\r':
                    case '\n':
                        index++;
                        j--;
                        break;
                    default:
                        index++;
                        super.field[i][j] = null;
                        break;
                }
            }
        }
    }

}
