package managers;

import fields.DynamicField;
import fields.StaticField;
import models.Box;
import models.Target;

public class GameManager {

    private StaticField staticField;
    private DynamicField dynamicField;

    public GameManager(StaticField staticField, DynamicField dynamicField) {
        this.staticField = staticField;
        this.dynamicField = dynamicField;
    }

    public boolean isWon() {
        var boxes = this.dynamicField.getEntities(Box.class);
        var boxesOnTargetsCount = 0;

        for (Box box : boxes) {
            var boxRow = box.getLocation().getRow();
            var boxCol = box.getLocation().getColumn();

            var targetEntity = staticField.getEntity(boxRow, boxCol);

            if (targetEntity instanceof Target) {
                boxesOnTargetsCount++;
            }
        }

        return boxesOnTargetsCount == boxes.size();
    }

}
