package Engine.Objects;

import Engine.Utilities.Vector2D;
import Engine.World.World;

import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 1/17/12
 * Time: 1:25 AM
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unchecked")
public class WheelObject extends CircularObject {

    private RimObject rp;
    private Vector2D tan;
    private Vector2D normSlip;

    private ArrayList edgePositions = new ArrayList();
    private ArrayList edgeObjects = new ArrayList();
    private double traction;
    private double fi = 0;

    /**
     * @param x          The initial x position.
     * @param y          The initial y position.
     * @param radius     The radius of this object.
     * @param traction   The rotation of this object in radians.
     * @param fixed      Determines if the object is fixed or not. Fixed objects
     *                   are not affected by forces or collisions and are good to use as surfaces.
     *                   Non-fixed objects move freely in response to collision and forces.
     * @param mass       The mass of the object
     * @param elasticity The elasticity of the object. Higher values mean more elasticity.
     * @param friction   The surface friction of the object.
     *                   <p>
     *                   Note that WheelObjects can be fixed but still have their rotation property
     *                   changed.
     *                   </p>
     */
    public WheelObject(double x, double y, double radius, boolean fixed, double mass, double elasticity, double friction, double traction) {
        super(x, y, radius, fixed, mass, elasticity, friction);
        tan = new Vector2D(0, 0);
        normSlip = new Vector2D(0, 0);
        rp = new RimObject(radius, 2);

        setTraction(traction);

        edgePositions = getEdgePositions();
        edgeObjects = getEdgeObjects();
    }

    /**
     * The angular velocity of the WheelObject. You can alter this value to make the
     * WheelObject spin.
     * @return Wheel's Angular Velocity
     */
    public double getAngularVelocity() {
        return rp.getAngularVelocity();
    }

    public void setAngularVelocity(double a) {
        rp.setAngularVelocity(a);
    }

    /**
     * The amount of traction during a collision. This property controls how much traction is
     * applied when the WheelObject is in contact with another object. If the value is set
     * to 0, there will be no traction and the WheelObject will behave as if the
     * surface was totally slippery, like ice. Acceptable values are between 0 and 1.
     * <p/>
     * <p>
     * Note that the friction property behaves differently than traction. If the surface
     * friction is set high during a collision, the WheelObject will move slowly as if
     * the surface was covered in glue.
     * </p>
     * @return Wheel's Traction
     */
    public double getTraction() {
        return 1 - traction;
    }

    public void setTraction(double t) {
        traction = 1 - t;
    }

    /**
     * An Array of 4 contact objects on the rim of the wheel.  The edge objects
     * are positioned relatively at 12, 3, 6, and 9 o'clock positions. You can attach other
     * objects or constraints to these objects. Note this is a one-way effect, meaning the
     * WheelObject's motion will move objects attached to the edge objects, but the reverse
     * is not true.
     * <p/>
     * <p>
     * In order to access one of the 4 edge objects, you can use array notation
     * e.g., <code>myWheelObject.edgeObjects[0]</code>
     * </p>
     * @return Returns Edge Objects
     */
    public ArrayList getEdgeObjects() {

        if (edgePositions.size() == 0) getEdgePositions();

        if (edgeObjects.size() == 0) {
            CircularObject cp1 = new CircularObject(0, 0, 1, false, 1, 0.3, 0);
            cp1.setCollidable(false);
            cp1.setVisible(false);
            World.addObject(cp1);

            CircularObject cp2 = new CircularObject(0, 0, 1, false, 1, 0.3, 0);
            cp2.setCollidable(false);
            cp2.setVisible(false);
            World.addObject(cp2);

            CircularObject cp3 = new CircularObject(0, 0, 1, false, 1, 0.3, 0);
            cp3.setCollidable(false);
            cp3.setVisible(false);
            World.addObject(cp3);

            CircularObject cp4 = new CircularObject(0, 0, 1, false, 1, 0.3, 0);
            cp4.setCollidable(false);
            cp4.setVisible(false);
            World.addObject(cp4);

            edgeObjects.add(cp1);
            edgeObjects.add(cp2);
            edgeObjects.add(cp3);
            edgeObjects.add(cp4);

            updateEdgeObjects();
        }
        return edgeObjects;
    }

    public void updateFi() {
        float rx = new Double(rp.curr.x).floatValue();
        float ry = new Double(rp.curr.y).floatValue();
        setFi(Math.atan(ry / rx));
    }

    public void setFi(double fi) {
        this.fi = fi;
    }

    public double getFi() {
        return fi;
    }

    /**
     * An Array of 4 <code>Vector2D</code> objects storing the location of the 4
     * edge positions of this WheelObject. The edge positions
     * are located relatively at the 12, 3, 6, and 9 o'clock positions.
     * @return Edge Positions
     */
    public ArrayList getEdgePositions() {

        if (edgePositions.size() == 0) {
            edgePositions.add(new Vector2D(0, 0));
            edgePositions.add(new Vector2D(0, 0));
            edgePositions.add(new Vector2D(0, 0));
            edgePositions.add(new Vector2D(0, 0));

            updateEdgePositions();
        }
        return edgePositions;
    }

    /**
     * The default paint method for the object. Note that you should only use
     * the default painting methods for quick prototyping. For anything beyond that
     * you should always write your own classes that either extend one of the
     * MarsinEngine object and constraint classes, or is a composite of them. Then within that
     * class you can define your own custom painting method.
     */
    public void paint() {

        float px = new Double(curr.x).floatValue();
        float py = new Double(curr.y).floatValue();
        float rx = new Double(rp.curr.x).floatValue();
        float ry = new Double(rp.curr.y).floatValue();

        if (dc == null) dc = getDefaultContainer();
        if (!getVisible()) return;

        GeneralPath f1 = new GeneralPath();

        f1.moveTo(px, py);
        f1.lineTo(rx + px, ry + py);

        f1.moveTo(px, py);
        f1.lineTo(-rx + px, -ry + py);

        f1.moveTo(px, py);
        f1.lineTo(-ry + px, rx + py);

        f1.moveTo(px, py);
        f1.lineTo(ry + px, -rx + py);

        dc.draw(f1);

        setFi(Math.atan(ry / rx));

        // draw wheel circle
        Ellipse2D.Double circle = new Ellipse2D.Double(curr.x - getRadius(), curr.y - getRadius(), (double) getRadius() * 2, (double) getRadius() * 2);
        dc.draw(circle);

    }

    public void update(double dt) {
        super.update(dt);
        rp.update(dt);

        if (edgePositions != null) updateEdgePositions();
        if (edgeObjects != null) updateEdgeObjects();
    }

    public void resolveCollision(
            Vector2D mtd,
            Vector2D velocity,
            Vector2D normal,
            double depth,
            double order) {

        super.resolveCollision(mtd, velocity, normal, depth, order);
        resolve(normal.mult(sign(depth * order)));
    }


    /**
     * simulates torque/wheel-ground interaction - n is the surface normal
     * Origins of this code thanks to Raigan Burns, Metanet software
     * @param n a Vector To Resolve
     */
    private void resolve(Vector2D n) {

        tan.setTo(-rp.curr.y, rp.curr.x);

        tan = tan.normalize();

        // velocity of the wheel's surface 
        Vector2D wheelSurfaceVelocity = tan.mult(rp.getSpeed());

        // the velocity of the wheel's surface relative to the ground
        Vector2D combinedVelocity = getVelocity().plusEquals(wheelSurfaceVelocity);

        // the wheel's comb velocity projected onto the contact normal
        double cp = combinedVelocity.cross(n);

        // set the wheel's spinspeed to track the ground
        tan.multEquals(cp);
        rp.prev.copy(rp.curr.minus(tan));

        // some of the wheel's torque is removed and converted into linear displacement
        double slipSpeed = (1 - traction) * rp.getSpeed();
        normSlip.setTo(slipSpeed * n.y, slipSpeed * n.x);
        curr.plusEquals(normSlip);
        rp.setSpeed(rp.getSpeed() * traction);
    }

    private void updateEdgePositions() {

        double px = curr.x;
        double py = curr.y;
        double rx = rp.curr.x;
        double ry = rp.curr.y;

        ((Vector2D) edgePositions.get(0)).setTo(rx + px, ry + py);
        ((Vector2D) edgePositions.get(1)).setTo(-ry + px, rx + py);
        ((Vector2D) edgePositions.get(2)).setTo(-rx + px, -ry + py);
        ((Vector2D) edgePositions.get(3)).setTo(ry + px, -rx + py);
    }

    private void updateEdgeObjects() {
        for (int i = 0; i < 4; i++) {
            ((CircularObject) edgeObjects.get(i)).setpx(((Vector2D) edgePositions.get(i)).x);
            ((CircularObject) edgeObjects.get(i)).setpy(((Vector2D) edgePositions.get(i)).y);
        }
    }

    /**
     * Returns 1 if the value is >= 0. Returns -1 if the value is < 0.
     */
    private int sign(double val) {
        if (val < 0) return -1;
        return 1;
    }

}
