package interfaces;

import common.Coordinates;
import common.Direction;

import java.lang.reflect.Type;

public interface Movable extends Displayable {

    Coordinates getLocation();

    Coordinates getManipulatedLocation(Direction direction);

    Type[] getCollisionTypes();

}
