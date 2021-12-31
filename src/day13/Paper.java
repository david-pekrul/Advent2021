package day13;

import java.util.*;

public class Paper {
    Set<Coordinate> points;
    List<Fold> remainingFolds;


    public Paper(String rawInput) {

        String line = "";
        points = new HashSet<>();
        remainingFolds = new ArrayList<>();

        Scanner scanner = new Scanner(rawInput);
        while (scanner.hasNext()) {
            line = scanner.nextLine();
            if (line.isBlank()) {
                //done with points
                break;
            }

            String[] split = line.split(",");
            points.add(new Coordinate(Integer.parseInt(split[1]), Integer.parseInt(split[0])));
        }

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            line = line.replace("fold along", "").trim();
            String[] split = line.split("=");
            remainingFolds.add(new Fold(Integer.parseInt(split[1]), "y".equalsIgnoreCase(split[0])));
        }

    }

    private Paper(Set<Coordinate> p, List<Fold> rf){
        this.points = p;
        this.remainingFolds = rf;
    }

    public boolean hasMoveFolds(){
        return !remainingFolds.isEmpty();
    }

    public int visiblePoints(){
        return points.size();
    }

    public Paper applyNextFold() {

        Fold fold = remainingFolds.remove(0);

        Set<Coordinate> newPoints = new HashSet<>();
        for(Coordinate c : points){
            fold.apply(c).ifPresent(newPoints::add);
        }

        return new Paper(newPoints,remainingFolds);
    }

    private Coordinate getDimensions() {
        int maxRows = 0;
        int maxCols = 0;
        for (Coordinate c : points) {
            if (c.r > maxRows) {
                maxRows = c.r;
            }
            if (c.c > maxCols) {
                maxCols = c.c;
            }
        }

        return new Coordinate(maxRows + 1, maxCols + 1);
    }

    @Override
    public String toString() {
        Coordinate dimaneions = getDimensions();
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < dimaneions.r; r++) {
            for (int c = 0; c < dimaneions.c; c++) {
                if (points.contains(new Coordinate(r, c))) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\r\n");
        }

        return sb.toString();
    }
}
