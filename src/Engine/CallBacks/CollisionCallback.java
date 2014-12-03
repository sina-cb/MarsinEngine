package Engine.CallBacks;

import Engine.Objects.Object2D;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 2/2/12
 * Time: 10:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CollisionCallback {

    /**
     * This should be called when two objects are collided
     * @param obj1 Object 1 in collision
     * @param obj2 Object 2 in collision
     */
    public void boom(Object2D obj1, Object2D obj2);

}
