package main.java.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6 {

    static final int DAYS_TO_SPAWN = 7;
    static final int MATURATION_DELAY = 2;
    static final int NUMBER_OF_DAYS = 80;
    static final int NUMBER_OF_DAYS2 = 256;

    public static void main(String[] args) {
        FishPool pool = new FishPool(DAYS_TO_SPAWN, MATURATION_DELAY, realStart);

        for (int i = 0; i < NUMBER_OF_DAYS; i++) {
            pool.processDay();
        }
        System.out.println("Answer 1: " + pool.countFish());

        for(int i = 0; i < NUMBER_OF_DAYS2 - NUMBER_OF_DAYS; i++){
            pool.processDay();
        }
        System.out.println("Answer 2: " + pool.countFish());
    }


    public static List<FishPool> parseResults() {
        List<FishPool> testResults = new ArrayList<>();
        Scanner scanner = new Scanner(testOutput);
        while (scanner.hasNext()) {
            testResults.add(new FishPool(DAYS_TO_SPAWN, MATURATION_DELAY, scanner.nextLine()));
        }
        return testResults;
    }


    static String testStart = "3,4,3,1,2";

    static String realStart = "1,1,3,1,3,2,1,3,1,1,3,1,1,2,1,3,1,1,3,5,1,1,1,3,1,2,1,1,1,1,4,4,1,2,1,2,1,1,1,5,3,2,1,5,2,5,3,3,2,2,5,4,1,1,4,4,1,1,1,1,1,1,5,1,2,4,3,2,2,2,2,1,4,1,1,5,1,3,4,4,1,1,3,3,5,5,3,1,3,3,3,1,4,2,2,1,3,4,1,4,3,3,2,3,1,1,1,5,3,1,4,2,2,3,1,3,1,2,3,3,1,4,2,2,4,1,3,1,1,1,1,1,2,1,3,3,1,2,1,1,3,4,1,1,1,1,5,1,1,5,1,1,1,4,1,5,3,1,1,3,2,1,1,3,1,1,1,5,4,3,3,5,1,3,4,3,3,1,4,4,1,2,1,1,2,1,1,1,2,1,1,1,1,1,5,1,1,2,1,5,2,1,1,2,3,2,3,1,3,1,1,1,5,1,1,2,1,1,1,1,3,4,5,3,1,4,1,1,4,1,4,1,1,1,4,5,1,1,1,4,1,3,2,2,1,1,2,3,1,4,3,5,1,5,1,1,4,5,5,1,1,3,3,1,1,1,1,5,5,3,3,2,4,1,1,1,1,1,5,1,1,2,5,5,4,2,4,4,1,1,3,3,1,5,1,1,1,1,1,1";

    static String testOutput = """
            2,3,2,0,1
            1,2,1,6,0,8
            0,1,0,5,6,7,8
            6,0,6,4,5,6,7,8,8
            5,6,5,3,4,5,6,7,7,8
            4,5,4,2,3,4,5,6,6,7
            3,4,3,1,2,3,4,5,5,6
            2,3,2,0,1,2,3,4,4,5
            1,2,1,6,0,1,2,3,3,4,8
            0,1,0,5,6,0,1,2,2,3,7,8
            6,0,6,4,5,6,0,1,1,2,6,7,8,8,8
            5,6,5,3,4,5,6,0,0,1,5,6,7,7,7,8,8
            4,5,4,2,3,4,5,6,6,0,4,5,6,6,6,7,7,8,8
            3,4,3,1,2,3,4,5,5,6,3,4,5,5,5,6,6,7,7,8
            2,3,2,0,1,2,3,4,4,5,2,3,4,4,4,5,5,6,6,7
            1,2,1,6,0,1,2,3,3,4,1,2,3,3,3,4,4,5,5,6,8
            0,1,0,5,6,0,1,2,2,3,0,1,2,2,2,3,3,4,4,5,7,8
            6,0,6,4,5,6,0,1,1,2,6,0,1,1,1,2,2,3,3,4,6,7,8,8,8,8
            """;
}
