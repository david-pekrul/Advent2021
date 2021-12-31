package day13;

import java.util.Optional;

public class Fold {
    public int foldLocation;
    public boolean isHorizontal; //make this an enum?
    public boolean isVertical;

    public Fold(int v, boolean isH) {
        foldLocation = v;
        isHorizontal = isH;
        isVertical = !isH;
    }

    public Optional<Coordinate> apply(Coordinate in) {
        if (isHorizontal && in.r == foldLocation) {
            return Optional.empty();
        }

        if (isVertical && in.c == foldLocation) {
            return Optional.empty();
        }

        if ((isHorizontal && in.r < foldLocation) || (isVertical && in.c < foldLocation)) {
            //the point is not affected by the fold
            return Optional.of(in);
        }

        //the point is modified by the fold
        if(isHorizontal){
            int diff = Math.abs(in.r - foldLocation);
            return Optional.of(new Coordinate(foldLocation - diff, in.c));
        }

        //is vertical
        int diff = Math.abs(in.c - foldLocation);
        return Optional.of(new Coordinate(in.r, foldLocation - diff));
    }

    @Override
    public String toString() {
        return "Fold{" +
                "foldLocation=" + foldLocation +
                ", isHorizontal=" + isHorizontal +
                ", isVertical=" + isVertical +
                '}';
    }
}
