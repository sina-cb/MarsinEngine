package Engine.Collision;

import Engine.Events.CollisionEvent;
import Engine.Events.CollisionListener;
import Engine.Objects.CircularObject;
import Engine.Objects.Object2D;
import Engine.Objects.RectangularObject;
import Engine.Utilities.Vector2D;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 12/1/11
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */

@SuppressWarnings("unchecked")
public final class CollisionDetector {

    /**
     * Tests the collision between two objects. If there is a collision it is passed off
     * to the CollisionAdapter class to resolve the collision.
     *
     * @param objA First Object
     * @param objB Second Object
     */
    public void testCollision(Object2D objA, Object2D objB) {

        if (objA.getFixed() && objB.getFixed()) return;

        if (objA instanceof RectangularObject && objB instanceof RectangularObject) {
            testRectVsRect((RectangularObject) objA, (RectangularObject) objB);
        } else if (objA instanceof CircularObject && objB instanceof CircularObject) {
            testCircleVsCircle((CircularObject) objA, (CircularObject) objB);
        } else if (objA instanceof RectangularObject && objB instanceof CircularObject) {
            testRectVsCircle((RectangularObject) objA, (CircularObject) objB);
        } else if (objA instanceof CircularObject && objB instanceof RectangularObject) {
            testRectVsCircle((RectangularObject) objB, (CircularObject) objA);
        }

    }

    /**
     * Tests the collision between two RectangularObjects. If there is a collision it
     * determines its axis and depth, and then passes it off to the CollisionAdapter for handling.
     *
     * @param ra First Rect
     * @param rb Second Rect
     */
    private void testRectVsRect(RectangularObject ra, RectangularObject rb) {

        Vector2D collisionNormal = new Vector2D(0, 0);
        double collisionDepth = Double.POSITIVE_INFINITY;

        for (int i = 0; i < 2; i++) {
            Vector2D axisA = (Vector2D) ra.getAxes().get(i);
            double depthA = testIntervals(ra.getProjection(axisA), rb.getProjection(axisA));
            if (depthA == 0) return;

            Vector2D axisB = (Vector2D) rb.getAxes().get(i);
            double depthB = testIntervals(ra.getProjection(axisB), rb.getProjection(axisB));
            if (depthB == 0) return;

            double absA = Math.abs(depthA);
            double absB = Math.abs(depthB);

            if (absA < Math.abs(collisionDepth) || absB < Math.abs(collisionDepth)) {
                boolean altb = absA < absB;
                collisionNormal = altb ? axisA : axisB;
                collisionDepth = altb ? depthA : depthB;
            }
        }

        CollisionAdapter.resolveCollisionObjectVsObject(ra, rb, collisionNormal, collisionDepth);
        fireCollisionEvent(new CollisionEvent(ra, rb));
    }

    /**
     * Tests the collision between a RectangularObject and a CircularObject.
     * If there is a collision it determines its axis and depth, and then passes it off
     * to the CollisionAdapter for handling.
     *
     * @param ra Rectangular Object
     * @param ca Circular Object
     */
    private void testRectVsCircle(RectangularObject ra, CircularObject ca) {

        Vector2D collisionNormal = new Vector2D(0, 0);
        double collisionDepth = Double.POSITIVE_INFINITY;
        ArrayList depths = new ArrayList(2);

        // First We Check Axes for the Rectangle
        for (int i = 0; i < 2; i++) {
            Vector2D rectAxis = (Vector2D) ra.getAxes().get(i);
            double depth = testIntervals(ra.getProjection(rectAxis), ca.getProjection(rectAxis));
            if (depth == 0) return;

            if (Math.abs(depth) < Math.abs(collisionDepth)) {
                collisionNormal = rectAxis;
                collisionDepth = depth;
            }
            depths.add(i, depth);
        }

        // Checks if the circle's center is in a vertex region
        double r = ca.getRadius();
        if (Math.abs((Double) depths.get(0)) < r && Math.abs((Double) depths.get(1)) < r) {

            Vector2D vertex = closestVertexOnRect(ca.curr, ra);

            collisionNormal = vertex.minus(ca.curr);
            double mag = collisionNormal.magnitude();
            collisionDepth = r - mag;

            if (collisionDepth > 0) {
                collisionNormal.divEquals(mag);
            } else {
                return;
            }
        }

        CollisionAdapter.resolveCollisionObjectVsObject(ra, ca, collisionNormal, collisionDepth);
        fireCollisionEvent(new CollisionEvent(ra, ca));


    }

    /**
     * Tests the collision between two CircularObjects. If there is a collision it
     * determines its axis and depth, and then passes it off to the CollisionAdapter
     * for handling.
     *
     * @param ca Circle A
     * @param cb Circle B
     */
    private void testCircleVsCircle(CircularObject ca, CircularObject cb) {
        double depthX = testIntervals(ca.getIntervalX(), cb.getIntervalX());
        if (depthX == 0) return;

        double depthY = testIntervals(ca.getIntervalY(), cb.getIntervalY());
        if (depthY == 0) return;

        Vector2D collisionNormal = ca.curr.minus(cb.curr);
        double mag = collisionNormal.magnitude();
        double collisionDepth = (ca.getRadius() + cb.getRadius()) - mag;

        if (collisionDepth > 0) {
            collisionNormal.divEquals(mag);
            CollisionAdapter.resolveCollisionObjectVsObject(ca, cb, collisionNormal, collisionDepth);
            fireCollisionEvent(new CollisionEvent(ca, cb));
        }
    }

    /**
     * Returns 0 if Projections do not overlap. Returns smallest depth if they do.
     *
     * @param intervalA Projection of First Object
     * @param intervalB Projection of Second Object
     * @return 0 if we have no overlaps, greater than zero for overlap amount
     */
    private double testIntervals(Interval intervalA, Interval intervalB) {

        if (intervalA.max < intervalB.min) return 0;
        if (intervalB.max < intervalA.min) return 0;

        double lenA = intervalB.max - intervalA.min;
        double lenB = intervalB.min - intervalA.max;

        return (Math.abs(lenA) < Math.abs(lenB)) ? lenA : lenB;
    }

    /**
     * Returns the location of the closest vertex on r to point p
     *
     * @param p The point
     * @param r The Rectangle
     * @return Closest Vertex on the Rectangle
     */
    private Vector2D closestVertexOnRect(Vector2D p, RectangularObject r) {

        Vector2D d = p.minus(r.curr);
        Vector2D q = new Vector2D(r.curr.x, r.curr.y);

        for (int i = 0; i < 2; i++) {
            double dist = d.dot((Vector2D) r.getAxes().get(i));

            if (dist >= 0) dist = (Double) r.getExtents().get(i);
            else if (dist < 0) dist = -(Double) r.getExtents().get(i);

            q.plusEquals(((Vector2D) r.getAxes().get(i)).mult(dist));
        }
        return q;
    }

    //////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////Event Listening Part///////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////

    // Create the listener list
    protected javax.swing.event.EventListenerList listenerList =
            new javax.swing.event.EventListenerList();

    // This methods allows classes to register for MyEvents
    public void addCollisionEventListener(CollisionListener listener) {
        listenerList.add(CollisionListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeMyEventListener(CollisionListener listener) {
        listenerList.remove(CollisionListener.class, listener);
    }

    // This private class is used to fire MyEvents
    void fireCollisionEvent(CollisionEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==CollisionListener.class) {
                ((CollisionListener)listeners[i+1]).collisionOccurred(evt);
            }
        }
    }

}
