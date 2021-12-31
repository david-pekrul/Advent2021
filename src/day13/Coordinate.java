package day13;

import java.util.Objects;

public class Coordinate {
    public int r, c;

    public Coordinate(int r1, int c1) {
        this.r = r1;
        this.c = c1;
    }

    public Coordinate foldX(int x){
        return null;
    }

    public Coordinate foldY(int y){
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return r == that.r && c == that.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }
}
