package interfaces;

import common.Coordinates;
import common.Direction;

import java.lang.reflect.Type;

public interface Movable extends Displayable {

    Coordinates getLocation();

    void setLocation(Coordinates location);

    Coordinates getManipulatedLocation(Direction direction);

    Type[] getCollisionTypes();

}
