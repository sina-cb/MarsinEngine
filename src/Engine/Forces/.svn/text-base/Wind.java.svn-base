package Engine.Forces;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 10/22/11
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class Wind extends Force {

    /**
     * This Will Create a Force Object as Wind with the power and angle passed to the constructor
     *
     * @param power Power of the Wind
     * @param angle Angle og the Wind
     */
    public Wind(double power, double angle) {
        this.forceX = Math.cos(Math.PI * angle / 180) * power;
        this.forceY = -Math.sin(Math.PI * angle / 180) * power;
    }
}
