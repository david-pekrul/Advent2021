package day9;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cell {
    public int height;
    public int row;
    public int col;
    public boolean visited;

    public Cell(int r, int c, int h){
        height = h;
        row = r;
        col = c;
        visited = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return height == cell.height && row == cell.row && col == cell.col && visited == cell.visited;
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, row, col, visited);
    }
}
