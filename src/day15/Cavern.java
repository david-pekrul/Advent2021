package day15;

import java.util.*;

public class Cavern {

    Set<Cell> cells;
    Cell start;
    Cell end;

    private Cavern(Set<Cell> cells, Cell start, Cell end) {
        this.cells = cells;
        this.start = start;
        this.end = end;
    }

    public static class Builder {
        String rawInput;
        Set<Cell> cells;
        Cell start;
        Cell end;

        public Builder setRawInput(String rawInput) {
            this.rawInput = rawInput;
            return this;
        }

        public Cavern build1() {
            Scanner scanner = new Scanner(rawInput);
            List<List<Cell>> risksList;
            risksList = new ArrayList<>();

            int r = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                List<Cell> row = new ArrayList<>();
                int c = 0;
                for (char value : line.toCharArray()) {
                    row.add(new Cell(r, c, Integer.parseInt("" + value)));
                    c++;
                }
                r++;
                risksList.add(row);
            }

            int rows = risksList.size();
            int cols = risksList.get(0).size();
            cells = new HashSet<>();

            for (r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Cell current = risksList.get(r).get(c);
                    List<Coordinate> neighborCoords = (new Coordinate(r, c)).getNeighbors(rows, cols);
                    for (Coordinate nc : neighborCoords) {
                        current.addNeighbor(risksList.get(nc.r).get(nc.c));
                    }
                    cells.add(current);
                }
            }
            start = risksList.get(0).get(0);
            end = risksList.get(rows - 1).get(cols - 1);

            return new Cavern(cells, start, end);
        }

        public Cavern build2(int repeats, String rawVerify) {
            Scanner scanner = new Scanner(rawInput);
            List<List<Integer>> risksList;
            risksList = new ArrayList<>();


            int r = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                List<Integer> row = new ArrayList<>();
                int c = 0;
                for (char value : line.toCharArray()) {
                    row.add(Integer.parseInt("" + value));
                    c++;
                }
                r++;
                risksList.add(row);
            }

            int rows = risksList.size();
            int cols = risksList.get(0).size();

            Cell[][] cellGrid = new Cell[rows * 5][cols * 5];


            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    Integer original = risksList.get(row).get(col);
                    for (int y = 0; y < repeats; y++) {
                        for (int x = 0; x < repeats; x++) {
                            int newCost = calcNewCost(original, y+x);
                            int newRow = row+(y*rows);
                            int newCol = col+(x*cols);
                            cellGrid[newRow][newCol] = new Cell(newRow, newCol, newCost);
                        }
                    }
                }
            }


            if (!rawVerify.isBlank()) {
                int x = 0;
                scanner = new Scanner(rawVerify);
                while (scanner.hasNext()) {
                    char[] line = scanner.nextLine().toCharArray();
                    for (int y = 0; y < line.length; y++) {
                        int verifyCost = Integer.parseInt("" + line[y]);
                        Integer computedCell = cellGrid[x][y].cost;
                        if (verifyCost != computedCell) {
                            System.out.println(String.format("Wrong at %d,%d; Expected %d, Got %d", x, y, verifyCost, computedCell));
                        }
                    }
                    x++;
                }
            }

            rows = rows * 5;
            cols = cols * 5;

            cells = new HashSet<>();

            for (r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Cell current = cellGrid[r][c];
                    List<Coordinate> neighborCoords = (new Coordinate(r, c)).getNeighbors(rows, cols);
                    for (Coordinate nc : neighborCoords) {
                        current.addNeighbor(cellGrid[nc.r][nc.c]);
                    }
                    cells.add(current);
                }
            }
            start = cellGrid[0][0];
            end = cellGrid[rows - 1][cols - 1];

            return new Cavern(cells, start, end);
        }
    }

    public int getLowestRiskScore() {

        Set<Cell> Q = new HashSet<>(cells);

        Map<Cell, Integer> dist = new HashMap<>();
        Map<Cell, Cell> prev = new HashMap<>();

        for (Cell c : cells) {
            dist.put(c, Integer.MAX_VALUE);
            //prev not updated because I can tell if it is not in there already instead of using null.
        }

        dist.put(start, 0);


        while (!Q.isEmpty()) {

            Cell current = cellWithMinDistance(Q, dist);
            Q.remove(current);

            for (Cell neighbor : current.neighbors) {
                if (!Q.contains(neighbor)) {
                    continue;
                }

                int alt = dist.get(current) + neighbor.cost;
                if (alt < dist.get(neighbor)) {
                    dist.put(neighbor, alt);
                    prev.put(neighbor, current);
                }
                //determine if this is a more efficeint route to the neighbor
            }
        }
        return dist.get(end);
    }

    private Cell cellWithMinDistance(Set<Cell> Q, Map<Cell, Integer> dist) {
        Cell answer = Q.stream().findAny().get();
        Integer minDist = dist.get(answer);
        for (Cell c : Q) {
            int cellDist = dist.get(c);
            if (cellDist < minDist) {
                minDist = cellDist;
                answer = c;
            }
        }
        return answer;
    }

    private static int calcNewCost(int original, int section) {
        int next = original;
        for (int i = 0; i < section; i++) {
            next++;
            if (next == 10) {
                next = 1;
            }
        }
        return next;
    }

    //    Map<Cell,Cell> cameFrom = new HashMap<>();
    public int aStarCost() {
        Set<Cell> openSet = new HashSet<>();
        openSet.add(start);

//        cameFrom = new HashMap<>();
        Map<Cell, Integer> gScore = new HashMap<>();
        Map<Cell, Integer> fScores = new HashMap<>();

        for (Cell c : cells) {
            gScore.put(c, Integer.MAX_VALUE);
            fScores.put(c, Integer.MAX_VALUE);
        }
        gScore.put(start, 0);
        fScores.put(start, heuristicCost(start));


        int printCount = 0;
        while (!openSet.isEmpty()) {
            //current = the node in openSet that has the lowest fScore value.
            Cell current = lowestFScore(openSet, fScores);

            if (current.equals(end)) {
                System.out.println();
                return gScore.get(end);
            }

            openSet.remove(current);
//            System.out.print(openSet.size() + ", ");
//            printCount++;
//            if (printCount > 30) {
//                System.out.println();
//                printCount = 0;
//            }

            for (Cell neighbor : current.neighbors) {
                int tentativeGScore = gScore.get(current) + neighbor.cost;
                if (tentativeGScore < gScore.get(neighbor)) {
//                    cameFrom.put(neighbor,current);
                    gScore.put(neighbor, tentativeGScore);
                    fScores.put(neighbor, tentativeGScore + heuristicCost(neighbor));
                    openSet.add(neighbor);
                }
            }
        }

        throw new RuntimeException("Failure");
    }

//    public List<Cell> reconstructPath(){
//        Cell current = end;
//        List<Cell> totalPath = new ArrayList<>();
//        totalPath.add(end);
//        while(cameFrom.containsKey(current)){
//            current = cameFrom.get(current);
//            totalPath.add(current);
//        }
//        return totalPath;
//    }

    private Cell lowestFScore(Set<Cell> openSet, Map<Cell, Integer> fScores) {
        Cell lowCell = openSet.stream().findAny().get();

//        Cell lowCell = fScores.keySet().stream().filter(x -> openSet.contains(x)).findAny().get();
        int lowestScore = fScores.get(lowCell);

        for (Cell c : openSet) {
            if (fScores.get(c) < lowestScore) {
                lowestScore = fScores.get(c);
                lowCell = c;
            }
        }

        return lowCell;
    }

    private int heuristicCost(Cell n) {
        int deltaX = end.col - n.col;
        int deltaY = end.row - n.row;
        /*
            Get the number of steps to the end.
            Multiply by 5, the average value of values for the grid.
        */
        int expectedCost = (deltaX + deltaY);
        return expectedCost;

    }
}
