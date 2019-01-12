package fields;

import interfaces.Displayable;
import models.Target;
import models.Wall;

public class StaticField extends Field {

    public StaticField() {
        super();
    }

    public Displayable getEntity(int row, int col) {
        return super.field[row][col];
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
                    case '#':
                        super.field[i][j] = new Wall();
                        index++;
                        break;
                    case '.':
                        super.field[i][j] = new Target();
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
