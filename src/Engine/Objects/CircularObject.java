package Engine.Objects;

import Engine.Collision.Interval;
import Engine.Utilities.Vector2D;

import java.awt.geom.Ellipse2D;

/**
 * Created by IntelliJ IDEA.
 * User: Sina Solaimanpour
 * Date: 10/18/11
 * Time: 10:40 PM
 */
public class CircularObject extends Object2D {

    private double _radius;


    /**
     * @param x          The initial x position of this object.
     * @param y          The initial y position of this object.
     * @param radius     The radius of this object.
     * @param fixed      Determines if the object is fixed or not. Fixed objects
     *                   are not affected by forces or collisions and are good to use as surfaces.
     *                   Non-fixed objects move freely in response to collision and forces.
     * @param mass       The mass of the object.
     * @param elasticity The elasticity of the object. Higher values mean more elasticity or 'bounciness'.
     * @param friction   The surface friction of the object.
     */
    public CircularObject(
            double x,
            double y,
            double radius,
            boolean fixed,
            double mass,
            double elasticity,
            double friction) {
        super(x, y, fixed, mass, elasticity, friction);
        _radius = radius;

        // TG TODO cannot have this before calling a super.
        /*if (Double.valueOf(mass) == null)
            mass = 1;
        if (Double.valueOf(elasticity) == null)
            elasticity = 0.3;
        if (Double.valueOf(friction) == null)
            friction = 0;*/

    }

    /**
     * The radius of the object.
     */
    public double getRadius() {
        return _radius;
    }


    /**
     * @private
     */
    public void setRadius(double r) {
        _radius = r;
    }


    /**
     * The default paint method for the object. Note that you should only use
     * the default painting methods for quick prototyping. For anything beyond that
     * you should always write your own object classes that either extend one of the
     * MarsinEngine object and constraint classes, or is a composite of them. Then within that
     * class you can define your own custom painting method.
     */
    public void paint() {

        if (dc == null) dc = getDefaultContainer();

        if (!getVisible()) return;

        Ellipse2D.Double circle = new Ellipse2D.Double(curr.x - getRadius(), curr.y - getRadius(), (double) getRadius() * 2, (double) getRadius() * 2);
        dc.draw(circle);

    }


    // REVIEW FOR ANY POSSIBILITY OF PRECOMPUTING

    /**
     * @private
     */
    public Interval getProjection(Vector2D axis) {
        double c = curr.dot(axis);
        interval.min = c - _radius;
        interval.max = c + _radius;
        return interval;
    }


    /**
     * @private :D
     * @return :D
     */
    public Interval getIntervalX() {
        interval.min = curr.x - _radius;
        interval.max = curr.x + _radius;
        return interval;
    }


    /**
     * @private :D
     * @return :D
     */
    public Interval getIntervalY() {
        interval.min = curr.y - _radius;
        interval.max = curr.y + _radius;
        return interval;
    }
}
