package day9;

import java.sql.Array;
import java.util.*;

public class HeightMap {
    List<List<Cell>> cells;
    int rows;
    int cols;

    private HeightMap(List<List<Cell>> cells) {
        this.cells = cells;
        rows = cells.size();
        cols = cells.get(0).size();
    }

    public List<Cell> getLowPoints() {
        //find the low points
        List<Cell> horizontalLowPoints = new ArrayList<>();
        for (List<Cell> cellRow : cells) {
            for (int i = 0; i < cellRow.size(); i++) {
                if (i > 0 && cellRow.get(i).height >= cellRow.get(i - 1).height) {
                    //check left
                    continue;
                }
                if (i < cellRow.size() - 1 && cellRow.get(i).height > cellRow.get(i + 1).height) {
                    continue;
                }
                horizontalLowPoints.add(cellRow.get(i));
            }
        }

        List<Cell> lowPoints = new ArrayList<>();
        for (Cell cell : horizontalLowPoints) {
            if (cell.row > 0 && cell.height >= cells.get(cell.row - 1).get(cell.col).height) {
                continue;
            }
            if (cell.row < rows - 1 && cell.height >= cells.get(cell.row + 1).get(cell.col).height) {
                continue;
            }
            lowPoints.add(cell);
        }

        return lowPoints;
    }

    public List<Set<Cell>> getBasins() {
        List<Cell> lowPoints = getLowPoints();

        List<Set<Cell>> basins = new ArrayList<>();
        for (Cell lowPoint : lowPoints) {
            Set<Cell> basin = new HashSet<>();
            Stack<Cell> stack = new Stack<>();
            stack.push(lowPoint);
            basin.add(lowPoint);
            while (!stack.isEmpty()) {
                Cell next = stack.pop();
                if (next.visited) {
                    continue;
                }
                //get the neighbors
                if (next.row > 0) {
                    //above
                    Cell neighbor = cells.get(next.row - 1).get(next.col);
                    addToBasin(basin, stack, neighbor);
                }
                if (next.row < rows - 1) {
                    //below
                    Cell neighbor = cells.get(next.row + 1).get(next.col);
                    addToBasin(basin, stack, neighbor);
                }
                if (next.col > 0) {
                    //left
                    Cell neighbor = cells.get(next.row).get(next.col - 1);
                    addToBasin(basin, stack, neighbor);
                }
                if (next.col < cols - 1) {
                    //right
                    Cell neighbor = cells.get(next.row).get(next.col + 1);
                    addToBasin(basin, stack, neighbor);
                }

                //Set the `visited` value to true once we've added all if this cell's neighbors.
                next.visited = true;
            }

            basins.add(basin);
        }


        return basins;
    }

    public void addToBasin(Set<Cell> basin, Stack<Cell> stack, Cell cell) {
        if (cell.height != 9 && !cell.visited) {
            stack.add(cell);
            basin.add(cell);
        }
    }


    public static class Builder {
        String rawInput;

        public Builder setInput(String rawInput) {
            this.rawInput = rawInput;
            return this;
        }

        public HeightMap build() {
            Scanner scanner = new Scanner(rawInput);
            List<List<Cell>> cellList = new ArrayList<>();
            int row = 0;
            int col = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                List<Cell> rowCells = new ArrayList<>();
                col = 0;
                for (Character c : line.toCharArray()) {
                    rowCells.add(new Cell(row, col, Integer.parseInt(c.toString())));
                    col++;
                }
                cellList.add(rowCells);
                row++;
            }


            return new HeightMap(cellList);
        }
    }
}
