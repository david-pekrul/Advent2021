package day5;

import java.util.ArrayList;
import java.util.List;

public class VentLine {
    private final Coordinate c1,c2;

    private VentLine(Coordinate c1, Coordinate c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public int getMaxX(){
        return Math.max(c1.x,c2.x);
    }
    public int getMaxY(){
        return Math.max(c1.y,c2.y);
    }

    public boolean isHorizontalOrVertical(){
        return c1.isHorizontalWith(c2) || c1.isVerticalWith(c2);
    }

    public List<Coordinate> getAllCoordinates(){
        List<Coordinate> coords = new ArrayList<>();
        Coordinate next = c1;
        CoordinateStep currentCoordinate = new CoordinateStep(c1, c2);
        while(!next.equals(c2)){
            coords.add(next);
            //get the next coordinate by applying the Step to the previous coordinate
            next = currentCoordinate.apply(next);
        }
        coords.add(c2);

        return coords;
    }

    public static class VentLineBuilder {

        private String rawInput;
        private int x1, y1, x2, y2;

        public VentLine build() {
            assert (rawInput != null);
            String[] coords = rawInput.split("->");
            String[] x1y1 = coords[0].trim().split(",");
            String[] x2y2 = coords[1].trim().split(",");
            this.x1 = Integer.parseInt(x1y1[0]);
            this.y1 = Integer.parseInt(x1y1[1]);
            this.x2 = Integer.parseInt(x2y2[0]);
            this.y2 = Integer.parseInt(x2y2[1]);

            return new VentLine(new Coordinate(x1,y1), new Coordinate(x2,y2));
        }

        public VentLineBuilder setInput(String rawInput) {
            this.rawInput = rawInput;
            return this;
        }
    }
}
