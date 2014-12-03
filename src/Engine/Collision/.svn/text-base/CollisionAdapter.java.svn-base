package Engine.Collision;

import Engine.Objects.Object2D;
import Engine.Utilities.Vector2D;
import Engine.World.World;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 12/1/11
 * Time: 10:36 PM
 */

@SuppressWarnings("unchecked")
public final class CollisionAdapter {

    public static void resolveCollisionObjectVsObject(Object2D objA, Object2D objB, Vector2D normal, double depth) {

        Vector2D mtd = normal.mult(depth);
        double totalElasticity = objA.getElasticity() + objB.getElasticity();

        // the total friction in a collision is combined but brought to [0,1]
        double totalFriction = 1 - (objA.getFriction() + objB.getFriction());
        if (totalFriction > 1)
            totalFriction = 1;
        else if (totalFriction < 0)
            totalFriction = 0;

        // get the total mass, and assign giant mass to fixed objects
        double massA = (objA.getFixed()) ? 100000 : objA.getMass();
        double massB = (objB.getFixed()) ? 100000 : objB.getMass();
        double totalMass = massA + massB;

        // get the collision components, vn and vt
        Collision colA = objA.getComponents(normal);
        Collision colB = objB.getComponents(normal);

        Vector2D vnA = null;
        Vector2D vnB = null;

        // calculate the coefficient of restitution based on the mass
        if (World.getImpactResponseMode() == World.OPTIMIZED) {
            vnA = (colB.vn.mult((totalElasticity + 1) * massB).plus(colA.vn.mult(massA - totalElasticity * massB))).divEquals(totalMass);
            vnB = (colA.vn.mult((totalElasticity + 1) * massA).plus(colB.vn.mult(massB - totalElasticity * massA))).divEquals(totalMass);
        }else if (World.getImpactResponseMode() == World.STANDARD) {
            vnA = (objB.getVelocity().mult((totalElasticity + 1) * massB).plus(objA.getVelocity().mult(massA - totalElasticity * massB))).divEquals(totalMass);
            vnB = (objA.getVelocity().mult((totalElasticity + 1) * massA).plus(objB.getVelocity().mult(massB - totalElasticity * massA))).divEquals(totalMass);
        }
        colA.vt.multEquals(totalFriction);
        colB.vt.multEquals(totalFriction);

        // scale the mtd by the ratio of the masses. heavier objects move less
        Vector2D mtdA = mtd.mult(massB / totalMass);
        Vector2D mtdB = mtd.mult(-massA / totalMass);

        if (!objA.getFixed())
            objA.resolveCollision(mtdA, vnA.plusEquals(colA.vt), normal, depth, -1);
        if (!objB.getFixed())
            objB.resolveCollision(mtdB, vnB.plusEquals(colB.vt), normal, depth, 1);
    }

}
