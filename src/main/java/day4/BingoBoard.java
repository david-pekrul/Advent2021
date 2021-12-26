package main.java.day4;

import java.util.*;

public class BingoBoard {

    private Cell[][] board;
    private Map<Integer, Cell> valueToCell;
    private boolean alreadyBingoed;

    private class Cell {
        public int value;
        public boolean used;
        public int row;
        public int col;

        public Cell(int value, int r, int c) {
            this.value = value;
            this.used = false;
            row = r;
            col = c;

        }

        @Override
        public String toString() {
            return "[" + value + ";" + (used ? 'y' : 'n') + ']';
        }
    }

    public BingoBoard(String rawInput) {
        valueToCell = new HashMap<>(25);
        parseBoard(rawInput);
        alreadyBingoed = false;
    }

    /**
     * Takes in the next called number and plays the board.
     * @param value the next called number
     * @return true if a row or column is completed, false if the board does not have a Bingo.
     */
    public boolean playValue(int value) {
        if(!valueToCell.containsKey(value)){
            // the board does not have the value, so no progress on this board is made.
            return false;
        }

        Cell playedCell = valueToCell.get(value);
        playedCell.used = true;

        int playedCol = playedCell.col;
        int playedRow = playedCell.row;

        boolean columnBingo = true;
        boolean rowBingo = true;
        for(int i = 0; i < 5; i++){
            if(columnBingo && !board[playedRow][i].used){
                columnBingo = false;
            }
            if(rowBingo && !board[i][playedCol].used){
                rowBingo = false;
            }

            if(!rowBingo && !columnBingo) {
                //both row and column are not bingos, so we can terminate early.
                return false;
            }
        }

        //made it through a full row or column without finding an unused value.
        alreadyBingoed = true;
        return true;
    }

    public int sumUnusedCells() {
        return valueToCell.values().stream().filter(x -> !x.used).reduce(0, (acc,next) -> acc + next.value, Integer::sum);
    }

    public void reset(){
        for(Cell c :valueToCell.values()){
            c.used = false;
        }
    }

    private void parseBoard(String rawInput) {

        this.board = new Cell[5][5];
        List<Cell[]> listBoard = new ArrayList<>(5);
        String[] rawLines = rawInput.split("\n");
        for (int row = 0; row < rawLines.length; row++) {
            String rawLine = rawLines[row];
            String[] rawValues = rawLine.trim().split("\\s+");
            for (int col = 0; col < rawValues.length; col++) {
                int val = Integer.parseInt(rawValues[col]);
                Cell cell = new Cell(val, row, col);
                board[row][col] = cell;
                valueToCell.put(val, cell);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : board) {
            sb.append(Arrays.toString(row));
            sb.append("\r\n");
        }
        return sb.toString();
    }

}
