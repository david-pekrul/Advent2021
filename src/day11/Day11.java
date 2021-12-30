package day11;

public class Day11 {
    public static void main(String[] args) {
        OctopiGrid octopi = new OctopiGrid.Builder().setInput(realInput).build();

        int days = 100;
        for(int d = 0; d < days; d++){
            octopi.processDay();
        }

        System.out.println("Answer 1: " + octopi.countFlashes());

        octopi = new OctopiGrid.Builder().setInput(realInput).build();

        boolean done = false;
        int day = 0;
        while(!done){
            octopi.processDay();
            day++;
            done = octopi.allFlash();
        }
        System.out.println("Answer 2: " + day);

    }

    static String testInput1 = """
            11111
            19991
            19191
            19991
            11111""";

    static String testInput2 = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526""";

    static String realInput = """
            6788383436
            5526827441
            4582435866
            5152547273
            3746433621
            2465145365
            6324887128
            8537558745
            4718427562
            2283324746""";
}
