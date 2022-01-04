package day15;

import java.util.*;

public class Cell {
    public int cost;
    public int row;
    public int col;
    public boolean visited;

    public Set<Cell> neighbors;

    public Cell(int r, int c, int v){
        cost = v;
        row = r;
        col = c;
        visited = false;
        neighbors = new HashSet<>();
    }

    public void addNeighbor(Cell c){
        this.neighbors.add(c);
        c.neighbors.add(this);
    }

    public List<Cell> sortedNeighbors(){
        List<Cell> n = neighbors.stream().toList();
        n.sort(Comparator.comparingInt(a -> a.cost));
        return n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return  row == cell.row && col == cell.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", col=" + col +
                ", cost=" + cost +
                '}';
    }
}
