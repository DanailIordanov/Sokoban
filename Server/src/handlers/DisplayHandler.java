package handlers;

import fields.DynamicField;
import fields.StaticField;

public class DisplayHandler {

    private StaticField staticField;
    private DynamicField dynamicField;

    public DisplayHandler(StaticField staticField, DynamicField dynamicField) {
        this.staticField = staticField;
        this.dynamicField = dynamicField;
    }

    public String show() {
        var sb = new StringBuilder();

        for (int i = 0; i < this.staticField.getRowsCount(); i++) {
            for (int j = 0; j < this.staticField.getColumnsCount(); j++) {
                if (this.dynamicField.getEntity(i, j) != null) {
                    sb.append(this.dynamicField.getEntity(i, j).getDisplayChar());
                } else {
                    sb.append(staticField.getEntity(i, j).getDisplayChar());
                }
            }
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }
}
