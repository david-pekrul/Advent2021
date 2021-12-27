package main.java.day8;

import java.util.*;
import java.util.stream.Collectors;

public class SevenSeg {

    static Map<Integer, List<Character>> digitToSegments = Map.of(
            0, List.of('a', 'b', 'c', 'e', 'f', 'g'),
            1, List.of('c', 'f'),
            2, List.of('a', 'c', 'd', 'e', 'g'),
            3, List.of('a', 'c', 'd', 'f', 'g'),
            4, List.of('b', 'c', 'd', 'f'),
            5, List.of('a', 'b', 'd', 'f', 'g'),
            6, List.of('a', 'b', 'd', 'e', 'f', 'g'),
            7, List.of('a', 'c', 'f'),
            8, List.of('a', 'b', 'c', 'd', 'e', 'f', 'g'),
            9, List.of('a', 'b', 'c', 'd', 'f', 'g')
    );

    static final List<Integer> orderToSolveDigitsIn = List.of(1, 7, 4, 8, 2, 3, 5, 6, 9, 0);

    public Set<Integer> unusedNumbers;
    public Map<Character, Character> inputToSeg;
    public List<String> remainingRawInputs;


    public SevenSeg(List<String> encodedInputs) {
        unusedNumbers = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        inputToSeg = new HashMap<>();
        this.remainingRawInputs = encodedInputs;

    }

    public SevenSeg(Map<Character, Character> inputToSeg, List<String> remainingRawInputs, List<Integer> unusedNumbers) {
        this.unusedNumbers = Set.copyOf(unusedNumbers);
        this.inputToSeg = Map.copyOf(inputToSeg);
        this.remainingRawInputs = List.copyOf(remainingRawInputs);
    }

    public boolean isComplete() {
//        return unusedNumbers.size() == 0 && unusedSegment.size() == 0;
        return inputToSeg.size() == 7 && unusedNumbers.isEmpty();
    }

    /**
     * This takes in a digit and the raw segment
     * From those, this function determines all the possible valid permutations of mappings can be made for the input parameters to work.
     *
     * @param digit
     * @param proposedRawSegment
     * @return
     */
    public List<SevenSeg> isMoveValid(int digit, String proposedRawSegment) {
        List<Character> digitSegments = digitToSegments.get(digit);
        if (digitSegments.size() != proposedRawSegment.length()) {
            return Collections.EMPTY_LIST;
        }

        List<SevenSeg> validSegments = new ArrayList<>();

        List<String> permutationsOfDigitSegments = generatePermutationsOfSet(digitSegments);

        for(String digitSegmentPermutation: permutationsOfDigitSegments) {
            Map<Character, Character> testInputToSeg = new HashMap<>(inputToSeg);
            boolean isValid = true;
            for (int i = 0; i < digitSegmentPermutation.length(); i++) {
                Character digitSegment = digitSegmentPermutation.charAt(i);
                Character inputSegment = proposedRawSegment.charAt(i);

                if (inputToSeg.containsKey(inputSegment)) {
                    if (!inputToSeg.get(inputSegment).equals(digitSegment)) {
                        //The proposedSegments input would remap an existing assignment.
                        isValid = false;
                        break;
                    }
                    testInputToSeg.put(inputSegment, digitSegment);
                } else {
                    testInputToSeg.put(inputSegment, digitSegment);
                }
            }
            if(!isValid){
                continue;
            }

            //Making it here means that the thing worked. Build the next SevenSeg
            List<String> nextUnusedInputs = new ArrayList<>(this.remainingRawInputs);
            nextUnusedInputs.remove(proposedRawSegment);

            ArrayList<Integer> nextUnusedNumbers = new ArrayList<>(this.unusedNumbers);
            nextUnusedNumbers.remove((Integer) digit);

            SevenSeg nextSevenSeg = new SevenSeg(testInputToSeg, nextUnusedInputs, nextUnusedNumbers);
            validSegments.add(nextSevenSeg);

        }

        return validSegments;
    }

    public List<SevenSeg> generateNextSearch() {

        int nextDigitToSolve = -1;
        for (int i : orderToSolveDigitsIn) {
            if (unusedNumbers.contains(i)) {
                nextDigitToSolve = i;
                break;
            }
        }

        Set<List<Character>> possibleRawInputs = digitToPossibleSegments(nextDigitToSolve);
//        List<Integer> nextUnusedDigits = new ArrayList<>(unusedNumbers);
//        nextUnusedDigits.remove(nextDigitToSolve);


        List<SevenSeg> nextGeneration = new ArrayList<>();
        for (List<Character> possibleRawInput : possibleRawInputs) {
            //possibleInput == the post-mapping value

            String possibleRawInputString = charsToString(possibleRawInput);
            List<SevenSeg> validNextSevenSeg = isMoveValid(nextDigitToSolve, possibleRawInputString);
            if (validNextSevenSeg.isEmpty()) {
                continue;
            }
            nextGeneration.addAll(validNextSevenSeg);
        }


        return nextGeneration;
    }

    public SevenSeg performSearch() {
        Stack<SevenSeg> stack = new Stack<>();
        stack.push(this);

        List<SevenSeg> validSolutions = new ArrayList<>();

        while (!stack.isEmpty()) {
            SevenSeg current = stack.pop();
            List<SevenSeg> nextGeneration = current.generateNextSearch();
            for (SevenSeg nextGenChild : nextGeneration) {
                if (nextGenChild.isComplete()) {
                    validSolutions.add(nextGenChild);
//                    return nextGenChild;
                    continue;
                }
                stack.add(nextGenChild);
            }
        }
        return validSolutions.get(0);
    }

    public Set<List<Character>> digitToPossibleSegments(int digit) {
        //If I need to display the digit, which of the raw input strings could possibly be used to display that digit?
        int numberOfSegsUsedToDisplayDigit = digitToSegments.get(digit).size();
        Set<List<Character>> possibleSegmentsSetsToUse = remainingRawInputs.stream()
                .filter(rawInput -> rawInput.length() == numberOfSegsUsedToDisplayDigit)
                .map(rawInput -> {
                    return rawInput.chars()
                            .mapToObj(e -> (char) e).collect(Collectors.toList());
                })
                .collect(Collectors.toSet());

//         digitToSegments.entrySet().stream().filter(kv -> kv.getValue().size() == numberOfSegsUsedToDisplayDigit).map(kv -> kv.getValue()).collect(Collectors.toSet());
        return possibleSegmentsSetsToUse;
    }

    public static List<String> generatePermutationsOfSet(List<Character> set) {

        int stopSize = set.size();
        List<String> permutations = new ArrayList<>();

        Stack<String> generatingStack = new Stack<>();
        set.forEach(x -> generatingStack.add(x.toString()));

        while (!generatingStack.isEmpty()) {
            String prev = generatingStack.pop();
            if (prev.length() == stopSize) {
                permutations.add(prev);
                continue; //this value is finished
            }

            for (Character c : set) {
                if (!prev.contains(c.toString())) {
                    String next = prev + c;
                    generatingStack.push(next);
                }
            }
        }
        return permutations;
    }

    private String charsToString(List<Character> chars) {
        StringBuilder sb = new StringBuilder();
        for (Character c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }

    public int decypher(String rawInput){
        Set<Character> mappedChars = new HashSet<>();
        for(Character c : rawInput.toCharArray()){
            mappedChars.add(inputToSeg.get(c));
        }

        return digitToSegments.entrySet().stream().filter(kv -> {
            return kv.getValue().containsAll(mappedChars) && kv.getValue().size() == mappedChars.size();
        }).map(kv -> kv.getKey()).toList().get(0);
    }


}