package Engine.Forces;

/**
 * Created by IntelliJ IDEA.
 * User: Sina Solaimanpour
 * Date: 10/18/11
 * Time: 10:35 PM
 */
public abstract class Force {

    double forceX;
    double forceY;

    /**
     * @return Returns Force Value on X Axis
     */
    public double getForceX() {
        return forceX;
    }

    /**
     * @param forceX Set The Force Value on X Axis
     */
    public void setForceX(double forceX) {
        this.forceX = forceX;
    }

    /**
     * @return Returns Force Value on Y Axis
     */
    public double getForceY() {
        return forceY;
    }

    /**
     * @param forceX Set The Force Value on Y Axis
     */
    public void setForceY(double forceY) {
        this.forceY = forceY;
    }
}
