package main.java.day8;

import java.util.List;

public class Day8Input {
    public List<String> calibrations;
    public List<String> encodedOutputs;

    public Day8Input(String rawLine){
        String[] split = rawLine.split("\\|");
        this.calibrations = List.of(split[0].trim().split(" "));
        this.encodedOutputs = List.of(split[1].trim().split(" "));
    }
}
