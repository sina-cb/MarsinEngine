package Engine.World;

import Engine.Collision.CollisionDetector;
import Engine.Constraint.AbstractConstraint;
import Engine.Constraint.SpringConstraint;
import Engine.Events.CollisionListener;
import Engine.Objects.*;
import Engine.Utilities.Vector2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by IntelliJ IDEA.
 * User: Sina Solaimanpour
 * Date: 10/18/11
 * Time: 10:43 PM
 */
@SuppressWarnings("unchecked")
public final class World {
    public static final int STANDARD = 100;
    public static final int SELECTIVE = 200;
    public static final int SIMPLE = 300;
    public static final int OPTIMIZED = 400;

    private URL imageSrc = null;
    private BufferedImage background = null;

    public static Vector2D force = new Vector2D(0, 0);
    public static Vector2D masslessForce = new Vector2D(0, 0);

    private static double timeStep;
    private static ArrayList objects = new ArrayList();
    private static ArrayList rotatingObjects = new ArrayList();

    private static ArrayList constraints = new ArrayList();

    private static double damping;
    public static Graphics2D defaultContainer;
    private static int collisionResponseMode = STANDARD;
    private static int impactResponseMode = OPTIMIZED;

    private static int screenWidth = 1600;
    private static int screenHeight = 900;
    private static int playHeight = 820;
    private int speed = 30;

    public BufferStrategy strategy;
    public long usedTime;
    public static ArrayList paintQueue = new ArrayList();
    public static CollisionDetector cd = new CollisionDetector();

    Timer clock = null;

    /**
     * Initializes the engine. You must call this method prior to adding
     * any objects or constraints.
     *
     * @param dt The delta time value for the engine. This parameter can be used -- in
     *           conjunction with speed at which <code>World.step()</code> is called -- to change the speed
     *           of the simulation. Typical values are 1/3 or 1/4. Lower values result in slower,
     *           but more accurate simulations, and higher ones result in faster, less accurate ones.
     *           Note that this only applies to the forces added to objects. If you do not add any
     *           forces, the <code>dt</code> value won't matter.
     */
    public static void init(double dt) {
        timeStep = dt * dt;
        damping = 1;
    }

    /**
     * The global damping. Values should be between 0 and 1. Higher numbers
     * result in less damping. A value of 1 is no damping. A value of 0 will
     * not allow any objects to move. The default is 1.
     * <p/>
     * <p>
     * Damping will slow down your simulation and make it more stable. If you find
     * that your sim is "blowing up', try applying more damping.
     * </p>
     *
     * @return Returns Damping
     */
    public static double getDamping() {
        return damping;
    }

    public static void setDamping(double d) {
        damping = d;
    }

    /**
     * The default container used by the default painting methods of the objects and
     * constraints. If you wish to use to the built in painting methods you must set
     * this first.
     * <p/>
     * <p>
     * For simple prototyping, a default painting method is included in the engine. For
     * any serious development, you should either subclass or make a composite of the constraints
     * and objects, and write your own painting methods. If you do that, it is not necessary
     * to call this function, although you can use it for you own painting methods if you need
     * a container.
     * </p>
     */
    public static Graphics2D getDefaultContainer() {
        return defaultContainer;
    }


    /**
     * @private
     */
    public static void setDefaultContainer(Graphics2D s) {
        defaultContainer = s;
    }

    /**
     * The collision response mode for the engine. The engine has three different possible
     * settings for the collisionResponseMode property. Valid values are World.STANDARD,
     * World.SELECTIVE, and World.SIMPLE. Those settings go in order from slower and
     * more accurate to faster and less accurate. In all cases it's worth experimenting to
     * see what mode suits your sim best.
     * <p/>
     * <ul>
     * <li>
     * <b>World.STANDARD</b>&mdash;Objects are moved out of collision and then velocity is
     * applied. Momentum is conserved and the mass of the objects is properly calculated. This
     * is the default and most physically accurate setting.<br/><br/>
     * </li>
     * <p/>
     * <li>
     * <b>World.SELECTIVE</b>&mdash;Similar to the World.STANDARD setting, except only
     * previously non-colliding objects have their velocity set. In otherwords, if there are
     * multiple collisions on a object, only the first collision on that object causes a
     * change in its velocity. Both this and the World.SIMPLE setting may give better results
     * than World.STANDARD when using a large number of colliding objects.<br/><br/>
     * </li>
     * <p/>
     * <li>
     * <b>World.SIMPLE</b>&mdash;Objects do not have their velocity set after colliding. This
     * is faster than the other two modes but is the least accurate. Mass is not calculated, and
     * there is no conservation of momentum. <br/><br/>
     * </li>
     * </ul>
     * @return Collision Response Mode
     */
    public static int getCollisionResponseMode() {
        return collisionResponseMode;
    }

    public static void setCollisionResponseMode(int m) {
        collisionResponseMode = m;
    }

    /**
     * Adds a force to all objects in the system. The mass of the object is taken into
     * account when using this method, so it is useful for adding forces that simulate effects
     * like wind. Objects with larger masses will not be affected as greatly as those with
     * smaller masses. Note that the size (not to be confused with mass) of the object has
     * no effect on its physical behavior.
     *
     * @param v Force to add
     */
    public static void addForce(Vector2D v) {
        force.plusEquals(v);
    }

    /**
     * Adds a 'massless' force to all objects in the system. The mass of the object is
     * not taken into account when using this method, so it is useful for adding forces that
     * simulate effects like gravity. Objects with larger masses will be affected the same
     * as those with smaller masses. Note that the size (not to be confused with mass) of
     * the object has no effect on its physical behavior.
     *
     * @param v MasslessForce to add
     */
    public static void addMasslessForce(Vector2D v) {
        masslessForce.plusEquals(v);
    }

    /**
     * Adds a object to the engine.
     *
     * @param p The object to be added.
     */
    public static void addObject(Object2D p) {
        objects.add(p);
        paintQueue.add(p);
    }

    /**
     * Removes a object to the engine.
     *
     * @param p The object to be removed.
     */
    public static void removeObject(Object2D p) {
        int ppos = objects.indexOf(p);
        if (ppos == -1) return;
        objects.remove(ppos);
        paintQueue.remove(p);
    }

    /**
     * Adds a constraint to the engine.
     *
     * @param c The constraint to be added.
     */
    public static void addConstraint(AbstractConstraint c) {
        constraints.add(c); // adds to the end of the list http://java.sun.com/j2se/1.4.2/docs/api/java/util/ArrayList.html
        paintQueue.add(c);
    }

    /**
     * Removes a constraint from the engine.
     *
     * @param c The constraint to be removed.
     */
    public static void removeConstraint(AbstractConstraint c) {
        int cpos = constraints.indexOf(c);
        if (cpos == -1) return;
        constraints.remove(cpos);
        paintQueue.remove(c);
    }

    /**
     * @return Returns an array of every item added to the engine. This includes all objects and constraints.
     */
    public static ArrayList getAll() {
        ArrayList a = (ArrayList) objects.clone(); // I have added this line
        a.addAll(constraints);
        return a;
    }

    /**
     * @return Returns an array of every object added to the engine.
     */
    public static ArrayList getAllObjects() {
        return objects;
    }

    /**
     * Returns an array of all the constraints added to the engine
     *
     * @return Returns All Constraints
     */
    public static ArrayList getAllConstraints() {
        return constraints;
    }

    /**
     * @return <p>Returns an array of objects added to the engine whose type is one of the built-in
     *         object types in the MarsinE. This includes the CircularObject, WheelObject, and
     *         RectangularObject.</p>
     */
    public static ArrayList getObjects() {
        ArrayList tempObjects = new ArrayList();
        for (Object object : objects) {
            Object2D p = (Object2D) object;
            tempObjects.add(p);
        }
        return tempObjects;
    }

    /**
     * The main step function of the engine. This method should be called
     * continously to advance the simulation. The faster this method is
     * called, the faster the simulation will run. Usually you would call
     * this in your main program loop.
     */
    public static void step() {
        updateObjects();
        updateConstraints();
        checkCollisions();
    }

    private static void updateObjects() {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) instanceof RectangularObject && !((RectangularObject) objects.get(i)).isIfBeingDragged())
                ((RectangularObject) objects.get(i)).update(timeStep);
            else if (objects.get(i) instanceof CircularObject && !((CircularObject) objects.get(i)).isIfBeingDragged())
                ((CircularObject) objects.get(i)).update(timeStep);
        }
    }

    private static void updateConstraints() {
        for (int i = 0; i < constraints.size(); i++) {
            ((AbstractConstraint) constraints.get(i)).resolve();
        }
    }

    /**
     * Checks all collisions between objects and constraints. The following rules apply:
     * Objects vs Objects are tested unless either collidable property is set to false.
     * Objects vs Constraints are not tested by default unless collidable is true.
     * is called on a SpringConstraint. AngularConstraints are not tested for collision,
     * but their component SpringConstraints are -- with the previous rule in effect. If
     * a Object is attached to a SpringConstraint it is never tested against that
     * SpringConstraint for collision
     */
    private static void checkCollisions() {
        for (int j = 0; j < objects.size(); j++) {

            Object2D pa = (Object2D) objects.get(j);

            if (pa != null) {
                for (int i = j + 1; i < objects.size(); i++) {
                    Object2D pb = (Object2D) objects.get(i);
                    if (pa.isCollidable() && pb.isCollidable()) {
                        cd.testCollision(pa, pb);
                    }
                }

                for (int n = 0; n < constraints.size(); n++) {
                    SpringConstraint c = (SpringConstraint) constraints.get(n);
                    if (pa.isCollidable() && c.getCollidable() && !c.isConnectedTo(pa)) {
                        cd.testCollision(pa, c.getCollisionRect());
                    }
                }

                pa.isColliding = false;
            }
        }
    }

    public void paintWorld() {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        //anti aliaising
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // TG set the default container.
        setDefaultContainer(g);

        if (paintQueue.size() == 0) {
            paintFps(g);
            strategy.show();
            g.clearRect(0, 0, getScreenWidth(), getScreenHeight());
        } else {
            try {
                for (Object aPaintQueue : paintQueue) {
                    if (aPaintQueue instanceof RectangularObject && !((RectangularObject) aPaintQueue).hasImage())
                        ((RectangularObject) aPaintQueue).paint();
                    else if (aPaintQueue instanceof CircularObject && !((CircularObject) aPaintQueue).hasImage())
                        ((CircularObject) aPaintQueue).paint();
                    else if (aPaintQueue instanceof SpringConstraint && !((SpringConstraint) aPaintQueue).getCollisionRect().hasImage())
                        ((SpringConstraint) aPaintQueue).paint();

                    if (aPaintQueue instanceof WheelObject) {
                        ((WheelObject) aPaintQueue).updateFi();
                    }
                }
                paintFps(g);
                strategy.show();
                g.clearRect(0, 0, getScreenWidth(), getScreenHeight());
            } catch (ConcurrentModificationException e) {
                System.err.println("Error Type 1 Handled...");
            }
        }

        if (hasBackground()) {
            paintBackground(g);
        }
        paintImages(g);
    }

    private void paintBackground(Graphics2D g) {
        BufferedImage bf = background;

        double theObjectsWid = World.getScreenWidth();
        double theObjectsHei = World.getScreenHeight();

        double rescalingX = theObjectsWid / bf.getWidth();
        double rescalingY = theObjectsHei / bf.getHeight();

        AffineTransform at = new AffineTransform();
        at.scale(rescalingX, rescalingY);
        g.drawImage(bf, at, null);
    }

    private void paintImages(Graphics2D g) {

        try {
            for (Object obj : paintQueue) {

                BufferedImage bf = null;
                if (obj instanceof RectangularObject || obj instanceof CircularObject)
                    bf = ((Object2D) obj).getImage();
                else
                    bf = ((SpringConstraint) obj).getCollisionRect().getImage();

                if (obj instanceof RectangularObject && bf != null) {
                    RectangularObject theObject = (RectangularObject) obj;

                    double theObjectsWid = (Double) (theObject.getExtents().get(0)) * 2;
                    double theObjectsHei = (Double) (theObject.getExtents().get(1)) * 2;

                    double rescalingX = theObjectsWid / bf.getWidth();
                    double rescalingY = theObjectsHei / bf.getHeight();

                    double rotateAngle = theObject.getRotation();

                    AffineTransform at = new AffineTransform();

                    // 1. translate it to the center of the component
                    at.translate((int) (theObject.getPosition().x), (int) (theObject.getPosition().y));

                    // 2. do the actual rotation
                    at.rotate(rotateAngle);

                    // 3. just a scale because this image is big
                    at.scale(rescalingX, rescalingY);

                    // 4. translate the object so that you rotate it around the
                    //    center (easier :))
                    at.translate(-bf.getWidth() / 2, -bf.getHeight() / 2);

                    g.drawImage(bf, at, null);
                } else if (obj instanceof WheelObject && bf != null) {
                    WheelObject theObject = (WheelObject) obj;

                    double theObjectsWid = theObject.getRadius() * 2;
                    double theObjectsHei = theObject.getRadius() * 2;

                    double rescalingX = theObjectsWid / bf.getWidth();
                    double rescalingY = theObjectsHei / bf.getHeight();

                    double rotateAngle = theObject.getFi();

                    AffineTransform at = new AffineTransform();

                    // 1. translate it to the center of the component
                    at.translate((int) (theObject.getPosition().x), (int) (theObject.getPosition().y));

                    // 2. just a scale because this image is big
                    at.scale(rescalingX, rescalingY);

                    // 3. do the actual rotation
                    at.rotate(rotateAngle);

                    // 4. translate the object so that you rotate it around the
                    //    center (easier :))
                    at.translate(-bf.getWidth() / 2, -bf.getHeight() / 2);

                    g.drawImage(bf, at, null);
                } else if (obj instanceof CircularObject && bf != null) {
                    CircularObject theObject = (CircularObject) obj;

                    double theObjectsWid = theObject.getRadius() * 2;
                    double theObjectsHei = theObject.getRadius() * 2;

                    double rescalingX = theObjectsWid / bf.getWidth();
                    double rescalingY = theObjectsHei / bf.getHeight();

                    AffineTransform at = new AffineTransform();

                    // 1. translate it to the center of the component
                    at.translate((int) (theObject.getPosition().x), (int) (theObject.getPosition().y));

                    // 2. just a scale because this image is big
                    at.scale(rescalingX, rescalingY);

                    // 3. translate the object so that you rotate it around the
                    //    center (easier :))
                    at.translate(-bf.getWidth() / 2, -bf.getHeight() / 2);

                    g.drawImage(bf, at, null);
                } else if (obj instanceof SpringConstraint && bf != null) {
                    RectangularObject theObject = ((SpringConstraint) obj).getCollisionRect();

                    double theObjectsWid = (Double) (theObject.getExtents().get(0)) * 2;
                    double theObjectsHei = (Double) (theObject.getExtents().get(1)) * 2;

                    double rescalingX = theObjectsWid / bf.getWidth();
                    double rescalingY = theObjectsHei / bf.getHeight();

                    double rotateAngle = theObject.getRotation();

                    AffineTransform at = new AffineTransform();

                    // 1. translate it to the center of the component
                    at.translate((int) (theObject.getPosition().x), (int) (theObject.getPosition().y));

                    // 2. do the actual rotation
                    at.rotate(rotateAngle);

                    // 3. just a scale because this image is big
                    at.scale(rescalingX, rescalingY);

                    // 4. translate the object so that you rotate it around the
                    //    center (easier :))
                    at.translate(-bf.getWidth() / 2, -bf.getHeight() / 2);

                    g.drawImage(bf, at, null);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.err.println("Error Type 2 Was Handled");
        }
    }

    public void paintFps(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 12));
        if (usedTime > 0)
            g.drawString(String.valueOf(1000 / usedTime) + " fps", getScreenWidth() - 50, getPlayHeight());
        else
            g.drawString("--- fps", getScreenWidth() - 50, getPlayHeight());
    }

    public static void updateWorld() {
        step();
        updateRotatingObjects();
    }

    private static void updateRotatingObjects() {
        for (Object obj : rotatingObjects) {
            ((RectangularObject) obj).setRotation(((RectangularObject) obj).getRotation() + ((RectangularObject) obj).getRotationSpeed());
        }
    }

    public static void addRotatingObject(Object2D obj, double rotationSpeed) {
        obj.setRotationSpeed(rotationSpeed);
        rotatingObjects.add(obj);
    }

    public void createLeftWall(double elasticity, double friction) {
        RectangularObject wall = new RectangularObject(-200, getScreenHeight() / 2, 400, getScreenHeight(), 0, true, 100, elasticity, friction);
        wall.setType(ObjectTypes.GROUND);
        wall.setCollidable(true);
        addObject(wall);
    }

    public void createRightWall(double elasticity, double friction) {
        RectangularObject wall = new RectangularObject(getScreenWidth() + 199, getScreenHeight() / 2, 400, getScreenHeight(), 0, true, 100, elasticity, friction);
        wall.setType(ObjectTypes.GROUND);
        wall.setCollidable(true);
        addObject(wall);
    }

    public void createUpWall(double elasticity, double friction) {
        RectangularObject wall = new RectangularObject(getScreenWidth() / 2, -200, getScreenWidth(), 400, 0, true, 100, elasticity, friction);
        wall.setType(ObjectTypes.GROUND);
        wall.setCollidable(true);
        addObject(wall);
    }

    public void createDownWall(double elasticity, double friction) {
        RectangularObject wall = new RectangularObject(getScreenWidth() / 2, getScreenHeight() + 171, getScreenWidth(), 400, 0, true, 100, elasticity, friction);
        wall.setType(ObjectTypes.GROUND);
        wall.setCollidable(true);
        addObject(wall);
    }

    public void createLeftWall(double elasticity, double friction, File image) {
        RectangularObject wall = new RectangularObject(-200, getScreenHeight() / 2, 400, getScreenHeight(), 0, true, 100, elasticity, friction);
        wall.setType(ObjectTypes.GROUND);
        wall.setImage(image);
        wall.setCollidable(true);
        addObject(wall);
    }

    public void createRightWall(double elasticity, double friction, File image) {
        RectangularObject wall = new RectangularObject(getScreenWidth() + 199, getScreenHeight() / 2, 400, getScreenHeight(), 0, true, 100, elasticity, friction);
        wall.setType(ObjectTypes.GROUND);
        wall.setImage(image);
        wall.setCollidable(true);
        addObject(wall);
    }

    public void createUpWall(double elasticity, double friction, File image) {
        RectangularObject wall = new RectangularObject(getScreenWidth() / 2, -200, getScreenWidth(), 400, 0, true, 100, elasticity, friction);
        wall.setType(ObjectTypes.GROUND);
        wall.setImage(image);
        wall.setCollidable(true);
        addObject(wall);
    }

    public void createDownWall(double elasticity, double friction, File image) {
        RectangularObject wall = new RectangularObject(getScreenWidth() / 2, getScreenHeight() + 171, getScreenWidth(), 400, 0, true, 100, elasticity, friction);
        wall.setType(ObjectTypes.GROUND);
        wall.setImage(image);
        wall.setCollidable(true);
        addObject(wall);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        World.screenHeight = screenHeight;
    }

    public static int getPlayHeight() {
        return playHeight;
    }

    public void setPlayHeight(int playHeight) {
        World.playHeight = playHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        World.screenWidth = screenWidth;
    }

    public Timer getClock() {
        return clock;
    }

    public void setClock(Timer clock) {
        this.clock = clock;
    }

    public static void moveObjectsOutOfTheWorld() {
        for (Object obj : objects) {
            paintQueue.remove(obj);
        }
        for (Object obj : constraints) {
            paintQueue.remove(obj);
        }
    }

    public static void clearWorldObjects() {
        objects.clear();
        rotatingObjects.clear();
        paintQueue.clear();
        constraints.clear();
    }

    public static int getImpactResponseMode() {
        return impactResponseMode;
    }

    public static void setImpactResponseMode(int impactResponseMode) {
        World.impactResponseMode = impactResponseMode;
    }

    public static void addCollisionListener(CollisionListener listener) {
        cd.addCollisionEventListener(listener);
    }

    public void setBackground(File src) {
        try {
            imageSrc = (src.toURI()).toURL();
        } catch (MalformedURLException ignored) {
        }

        try {
            background = ImageIO.read(imageSrc);
        } catch (IOException e) {
            System.err.println("Error Type 4 Handled");
        }
    }

    public boolean hasBackground() {
        if (background == null) {
            return false;
        } else {
            return true;
        }
    }

    public BufferedImage getBackground() {
        return background;
    }

}