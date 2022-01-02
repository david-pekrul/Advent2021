package day14;

import java.util.*;

public class PolymerChain {
    Map<Chemical, Long> currentState;
    Character lastChar;


    public PolymerChain(String rawInput){
        currentState = new HashMap<>();

        Scanner scanner = new Scanner(rawInput);
        String line = scanner.nextLine();

        lastChar = line.charAt(line.length() -1);

        for(int i = 1; i < line.length(); i++){
            Chemical c = new Chemical(line.charAt(i-1),line.charAt(i));
            addChemical(c, 1l);
        }

        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            if(line.isBlank()){
                continue;
            }

            Chemical.addTransition(line);

        }
    }

    private PolymerChain(Map<Chemical,Long> previous, Character lc){
        currentState = new HashMap<>();
        this.lastChar = lc;

        for(Map.Entry<Chemical, Long> kv : previous.entrySet()){
            for(Chemical c : kv.getKey().generateNext()){
                addChemical(c,kv.getValue());
            }
        }
    }

    public PolymerChain next(){
        return new PolymerChain(currentState, lastChar);
    }

    public void addChemical(Chemical c, Long numberOfTimes){
        currentState.compute(c,(k,v) -> v != null ? v + numberOfTimes : numberOfTimes);
    }

    public Integer getLength(){
        int total = 0;
        for(Map.Entry<Chemical, Long> kv : currentState.entrySet()){
                total += (kv.getValue());
        }
        //the +1 is because the last chemical is not overlapping
        return total+1;
    }

    public long getAnswer() {
        Map<Character,Long> counts = new HashMap<>();
        currentState.entrySet().forEach(kv -> {
            counts.compute(kv.getKey().left, (k,v) -> v != null? kv.getValue() + v : kv.getValue());
        });

        counts.compute(lastChar, (k, v) -> v +1 );
//        return integers.get(integers.size()-1) - integers.get(0);

        List<Long> sortedCounts = counts.entrySet().stream().map(kv -> kv.getValue()).sorted().toList();
        return sortedCounts.get(sortedCounts.size()-1) - sortedCounts.get(0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        currentState.entrySet().stream()
                .sorted(Comparator.comparing(a -> a.getKey().toString()))
                .forEach(a -> sb.append(a.getKey() + "->" + a.getValue() + ", "));

        return sb.toString();
    }
}
