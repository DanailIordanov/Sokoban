package fields;

import interfaces.Displayable;
import models.Blank;
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
                Displayable entity;
                switch (super.getBuffer().getChar(index)) {
                    case '#':
                        entity = new Wall();
                        super.setEntity(i, j, entity);
                        super.getDisplayField().add(entity.getDisplayImage(), j, i);
                        index++;
                        break;
                    case '.':
                        entity = new Target();
                        super.setEntity(i, j, entity);
                        super.getDisplayField().add(entity.getDisplayImage(), j, i);
                        index++;
                        break;
                    case '\r':
                    case '\n':
                        index++;
                        j--;
                        break;
                    default:
                        entity = new Blank();
                        super.setEntity(i, j, entity);
                        super.getDisplayField().add(entity.getDisplayImage(), j, i);
                        index++;
                        break;
                }
            }
        }
    }

}
