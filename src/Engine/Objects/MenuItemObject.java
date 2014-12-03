package Engine.Objects;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 2/3/12
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class MenuItemObject extends RectangularObject {

    String text = "Menu Text";

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
    public MenuItemObject(double x, double y, double width, double height, double rotation, boolean fixed, double mass, double elasticity, double friction) {
        super(x, y, width, height, rotation, fixed, mass, elasticity, friction);
    }

    public void paint() {
        super.paint();

        int xShift = (int)((text.length() * 4) / 1.5);
        int yShift = -5;

        dc.drawString(text, (float) (this.getPosition().x - xShift), (float) (this.getPosition().y - yShift));
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
