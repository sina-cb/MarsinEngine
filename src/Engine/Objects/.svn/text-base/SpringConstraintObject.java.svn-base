package Engine.Objects;

import Engine.Utilities.Vector2D;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 1/17/12
 * Time: 1:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class SpringConstraintObject extends RectangularObject {

    private Object2D p1;
    private Object2D p2;
    private Vector2D avgVelocity;

    /**
     * @param x          The initial x position.
     * @param y          The initial y position.
     * @param width      The width of this object.
     * @param height     The height of this object.
     * @param rotation   The rotation of this object in radians.
     * @param fixed      Determines if the object is fixed or not. Fixed objects
     *                   are not affected by forces or collisions and are good to use as surfaces.
     *                   Non-fixed objects move freely in response to collision and forces.
     * @param mass       The mass of the object
     * @param elasticity The elasticity of the object. Higher values mean more elasticity.
     * @param friction   The surface friction of the object.
     *                   <p>
     *                   Note that RectangleObjects can be fixed but still have their rotation property
     *                   changed.
     *                   </p>
     */
    public SpringConstraintObject(double x, double y, double width, double height, double rotation, boolean fixed, double mass, double elasticity, double friction) {
        super(x, y, width, height, rotation, fixed, mass, elasticity, friction);
    }

    public SpringConstraintObject(Object2D p1, Object2D p2) {
        //TG TODO check the defaults to super class
        super(0, 0, 0, 0, 0, false, 1, 0.3, 0);
        this.p1 = p1;
        this.p2 = p2;
        avgVelocity = new Vector2D(0, 0);
    }


    /**
     * returns the average mass of the two connected particles
     */
    public double getMass() {
        return (p1.getMass() + p2.getMass()) / 2;
    }


    /**
     * returns the average velocity of the two connected particles
     */
    public Vector2D getVelocity() {
        Vector2D p1v = p1.getVelocity();
        Vector2D p2v = p2.getVelocity();

        avgVelocity.setTo(((p1v.x + p2v.x) / 2), ((p1v.y + p2v.y) / 2));
        return avgVelocity;
    }


    public void paint() {
        if (cornerPositions != null) updateCornerPositions();
        super.paint();
    }


    public void resolveCollision(Vector2D mtd, Vector2D vel, Vector2D n, double d, double o) {

        if (!p1.getFixed()) {
            p1.curr.plusEquals(mtd);
            p1.setVelocity(vel);
        }

        if (!p2.getFixed()) {
            p2.curr.plusEquals(mtd);
            p2.setVelocity(vel);
        }
    }

}