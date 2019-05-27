package managers;

import common.Direction;
import fields.DynamicField;
import fields.StaticField;
import interfaces.Movable;

import java.io.IOException;

public class FieldManager {

    private DynamicField dynamicField;
    private StaticField staticField;

    public FieldManager(StaticField staticField, DynamicField dynamicField) {
        this.staticField = staticField;
        this.dynamicField = dynamicField;
    }

    public void moveIn(Direction direction, Movable entity) {
        var entityRow = entity.getLocation().getRow();
        var entityCol = entity.getLocation().getColumn();
        this.dynamicField.setEntity(entityRow, entityCol, null);

        var targetLocation = entity.getManipulatedLocation(direction);
        entity.setLocation(targetLocation);
        this.dynamicField.setEntity(targetLocation.getRow(), targetLocation.getColumn(), entity);
    }

    public String show() {
        var sb = new StringBuilder();

        for (int i = 0; i < this.staticField.getRowsCount(); i++) {
            for (int j = 0; j < this.staticField.getColumnsCount(); j++) {
                if (dynamicField.getEntity(i, j) != null) {
                    sb.append(dynamicField.getEntity(i, j).getDisplayChar());
                } else {
                    sb.append(staticField.getEntity(i, j).getDisplayChar());
                }
            }
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }

    public void hide() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
