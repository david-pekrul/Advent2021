package day12;

import java.util.*;

public class CavePath {
    public Queue<Cave> path;
    public Cave lastCave;
    boolean part2;
    Optional<Cave> doubledSmallCave;

    public CavePath(Cave start, boolean part2) {
        if (!start.isStart()) {
            throw new RuntimeException("Starting with not the start");
        }
        path = new LinkedList<>();
        path.add(start);
        lastCave = start;
        this.part2 = part2;
        doubledSmallCave = Optional.empty();
    }

    private CavePath(Queue<Cave> p, Cave l, Cave next, boolean part2, Optional<Cave> dupeCave) {
        this.path = new LinkedList(p);
        this.lastCave = l;
        this.path.add(next);
        this.lastCave = next;
        this.doubledSmallCave = dupeCave;
        this.part2 = part2;
    }

    public boolean isDone() {
        return lastCave.isEnd();
    }

    public List<CavePath> addCave(Cave next) {

        if (next.isStart()) {
            //We cannot add the start cave in the middle of a path.
            return List.of();
        }

        if (next.isBigCave) {
            //These are always allowed
            return List.of(fork(next, false));
        }


        if (!part2 && path.contains(next)) {
            //This is part 1 that does not allow duplicates of small caves.
            //the path already contains the small cave, so we cannot add it.
            return List.of();
        }

        if(!part2){
            return List.of(fork(next, false));
        }

        if (path.contains(next)) {
            if (doubledSmallCave.isEmpty()) {
                //this path does not have a duplicated small cave yet.
                //this needs to return the option with the current path being duplicated
                CavePath forkedWithDuplicate = fork(next, true); //does set this small cave as teh duplicate
                return List.of(forkedWithDuplicate);
            }
            //cannot add next cave.
            //It is a small cave, and the single duplicated cave has already been used.
            return List.of();
        }


        //duplicate has already been used
        //Can only add the next cave if the path does not already contain this nexts small cave
        return List.of(fork(next, false));

    }

    private CavePath fork(Cave next, boolean allowDupliate) {
        Optional<Cave> dupeCave = this.doubledSmallCave;
        if (allowDupliate) {
            assert(dupeCave.isEmpty() == true);
            dupeCave = Optional.of(next);
        }
        return new CavePath(this.path, this.lastCave, next, part2, dupeCave);
    }


    @Override
    public String toString() {
        return "CavePath{" +
                "path=" + Arrays.toString(path.stream().map(x -> x.id).toArray()) +
                '}';
    }

    public String toRawString() {
        StringBuilder sb = new StringBuilder();
        for (Cave c : path) {
            sb.append(c.id);
            sb.append(",");
        }
        String s = sb.toString();
        return s.substring(0, s.length() - 1);
    }
}
