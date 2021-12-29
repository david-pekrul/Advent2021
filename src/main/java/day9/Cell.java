package day9;

import java.util.HashSet;
import java.util.Set;

public class Cell {
    public int height;
    public int row;
    public int col;
    public Set<Cell> neighbors;

    public Cell(int r, int c, int h){
        height = h;
        row = r;
        col = c;
        neighbors = new HashSet<>();
    }
}
