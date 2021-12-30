package day12;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cave {
    public final String id;
    public final boolean isBigCave;
    public final boolean isSmallCave;
    public Set<Cave> neighbors;

    public Cave(String id) {
        this.id = id;
        this.isBigCave = (id.equals(id.toUpperCase()));
        this.isSmallCave = !isBigCave;
        neighbors = new HashSet<>();
    }

    public boolean isStart() {
        return "start".equalsIgnoreCase(id);
    }

    public boolean isEnd() {
        return "end".equalsIgnoreCase(id);
    }

    public void addNeighbor(Cave other){
        this.neighbors.add(other);
        other.neighbors.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return Objects.equals(id, cave.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cave{" +
                "id='" + id + '\'' +
                ", neighbors=" + Arrays.toString(neighbors.stream().map(x -> x.id).toArray()) +
                '}';
    }
}
