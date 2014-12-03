package Engine.Constraint;

import Engine.World.World;

import java.awt.Graphics2D;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 1/17/12
 * Time: 1:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractConstraint {

    protected Graphics2D dc;
    private boolean _visible;
    private double _stiffness;

    public AbstractConstraint(double stiffness) {
        _visible = true;
        _stiffness = stiffness;
    }

    /**
     * The stiffness of the constraint. Higher values result in result in
     * stiffer constraints. Values should be greater than 0 and less than or
     * equal to 1. Depending on the situation, setting constraints to very high
     * values may result in instability or unwanted energy.
     * @return Returns Stiffness
     */
    public double getStiffness() {
        return _stiffness;
    }

    public void setStiffness(double s) {
        _stiffness = s;
    }

    /**
     * The visibility of the constraint. This is only implemented for the default painting
     * methods of the constraints. When you create your painting methods in subclassed constraints
     * or composites you should add a check for this property.
     * @return If It's Visible
     */
    public boolean getVisible() {
        return _visible;
    }

    public void setVisible(boolean v) {
        _visible = v;
    }

    public void resolve() {
    }

    protected Graphics2D getDefaultContainer() {
        if (World.getDefaultContainer() == null) {
            String err = "";
            err += "You must set the defaultContainer property of the World class ";
            err += "if you wish to use the default paint methods of the constraints";
            throw new Error(err);
        }
        Graphics2D parentContainer = World.getDefaultContainer();
        return parentContainer;
    }

}
