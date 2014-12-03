package Engine.Objects;

import Engine.Collision.Interval;
import Engine.Utilities.Vector2D;
import Engine.World.World;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sina Solaimanpour
 * Date: 10/18/11
 * Time: 10:41 PM
 */
@SuppressWarnings("unchecked")
public class RectangularObject extends Object2D {

    public ArrayList cornerPositions = new ArrayList();

    private ArrayList cornerObjects = new ArrayList();
    private ArrayList extents = new ArrayList();
    private ArrayList axes = new ArrayList();
    private double rotation;


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
    //TG TODO check defaults
    public RectangularObject(
            double x,
            double y,
            double width,
            double height,
            double rotation,
            boolean fixed,
            double mass,
            double elasticity,
            double friction) {

        super(x, y, fixed, mass, elasticity, friction);

        extents.add(width / 2);
        extents.add(height / 2);

        axes.add(new Vector2D(0, 0));
        axes.add(new Vector2D(0, 0));
        setRotation(rotation);

        cornerPositions = getCornerPositions();
        cornerObjects = getCornerObjects();
    }


    /**
     * The rotation of the RectangleObject in radians. For drawing methods you may
     * want to use the <code>angle</code> property which gives the rotation in
     * degrees from 0 to 360.
     * <p/>
     * <p>
     * Note that while the RectangleObject can be rotated, it does not have angular
     * velocity. In otherwords, during collisions, the rotation is not altered,
     * and the energy of the rotation is not applied to other colliding objects.
     * A true rigid body is planned for a later release.
     * </p>
     */
    public double getRotation() {
        return rotation;
    }

    public void setRotation(double t) {
        rotation = t;
        setAxes(t);
    }

    /**
     * An Array of 4 contact objects at the corners of the RectangleObject. You can attach
     * other objects or constraints to these objects. Note this is a one-way effect, meaning the
     * RectangleObject's motion will move objects attached to the corner objects, but the
     * reverse is not true.
     * <p/>
     * <p>
     * In order to access one of the 4 corner objects, you can use array notation
     * e.g., <code>myRectangleObject.cornerObjects[0]</code>
     * </p>
     */
    public ArrayList getCornerObjects() {

        boolean showCornerObjects = false;

        if (cornerPositions.size() == 0) getCornerPositions();
        if (cornerObjects.size() == 0) {
            CircularObject cp1 = new CircularObject(0.0, 0.0, 1.0, false, 1.0, 0.3, 0.0);
            cp1.setCollidable(false);
            cp1.setVisible(showCornerObjects);
            World.addObject(cp1);

            CircularObject cp2 = new CircularObject(0.0, 0.0, 1.0, false, 1.0, 0.3, 0.0);
            cp2.setCollidable(false);
            cp2.setVisible(showCornerObjects);
            World.addObject(cp2);

            CircularObject cp3 = new CircularObject(0.0, 0.0, 1.0, false, 1.0, 0.3, 0.0);
            cp3.setCollidable(false);
            cp3.setVisible(showCornerObjects);
            World.addObject(cp3);

            CircularObject cp4 = new CircularObject(0.0, 0.0, 1.0, false, 1.0, 0.3, 0.0);
            cp4.setCollidable(false);
            cp4.setVisible(showCornerObjects);
            World.addObject(cp4);

            cornerObjects.add(cp1);
            cornerObjects.add(cp2);
            cornerObjects.add(cp3);
            cornerObjects.add(cp4);

            updateCornerObjects();
        }
        return cornerObjects;
    }


    /**
     * An Array of <code>Vector2D</code> objects storing the location of the 4
     * corners of this RectangleObject. This method would usually be called
     * in a painting method if the locations of the corners were needed. If the
     * RectangleObject is being drawn using its position and angle properties
     * then you don't need to access this property.
     */
    public ArrayList getCornerPositions() {

        if (cornerPositions.size() == 0) {
            cornerPositions.add(new Vector2D(0, 0));
            cornerPositions.add(new Vector2D(0, 0));
            cornerPositions.add(new Vector2D(0, 0));
            cornerPositions.add(new Vector2D(0, 0));

            updateCornerPositions();
        }
        return cornerPositions;
    }


    /**
     * The default paint method for the object. Note that you should only use
     * the default painting methods for quick prototyping. For anything beyond that
     * you should always write your own object classes that extend one of the
     * MarsinEngine object classes. Then within that class you can define your own custom
     * painting method.
     */
    public void paint() {

        if (dc == null) dc = getDefaultContainer();

        if (!getVisible()) return;

        for (int j = 0; j < 4; j++) {
            int i = j;

            double X1 = ((Vector2D) cornerPositions.get(i)).x;
            double Y1 = ((Vector2D) cornerPositions.get(i)).y;

            // point back to first element
            if (j == 3) i = -1;

            double X2 = ((Vector2D) cornerPositions.get(i + 1)).x;
            double Y2 = ((Vector2D) cornerPositions.get(i + 1)).y;

            java.awt.geom.Line2D line = new java.awt.geom.Line2D.Double(X1, Y1, X2, Y2);
            dc.draw(line);
        }

    }

    public void update(double dt2) {
        super.update(dt2);
        if (cornerPositions.size() != 0) updateCornerPositions();
        if (cornerObjects.size() != 0) updateCornerObjects();
    }

    public ArrayList getAxes() {
        return axes;
    }

    public ArrayList getExtents() {
        return extents;
    }

    public Interval getProjection(Vector2D axis) {

        double radius =
                (Double) extents.get(0) * Math.abs(axis.dot((Vector2D) axes.get(0))) +
                        (Double) extents.get(1) * Math.abs(axis.dot((Vector2D) axes.get(1)));

        double c = curr.dot(axis);

        interval.min = c - radius;
        interval.max = c + radius;
        return interval;
    }

    public void updateCornerPositions() {

        double ae0_x = ((Vector2D) axes.get(0)).x * (Double) extents.get(0);
        double ae0_y = ((Vector2D) axes.get(0)).y * (Double) extents.get(0);
        double ae1_x = ((Vector2D) axes.get(1)).x * (Double) extents.get(1);
        double ae1_y = ((Vector2D) axes.get(1)).y * (Double) extents.get(1);

        double emx = ae0_x - ae1_x;
        double emy = ae0_y - ae1_y;
        double epx = ae0_x + ae1_x;
        double epy = ae0_y + ae1_y;

        Vector2D cornerPosition1 = new Vector2D(0, 0);
        Vector2D cornerPosition2 = new Vector2D(0, 0);
        Vector2D cornerPosition3 = new Vector2D(0, 0);
        Vector2D cornerPosition4 = new Vector2D(0, 0);

        cornerPosition1.x = curr.x - epx;
        cornerPosition1.y = curr.y - epy;
        cornerPositions.set(0, cornerPosition1);

        cornerPosition2.x = curr.x + emx;
        cornerPosition2.y = curr.y + emy;
        cornerPositions.set(1, cornerPosition2);

        cornerPosition3.x = curr.x + epx;
        cornerPosition3.y = curr.y + epy;
        cornerPositions.set(2, cornerPosition3);

        cornerPosition4.x = curr.x - emx;
        cornerPosition4.y = curr.y - emy;
        cornerPositions.set(3, cornerPosition4);

    }

    private void updateCornerObjects() {
        for (int i = 0; i < 4; i++) {
            ((Object2D) getCornerObjects().get(i)).setPositionX(((Vector2D) cornerPositions.get(i)).x);
            ((Object2D) getCornerObjects().get(i)).setPositionY(((Vector2D) cornerPositions.get(i)).y);
        }
    }

    private void setAxes(double t) {
        double s = Math.sin(t);
        double c = Math.cos(t);

        ((Vector2D) axes.get(0)).x = c;
        ((Vector2D) axes.get(0)).y = s;
        ((Vector2D) axes.get(1)).x = -s;
        ((Vector2D) axes.get(1)).y = c;
    }
}
