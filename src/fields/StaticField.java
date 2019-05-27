package fields;

import interfaces.Displayable;
import models.Target;
import models.Wall;

public class StaticField extends Field {

    public StaticField() {
        super();
    }

    @Override
    protected void load() {
        super.initialize(Displayable.class);

        var index = 0;
        for (int i = 0; i < super.getRowsCount(); i++) {
            for (int j = 0; j < super.getColumnsCount(); j++) {
                if(index >= super.getBuffer().getLength()) {
                    break;
                }

                switch (super.getBuffer().getChar(index)) {
                    case '#':
                        super.setEntity(i, j, new Wall());
                        index++;
                        break;
                    case '.':
                        super.setEntity(i, j, new Target());
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
