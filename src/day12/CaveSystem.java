package day12;

import java.util.*;

public class CaveSystem {

    HashMap<String, Cave> caves;

    private CaveSystem(HashMap<String, Cave> c) {
        this.caves = c;
    }

    public Set<CavePath> getAllPaths(boolean part2) {
        Set<CavePath> allPaths = new HashSet<>();

        Cave start = caves.get("start");
        CavePath startPath = new CavePath(start, part2);
        Stack<CavePath> stack = new Stack<>();
        stack.push(startPath);
        while (!stack.isEmpty()) {
            CavePath current = stack.pop();
            if(current.isDone()){
                allPaths.add(current);
                continue;
            }
            for(Cave neighbor : current.lastCave.neighbors){
                Optional<CavePath> nextPathOpt = current.addCave(neighbor);
                if(nextPathOpt.isPresent()){
                    stack.push(nextPathOpt.get());
                }
            }
        }
        return allPaths;
    }

    public static class Builder {
        String input;

        public Builder setInput(String i) {
            input = i;
            return this;
        }

        public CaveSystem build() {
            Scanner scanner = new Scanner(input);
            HashMap<String, Cave> caves = new HashMap<>();
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split("-");
                Cave c1 = new Cave(line[0]);
                Cave c2 = new Cave(line[1]);
                caves.putIfAbsent(c1.id, c1);
                caves.putIfAbsent(c2.id, c2);
                caves.get(c1.id).addNeighbor(caves.get(c2.id));
            }

            return new CaveSystem(caves);
        }
    }

}
