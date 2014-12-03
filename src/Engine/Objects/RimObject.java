package Engine.Objects;

import Engine.Utilities.Vector2D;
import Engine.World.World;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 1/17/12
 * Time: 1:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class RimObject {

    public Vector2D curr;
    public Vector2D prev;

    private double wr;
    private double av;
    private double sp;
    private double maxTorque;

    /**
     * The RimObject is really just a second component of the wheel model.
     * The rim object is simulated in a coordsystem relative to the wheel's
     * center, not in worldspace.
     * <p/>
     * @param r Something
     * @param mt Something Else
     */
    public RimObject(double r, double mt) {

        curr = new Vector2D(r, 0);
        prev = new Vector2D(0, 0);

        sp = 0;
        av = 0;

        maxTorque = mt;
        wr = r;
    }

    public double getSpeed() {
        return sp;
    }

    public void setSpeed(double s) {
        sp = s;
    }

    public double getAngularVelocity() {
        return av;
    }

    public void setAngularVelocity(double s) {
        av = s;
    }

    /**
     * Origins of this code are from Raigan Burns, Metanet Software
     * @param dt aa
     */
    public void update(double dt) {

        // USE Vector2D METHODS HERE
        //clamp torques to valid range
        sp = Math.max(-maxTorque, Math.min(maxTorque, sp + av));

        //apply torque
        //this is the tangent Vector2D at the rim object
        double dx = -curr.y;
        double dy = curr.x;

        //normalize so we can scale by the rotational speed
        double len = Math.sqrt(dx * dx + dy * dy);
        dx /= len;
        dy /= len;

        curr.x += sp * dx;
        curr.y += sp * dy;

        double ox = prev.x;
        double oy = prev.y;
        double px = prev.x = curr.x;
        double py = prev.y = curr.y;

        curr.x += World.getDamping() * (px - ox);
        curr.y += World.getDamping() * (py - oy);

        // hold the rim object in place
        double clen = Math.sqrt(curr.x * curr.x + curr.y * curr.y);
        double diff = (clen - wr) / clen;

        curr.x -= curr.x * diff;
        curr.y -= curr.y * diff;
    }

}
