package Engine.Utilities;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: 12/1/11
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double px, double py) {
        x = px;
        y = py;
    }

    public void setTo(double px, double py) {
        x = px;
        y = py;
    }

    public void copy(Vector2D v) {
        x = v.x;
        y = v.y;
    }

    public double dot(Vector2D v) {
        return x * v.x + y * v.y;
    }

    public double cross(Vector2D v) {
        return x * v.y - y * v.x;
    }

    public Vector2D plus(Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    public Vector2D plusEquals(Vector2D v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector2D minus(Vector2D v) {
        return new Vector2D(x - v.x, y - v.y);
    }

    public Vector2D minusEquals(Vector2D v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vector2D mult(double s) {
        return new Vector2D(x * s, y * s);
    }

    public Vector2D multEquals(double s) {
        x *= s;
        y *= s;
        return this;
    }

    public Vector2D times(Vector2D v) {
        return new Vector2D(x * v.x, y * v.y);
    }

    public Vector2D divEquals(double s) {
        if (s == 0) s = 0.0001;
        x /= s;
        y /= s;
        return this;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double distance(Vector2D v) {
        Vector2D delta = this.minus(v);
        return delta.magnitude();
    }

    public Vector2D normalize() {
        double m = magnitude();
        if (m == 0) m = 0.0001;
        return mult(1 / m);
    }

    public String toString() {
        return (x + " : " + y);
    }

}
