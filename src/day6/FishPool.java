package day6;

import java.util.Arrays;
import java.util.LinkedList;

public class FishPool {

    int daysToSpawn; //days it takes a mature fish to spawn a new fish;
    int maturationDelay;

    LinkedList<Long> fishAtState;

    public FishPool(int daysToSpawn, int maturationDelay, String initialState){
        this.daysToSpawn = daysToSpawn;
        this.maturationDelay = maturationDelay;
        fishAtState = new LinkedList<Long>();
        for(int i = 0; i < daysToSpawn + maturationDelay; i++){
            fishAtState.push(0l);
        }

        for(String rawFish : initialState.split(",")){
            int idx = Integer.parseInt(rawFish);
            fishAtState.set(idx,fishAtState.get(idx)+1);
        };
    }

    public void processDay() {
        long fishThatCanSpawn = fishAtState.removeFirst();
        long newFish = fishThatCanSpawn;
        fishAtState.set(daysToSpawn-1,fishAtState.get(daysToSpawn-1)+fishThatCanSpawn);
        fishAtState.addLast(newFish);
    }

    public long countFish() {
        return fishAtState.stream().map(x ->(long)x).reduce(0l,(a,b) -> a+b);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FishPool{");
        sb.append("daysToSpawn=");
        sb.append(daysToSpawn);
        sb.append(", delay=");
        sb.append(maturationDelay);
        sb.append(", states=");
        sb.append(Arrays.toString(fishAtState.toArray()));
        sb.append("}");

        return sb.toString();
    }
}
