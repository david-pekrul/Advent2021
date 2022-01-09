package day17;

import java.util.Objects;

public class Projectile {
    int dX;
    int dY;
    public int x;
    public int y;
    int drag;
    int steps;

    final int initialX;
    final int initialY;

    public Projectile(int dX, int dY) {
        x = 0;
        y = 0;
        initialY = dY;
        initialX = dX;
        this.dX = dX;
        this.dY = dY;
        steps = 0;

        drag = 0;
        if (dX > 0) {
            drag = -1;
        } else if (drag < 0) {
            drag = 1;
        }
    }

    public void step() {
        x += dX;
        y += dY;
        if (dX != 0) {
            dX += drag;
        }
        dY--;
        steps++;
    }

    public boolean hasHit(Target target) {
        return target.minX <= x && x <= target.maxX && target.minY <= y && y <= target.maxY;
    }

    public boolean canStillHit(Target target) {
        if (dX == 0 && x < target.minX) {
            // this projectile can't reach the minX of the target.
            return false;
        }
        if (x > target.maxX || y < target.minY) {
            // this projectile is either too far past the target in the X, or below the target in the Y
            return false;
        }
        return true;
    }

    public static int getMinXSpeed(int targetMinX) {

        /*
        targetMinX = (minX)(minX+1)/2
         */
        int minX = (int) Math.round(Math.sqrt(targetMinX * 2));
        return minX;
    }


    @Override
    public String toString() {
        return "Projectile{" +
                "dX=" + dX +
                ", dY=" + dY +
                ", x=" + x +
                ", y=" + y +
                ", drag=" + drag +
                ", initialX=" + initialX +
                ", initialY=" + initialY +
                ", steps=" + steps +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projectile that = (Projectile) o;
        return initialX == that.initialX && initialY == that.initialY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialX, initialY);
    }
}
