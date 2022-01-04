package day15;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Coordinate {
    public int r, c;

    public Coordinate(int r1, int c1) {
        this.r = r1;
        this.c = c1;
    }

    public List<Coordinate> getNeighbors(int maxRow, int maxCol) {
        List<Coordinate> neighbors = new LinkedList<>();
        if (r > 0) {
            //there are neighbors above
//            if (c > 0) {
//                //neighbor to the top-left
//                neighbors.add(new Coordinate(r - 1, c - 1));
//            }
            //neighbor directly above
            neighbors.add(new Coordinate(r - 1, c));
//            if (c < maxCol - 1) {
//                //neighbor to the top-right
//                neighbors.add(new Coordinate(r - 1, c + 1));
//            }
        }

        if(c > 0){
            //neighbor to the left
            neighbors.add(new Coordinate(r, c - 1));
        }
        if(c < maxCol-1){
            //neighbor to the right
            neighbors.add(new Coordinate(r, c + 1));
        }

        if (r < maxRow - 1) {
            //there are neighbors below
//            if (c > 0) {
//                //neighbor to the bottom-left
//                neighbors.add(new Coordinate(r + 1, c - 1));
//            }
            //neighbor directly below
            neighbors.add(new Coordinate(r + 1, c));
//            if (c < maxCol - 1) {
//                //neighbor to the bottom-right
//                neighbors.add(new Coordinate(r + 1, c + 1));
//            }
        }
        return neighbors;
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
