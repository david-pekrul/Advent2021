package day9;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HeightMap {
    List<List<Cell>> cells;
    int rows;
    int cols;

    private HeightMap(List<List<Cell>> cells) {
        this.cells = cells;
        rows = cells.size();
        cols = cells.get(0).size();
    }

    public List<Cell> getLowPoints(){
        //find the low points
        List<Cell> horizontalLowPoints = new ArrayList<>();
        for (List<Cell> cellRow : cells) {
            for (int i = 0; i < cellRow.size(); i++) {
                if(i > 0 && cellRow.get(i).height >= cellRow.get(i-1).height){
                    //check left
                    continue;
                }
                if(i < cellRow.size()-1 && cellRow.get(i).height > cellRow.get(i+1).height){
                    continue;
                }
                horizontalLowPoints.add(cellRow.get(i));
            }
        }

        List<Cell> lowPoints = new ArrayList<>();
        for(Cell cell : horizontalLowPoints){
            if(cell.row > 0 && cell.height >= cells.get(cell.row-1).get(cell.col).height){
                continue;
            }
            if(cell.row < rows -1 && cell.height >= cells.get(cell.row+1).get(cell.col).height){
                continue;
            }
            lowPoints.add(cell);
        }

        return lowPoints;
    }

    public static class Builder {
        String rawInput;
        public Builder setInput(String rawInput){
            this.rawInput = rawInput;
            return this;
        }

        public HeightMap build(){
            Scanner scanner = new Scanner(rawInput);
            List<List<Cell>> cellList = new ArrayList<>();
            int row = 0;
            int col = 0;
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                List<Cell> rowCells = new ArrayList<>();
                col = 0;
                for(Character c : line.toCharArray()){
                    rowCells.add(new Cell(row,col,Integer.parseInt(c.toString())));
                    col++;
                }
                cellList.add(rowCells);
                row++;
            }


            return new HeightMap(cellList);
        }
    }
}
