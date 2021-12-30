package day11;

import java.util.*;

public class OctopiGrid {
    List<List<Octopus>> grid;
    Set<Octopus> allOctopi;
    int day;

    private OctopiGrid(List<List<Octopus>> g) {
        day = 0;
        this.grid = g;
        allOctopi = new HashSet<>();
        for (List<Octopus> l : g) {
            allOctopi.addAll(l);
        }
    }

    public void processDay() {
        for (Octopus o : allOctopi) {
            o.increase();
        }
        for (Octopus o : allOctopi) {
            if (o.powerLevel > 9) {
                o.powerLevel = 0;
            }
            o.flashed = false;
        }
        day++;
    }

    public int countFlashes(){
        int count = 0;
        for(Octopus o : allOctopi){
            count += o.flashes;
        }
        return count;
    }

    public boolean allFlash(){
        return allOctopi.stream().allMatch(o -> o.powerLevel == 0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OctopiGrid[day:" + day + "]\r\n");
        for (List<Octopus> row : grid) {
            sb.append(Arrays.toString(row.toArray()));
            sb.append("\r\n");
        }
        sb.append("flashes: ");
        sb.append(countFlashes());
        sb.append("\r\n");
        return sb.toString();

    }

    public static class Builder {
        private String input;

        public Builder setInput(String rawInput) {
            this.input = rawInput;
            return this;
        }

        public OctopiGrid build() {
            Scanner scanner = new Scanner(input);
            short row = 0;
            List<List<Octopus>> listGrid = new ArrayList<>();

            while (scanner.hasNext()) {
                char[] line = scanner.nextLine().toCharArray();
                List<Octopus> oRow = new ArrayList<>();
                for (short col = 0; col < line.length; col++) {
                    Octopus o = new Octopus(row, col, Short.valueOf("" + line[col]));
                    oRow.add(o);
                }
                listGrid.add(oRow);
                row++;
            }

            // Link up neighbors
            int rows = listGrid.size();
            int cols = listGrid.get(0).size();

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Octopus current = listGrid.get(r).get(c);
                    List<Coordinate> neighborCoords = (new Coordinate(r, c)).getNeighbors(rows, cols);
                    for (Coordinate nc : neighborCoords) {
                        current.makeNeighbors(listGrid.get(nc.r).get(nc.c));
                    }
                }
            }
            return new OctopiGrid(listGrid);
        }


    }
}
