package day11;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Octopus {
    public boolean flashed;
    public short powerLevel;
    short row;
    short col;

    int flashes;

    Set<Octopus> neighbors;

    public Octopus(short r, short c, short p) {
        powerLevel = p;
        row = r;
        col = c;
        flashed = false;
        neighbors = new HashSet<>();
        flashes = 0;
    }

    public void increase() {
        powerLevel++;
        if (powerLevel > 9 && !flashed) {
            flashes = flashes + 1;
            flashed = true;
            //Flash for this octopi
            for (Octopus neighbor : neighbors) {
                neighbor.increase();
            }
        }
    }

    public void makeNeighbors(Octopus other) {
        if (this.equals(other)) {
            return;
        }
        this.neighbors.add(other);
        other.neighbors.add(this);
    }

    @Override
    public String toString() {
        return powerLevel + (powerLevel == 0?"*":" ");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Octopus octopus = (Octopus) o;
        return row == octopus.row && col == octopus.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
