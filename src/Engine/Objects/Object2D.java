package Engine.Objects;

import Engine.Collision.Collision;
import Engine.Collision.Interval;
import Engine.Utilities.Vector2D;
import Engine.World.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Sina Solaimanpour
 * Date: 10/18/11
 * Time: 10:34 PM
 */
public class Object2D {

    public Vector2D curr;
    public Vector2D prev;
    public boolean isColliding;
    public Interval interval;
    protected Graphics2D dc;

    private Vector2D forces;
    private Vector2D temp;

    private double elasticityValue;
    private double mass;
    private double massInvert;
    private boolean ifSolid;
    private boolean isVisible;
    private double frictionValue;
    private boolean isCollidable;
    private Collision collision;
    double rotationSpeed = 0;

    String type = ObjectTypes.COMMON;
    long birthTime = 0;
    boolean ifBeingDragged = false;

    private URL imageSrc = null;
    private BufferedImage bf = null;
    
    public Object2D(
            double x,
            double y,
            boolean isFixed,
            double mass,
            double elasticity,
            double friction) {

        interval = new Interval(0, 0);

        curr = new Vector2D(x, y);
        prev = new Vector2D(x, y);
        temp = new Vector2D(0, 0);
        setFixed(isFixed);

        forces = new Vector2D(0, 0);
        collision = new Collision(new Vector2D(0, 0), new Vector2D(0, 0));
        isColliding = false;

        setMass(mass);
        setElasticity(elasticity);
        setFriction(friction);

        setCollidable(true);
        setVisible(true);
    }

    /**
     * The mass of the object. Valid values are greater than zero. By default, all objects
     * have a mass of 1.
     * <p/>
     * <p>
     * The mass property has no relation to the size of the object. However it can be
     * easily simulated when creating objects. A simple example would be to set the
     * mass and the size of a object to same value when you instantiate it.
     * </p>
     * @return object's mass
     */
    public double getMass() {
        return mass;
    }

    public void setMass(double m) {
        if (m <= 0) throw new Error("mass may not be set <= 0");
        mass = m;
        massInvert = 1 / mass;
    }

    /**
     * The elasticity of the object. Standard values are between 0 and 1.
     * The higher the value, the greater the elasticity.
     * <p/>
     * <p>
     * During collisions the elasticity values are combined. If one object's
     * elasticity is set to 0.4 and the other is set to 0.4 then the collision will
     * be have a total elasticity of 0.8. The result will be the same if one object
     * has an elasticity of 0 and the other 0.8.
     * </p>
     * <p/>
     * <p>
     * Setting the elasticity to greater than 1 (of a single object, or in a combined
     * collision) will cause objects to bounce with energy greater than naturally
     * possible. Setting the elasticity to a value less than zero is allowed but may cause
     * unexpected results.
     * </p>
     * @return Object's Elasticity
     */
    public double getElasticity() {
        return elasticityValue;
    }

    public void setElasticity(double k) {
        elasticityValue = k;
    }

    /**
     * The visibility of the object. This is only implemented for the default painting
     * methods of the objects. When you create your painting methods in subclassed or
     * composite objects, you should add a check for this property.
     * @return if the object is visible
     */
    public boolean getVisible() {
        return isVisible;
    }

    public void setVisible(boolean v) {
        isVisible = v;
    }

    /**
     * The surface friction of the object. Values must be in the range of 0 to 1.
     * <p/>
     * <p>
     * 0 is no friction (slippery), 1 is full friction (sticky).
     * </p>
     * <p/>
     * <p>
     * During collisions, the friction values are summed, but are clamped between 1 and 0.
     * For example, If two objects have 0.7 as their surface friction, then the resulting
     * friction between the two objects will be 1 (full friction).
     * </p>
     * <p/>
     * <p>
     * Note: In the current release, only dynamic friction is calculated. Static friction
     * is planned for a later release.
     * </p>
     * @return object's friction
     */
    public double getFriction() {
        return frictionValue;
    }

    public void setFriction(double f) {
        if (f < 0 || f > 1) throw new Error("Legal friction must be >= 0 and <=1");
        frictionValue = f;
    }

    /**
     * The fixed state of the object. If the object is fixed, it does not move
     * in response to forces or collisions. Fixed objects are good for surfaces.
     * @return if the object is fixed
     */
    public boolean getFixed() {
        return ifSolid;
    }

    public void setFixed(boolean f) {
        ifSolid = f;
    }

    public Vector2D getPosition() {
        return new Vector2D(curr.x, curr.y);
    }

    public void setPosition(Vector2D p) {
        curr.copy(p);
        prev.copy(p);
    }

    /**
     * The x position of this object
     * @return object's X
     */
    public double getPositionX() {
        return curr.x;
    }

    /**
     * sets the position of the object
     * @param x object's X
     */
    public void setPositionX(double x) {
        curr.x = x;
        prev.x = x;
    }

    /**
     * The y position of this object
     * @return object's Y
     */
    public double getPositionY() {
        return curr.y;
    }

    /**
     * sets the position of the object
     * @param y object's Y
     */
    public void setPositionY(double y) {
        curr.y = y;
        prev.y = y;
    }

    /**
     * @return object's velocity
     */
    public Vector2D getVelocity() {
        return curr.minus(prev);
    }

    /**
     * sets the object's velocity
     * @param v the velocity
     */
    public void setVelocity(Vector2D v) {
        prev = curr.minus(v);
    }

    /**
     * Determines if the object can collide with other objects or not.
     * The default state is true.
     * @return object's collidable state
     */
    public boolean isCollidable() {
        return isCollidable;
    }

    /**
     * @param b sets the object's collidable state
     */
    public void setCollidable(boolean b) {
        isCollidable = b;
    }

    /**
     * Adds a force to the object. The mass of the object is taken into
     * account when using this method, so it is useful for adding forces
     * that simulate effects like wind. objects with larger masses will
     * not be affected as greatly as those with smaller masses.
     *
     * @param f A Vector2D representing the force added.
     */
    public void addForce(Vector2D f) {
        forces.plusEquals(f.multEquals(massInvert));
    }

    /**
     * Adds a 'massless' force to the object. The mass of the object is
     * not taken into account when using this method, so it is useful for
     * adding forces that simulate effects like gravity. objects with
     * larger masses will be affected the same as those with smaller masses.
     * If we want to have a constant force we should add it to the world's force
     * and masslessForce object
     *
     * @param f A Vector2D representing the force added.
     */
    public void addMasslessForce(Vector2D f) {
        forces.plusEquals(f);
    }

    /**
     * this will update the object's properties such as forces, and stuff like that
     * @param dt2 the timestep of the world
     */
    public void update(double dt2) {
        if (ifSolid) return;

        // global forces
        addForce(World.force);
        addMasslessForce(World.masslessForce);

        // object update
        temp.copy(curr);
        Vector2D nv = getVelocity().plus(forces.multEquals(dt2));
        curr.plusEquals(nv.multEquals(World.getDamping()));
        prev.copy(temp);

        // clear the forces
        forces.setTo(0, 0);
    }

    /**
     * when resolving the collision we need two vectors (vt and vn)
     * this method will calculate these vectors and return them
     * @param collisionNormal Normal axis for Collision penetration
     * @return vt and vn vectors we need
     */
    public Collision getComponents(Vector2D collisionNormal) {
        Vector2D vel = getVelocity();
        double vdotn = collisionNormal.dot(vel);
        collision.vn = collisionNormal.mult(vdotn);
        collision.vt = vel.minus(collision.vn);
        return collision;
    }

    /**
     * this method will resolve the collision occurred between two objects having
     * three Response Modes (Standard, Selective And Simple)
     * TODO We should determine what this method is doing and determine the inputs...
     * @param mtd
     * @param vel
     * @param n
     * @param d
     * @param o
     */
    public void resolveCollision(Vector2D mtd, Vector2D vel, Vector2D n, double d, double o) {
        curr.plusEquals(mtd);

        switch (World.getCollisionResponseMode()) {
            case World.STANDARD:
                setVelocity(vel);
                break;

            case World.SELECTIVE:
                if (!isColliding) setVelocity(vel);
                isColliding = true;
                break;

            case World.SIMPLE:
                break;
        }
    }

    public void setImage(File src) {
        try {
            imageSrc = (src.toURI()).toURL();
        } catch (MalformedURLException e) {
        }

        try {
            bf = ImageIO.read(imageSrc);
        } catch (IOException e) {
            System.err.println("Error Type 3 Handled");
        }
    }

    public boolean hasImage() {
        if (bf == null) {
            return false;
        }else {
            return true;
        }
    }
    
    public BufferedImage getImage() {
        return bf;
    }
    
    /**
     * returns world's default container
     * @return world's default container
     */
    public Graphics2D getDefaultContainer() {
        if (World.getDefaultContainer() == null) {
            String err = "";
            err += "You must set the defaultContainer property of the World class ";
            err += "if you wish to use the default paint methods of the objects";
            throw new Error(err);
        }
        return World.getDefaultContainer();
    }

    /**
     * @return object's inversed mass
     */
    public double getInvMass() {
        return massInvert;
    }

    /**
     * this will returns an interval object for projection of an object
     * @param axis the axis we want to project on
     * @return the interval
     */
    public Interval getProjection(Vector2D axis) {
        return null;
    }

    /**
     * Object's Rotation Speed
     * @return Angular Speed
     */
    public double getRotationSpeed() {
        return rotationSpeed;
    }

    /**
     * @param rotationSpeed Angular Speed
     */
    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    /**
     * The x position of this particle
     * @return aaa
     */
    public double getpx() {
        return curr.x;
    }

    public void setpx(double x) {
        curr.x = x;
        prev.x = x;
    }

    /**
     * The y position of this particle
     * @return aaa
     */
    public double getpy() {
        return curr.y;
    }

    public void setpy(double y) {
        curr.y = y;
        prev.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(long birthTime) {
        this.birthTime = birthTime;
    }

    public boolean isIfBeingDragged() {
        return ifBeingDragged;
    }

    public void setIfBeingDragged(boolean ifBeingDragged) {
        this.ifBeingDragged = ifBeingDragged;
    }
}
