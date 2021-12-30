package day12;

import java.util.*;

public class CavePath {
    public Queue<Cave> path;
    public Cave lastCave;
    boolean part2;

    Map<Cave, Integer> caveCount;

    public CavePath(Cave start, boolean part2) {
        if (!start.isStart()) {
            throw new RuntimeException("Starting with not the start");
        }
        path = new LinkedList<>();
        path.add(start);
        lastCave = start;
        this.part2 = part2;
        caveCount = new HashMap<>();
    }

    private CavePath(Queue<Cave> p, Cave l, Map<Cave, Integer> m, Cave next, boolean part2) {
        this.path = new LinkedList(p);
        this.lastCave = l;
        this.caveCount = new HashMap<>(m);
        this.path.add(next);
        this.lastCave = next;
        int v = this.caveCount.getOrDefault(next, 0);
        this.caveCount.put(next, v + 1);
        this.part2 = part2;
    }

    public boolean isDone() {
        return lastCave.isEnd();
    }

    public Optional<CavePath> addCave(Cave next) {
//        if (!next.isBigCave && path.contains(next)) {
//            return Optional.empty();
//        }
        if (next.isSmallCave && path.contains(next)) {
            if (!part2 && path.contains(next)) {
                return Optional.empty();
            } else if (next.isStart()) {
                return Optional.empty();
            } else {
                //for part 2, only one small cave can be visited twice
                //has a small cave already been visited twice?
                boolean alreadyDoubled = caveCount.values().contains(2);
                if(alreadyDoubled){
                    return Optional.empty();
                }
                int currentVisitCount = caveCount.getOrDefault(next, 0);
                if (currentVisitCount > 1) {
                    return Optional.empty();
                }
            }
        }

        CavePath forked = fork(next);
        return Optional.of(forked);
    }

    private CavePath fork(Cave next) {
        return new CavePath(this.path, this.lastCave, this.caveCount, next, part2);
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
