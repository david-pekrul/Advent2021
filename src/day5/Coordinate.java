package day5;

import java.util.Objects;

public class Coordinate {
    public int x, y;

    public Coordinate(int x1, int y1){
        this.x = x1;
        this.y = y1;
    }

    public boolean isVerticalWith(Coordinate c2){
        return this.y == c2.y;
    }

    public boolean isHorizontalWith(Coordinate c2){
        return this.x == c2.x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
