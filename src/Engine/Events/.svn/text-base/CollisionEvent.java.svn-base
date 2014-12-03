package Engine.Events;

import java.util.EventObject;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 2/3/12
 * Time: 4:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class CollisionEvent extends EventObject {

    private Object other;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public CollisionEvent(Object source, Object other) {
        super(source);
        this.other = other;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }
}
