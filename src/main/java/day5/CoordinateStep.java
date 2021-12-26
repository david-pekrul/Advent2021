package main.java.day5;

public class CoordinateStep {
    public int deltaX, deltaY;

    public CoordinateStep(Coordinate c1, Coordinate c2){
        int totalX = c2.x - c1.x;
        int totalY = c2.y - c1.y;

        if (totalX == 0) {
            deltaX = 0;
        } else {
            deltaX = totalX / Math.abs(totalX);
        }
        if (totalY == 0) {
            deltaY = 0;
        } else {
            deltaY = totalY / Math.abs(totalY);
        }
    }

    public Coordinate apply(Coordinate prev){
        return new Coordinate(prev.x + deltaX, prev.y + deltaY);
    }
}
