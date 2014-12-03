package Engine.Events;

import java.util.EventListener;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 2/3/12
 * Time: 4:53 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CollisionListener extends EventListener {

    public void collisionOccurred(CollisionEvent e);

}
