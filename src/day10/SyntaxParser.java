package day10;

import java.sql.Array;
import java.util.*;

public class SyntaxParser {
    String input;

    static Map<Character, Character> PAIRS = Map.of(
            '(', ')',
            '[', ']',
            '<', '>',
            '{', '}'
    );

    public SyntaxParser(String input) {
        this.input = input;
    }

    public Optional<Character> getIllegalChacacter() {
        Stack<Character> stack = new Stack<>();

        for (Character c : input.toCharArray()) {
            if (isOpenCharacter(c)) {
                stack.push(c);
                continue;
            }
            //we have a closing character
            Character popped = stack.pop();
            if (!PAIRS.get(popped).equals(c)) {
                //The closing character does not match the opening character
                stack.clear();
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public List<Character> getMissingClosing() {
        Stack<Character> stack = new Stack<>();

        for (Character c : input.toCharArray()) {
            if (isOpenCharacter(c)) {
                stack.push(c);
                continue;
            }
            //we have a closing character
            Character popped = stack.pop();
            if (!PAIRS.get(popped).equals(c)) {
                //The closing character does not match the opening character
                //For this, it cannot be completed.
                return List.of();

            }
        }

        if(stack.size() == 0){
            throw new RuntimeException("there are no more characters to match...");
        }

        List<Character> completingInput = new ArrayList<>();
        while(!stack.isEmpty()){
            completingInput.add(PAIRS.get(stack.pop()));
        }

        return completingInput;
    }

    private boolean isOpenCharacter(Character c) {
        return PAIRS.keySet().contains(c);
    }
}
