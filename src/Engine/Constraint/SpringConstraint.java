package Engine.Constraint;

import Engine.Objects.Object2D;
import Engine.Objects.RectangularObject;
import Engine.Objects.SpringConstraintObject;
import Engine.Utilities.Vector2D;

import java.awt.geom.Line2D;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 1/17/12
 * Time: 1:13 AM
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unchecked")
public class SpringConstraint extends AbstractConstraint {

    private Object2D p1;
    private Object2D p2;

    private double restLen;
    private Vector2D delta;
    private double deltaLength;
    private double collisionRectWidth;
    private double collisionRectScale;
    private boolean collidable;
    private SpringConstraintObject collisionRect;

    /**
     * @param p1        The first object this constraint is connected to.
     * @param p2        The second object this constraint is connected to.
     * @param stiffness The strength of the spring. Valid values are between 0 and 1. Lower values
     *                  result in softer springs. Higher values result in stiffer, stronger springs.
     */
    // TG TODO DEFAULTS
    public SpringConstraint(
            Object2D p1,
            Object2D p2,
            double stiffness) {

        super(stiffness);
        this.p1 = p1;
        this.p2 = p2;
        checkObjectssLocation();

        collisionRectWidth = 1;
        collisionRectScale = 1;
        collidable = false;

        delta = p1.curr.minus(p2.curr);
        deltaLength = p1.curr.distance(p2.curr);
        restLen = deltaLength;
    }

    /**
     * The rotational value created by the positions of the two objects attached to this
     * SpringConstraint. You can use this property to in your own painting methods, along with the
     * center property.
     *
     * @return Rotation
     */
    public double getRotation() {
        return Math.atan2(delta.y, delta.x);
    }

    /**
     * The center position created by the relative positions of the two objects attached to this
     * SpringConstraint. You can use this property to in your own painting methods, along with the
     * rotation property.
     *
     * @return A Vector2D representing the center of this SpringConstraint
     */
    public Vector2D getCenter() {
        return (p1.curr.plus(p2.curr)).divEquals(2);
    }

    /**
     * If the <code>collidable</code> property is true, you can set the scale of the collidible area
     * between the two attached objects. Valid values are from 0 to 1. If you set the value to 1, then
     * the collision area will extend all the way to the two attached objects. Setting the value lower
     * will result in an collision area that spans a percentage of that distance.
     *
     * @return Collision Rectangle Scale
     */
    public double getCollisionRectScale() {
        return collisionRectScale;
    }

    public void setCollisionRectScale(double scale) {
        collisionRectScale = scale;
    }

    /**
     * If the <code>collidable</code> property is true, you can set the width of the collidible area
     * between the two attached objects. Valid values are greater than 0. If you set the value to 10, then
     * the collision area will be 10 pixels wide. The width is perpendicular to a line connecting the two
     * objects
     *
     * @return Collision Rect Width
     */
    public double getCollisionRectWidth() {
        return collisionRectWidth;
    }

    public void setCollisionRectWidth(double w) {
        collisionRectWidth = w;
    }

    /**
     * The <code>restLength</code> property sets the length of SpringConstraint. This value will be
     * the distance between the two objects unless their position is altered by external forces. The
     * SpringConstraint will always try to keep the objects this distance apart.
     *
     * @return Rest Length
     */
    public double getRestLength() {
        return restLen;
    }

    public void setRestLength(double r) {
        restLen = r;
    }

    /**
     * Determines if the area between the two objects is tested for collision. If this value is on
     * you can set the <code>collisionRectScale</code> and <code>collisionRectWidth</code> properties
     * to alter the dimensions of the collidable area.
     *
     * @return If Collidable
     */
    public boolean getCollidable() {
        return collidable;
    }

    public void setCollidable(boolean b) {
        collidable = b;
        if (collidable) {
            collisionRect = new SpringConstraintObject(p1, p2);
            orientCollisionRectangle();
        } else {
            collisionRect = null;
        }
    }

    /**
     * Returns true if the passed object is one of the objects specified in the constructor.
     *
     * @param p Object2D
     * @return Returns true if the passed object is one of the objects specified in the constructor.
     */
    public boolean isConnectedTo(Object2D p) {
        return (p == p1 || p == p2);
    }

    /**
     * The default paint method for the constraint. Note that you should only use
     * the default painting methods for quick prototyping. For anything beyond that
     * you should always write your own classes that either extend one of the
     * MarsinEngine's object and constraint classes, or is a composite of them. Then within that
     * class you can define your own custom painting method.
     */
    public void paint() {

        if (dc == null) dc = getDefaultContainer();

        if (collidable) {
            collisionRect.paint();
        } else {
            if (!getVisible()) return;
            double X1 = p1.curr.x;
            double Y1 = p1.curr.y;
            double X2 = p2.curr.x;
            double Y2 = p2.curr.y;
            Line2D line = new Line2D.Double(X1, Y1, X2, Y2);
            dc.draw(line);
        }
    }

    public void resolve() {
        if (p1.getFixed() & p2.getFixed()) return;

        delta = p1.curr.minus(p2.curr);
        deltaLength = p1.curr.distance(p2.curr);
        if (collidable) orientCollisionRectangle();

        double diff = (deltaLength - restLen) / deltaLength;
        Vector2D dmd;
        if (p1.isIfBeingDragged() || p2.isIfBeingDragged()) {
            dmd = new Vector2D(0, 0);
        }else {
            dmd = delta.mult(diff * super.getStiffness());
        }

        double invM1 = p1.getInvMass();
        double invM2 = p2.getInvMass();
        double sumInvMass = invM1 + invM2;

        // REVIEW TO SEE IF A SINGLE FIXED OBJECT2D IS RESOLVED CORRECTLY
        if (!p1.getFixed()) p1.curr.minusEquals(dmd.mult((double) invM1 / sumInvMass));
        if (!p2.getFixed()) p2.curr.plusEquals(dmd.mult((double) invM2 / sumInvMass));
    }

    public RectangularObject getCollisionRect() {
        return collisionRect;
    }

    public void setImage(File src) {
        collisionRect.setImage(src);
    }

    private void orientCollisionRectangle() {
        Vector2D c = getCenter();
        double rot = getRotation();

        collisionRect.curr.setTo(c.x, c.y);
        collisionRect.getExtents().set(0, (deltaLength / 2) * collisionRectScale);
        collisionRect.getExtents().set(1, (collisionRectWidth / 2));
        collisionRect.setRotation(rot);
    }

    /**
     * if the two objects are at the same location warn the user
     */
    private void checkObjectssLocation() {
        if (p1.curr.x == p2.curr.x && p1.curr.y == p2.curr.y) {
            throw new Error("The two objects specified for a Spring Constraint can't be at the same location");
        }
    }

    public Object2D getP1() {
        return p1;
    }

    public Object2D getP2() {
        return p2;
    }
}
