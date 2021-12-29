package day5;

import java.util.*;
import java.util.logging.Logger;

public class VentGrid {
    private List<VentLine> ventLines;
    private Map<Coordinate, Integer> heightMap;

    private VentGrid(List<VentLine> ventLines) {
        this.ventLines = ventLines;
        heightMap = new HashMap<>(ventLines.size());
    }

    public void process(boolean useDiagonals) {
        heightMap.clear();

        for (VentLine v : ventLines) {
            if (!useDiagonals && !v.isHorizontalOrVertical()) {
                continue;
            }
            List<Coordinate> allCoordinates = v.getAllCoordinates();
            for (Coordinate coord : allCoordinates) {
                int currentHeight = heightMap.getOrDefault(coord, 0);
                int newHeight = currentHeight + 1;
                heightMap.put(coord, newHeight);
            }
        }
    }

    public int countOverlaps(){
        return (int)heightMap.values().stream().filter(v -> v > 1).count();
    }



    public static class Builder {
        private String input;

        public Builder setInput(String s) {
            input = s;
            return this;
        }

        public VentGrid build() {
            return new VentGrid(parseLines());
        }

        private List<VentLine> parseLines() {
            Scanner scanner = new Scanner(input);
            List<VentLine> lines = new ArrayList<>();
            while (scanner.hasNext()) {
                String rawLine = scanner.nextLine();
                lines.add(new VentLine.VentLineBuilder().setInput(rawLine).build());
            }
            return lines;
        }
    }

}
