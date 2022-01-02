package day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Chemical {
    char left;
    char right;

    static Map<String,Character> transition = new HashMap<>();

    public Chemical(char l, char r){
        left = l;
        right = r;
    }

    public List<Chemical> generateNext() {
        String current = "" + left + right;
        Character next = transition.get(current);
        return List.of(new Chemical(left, next), new Chemical(next, right));
    }

    public static void addTransition(String rawInput){
        String[] split = rawInput.split("->");
        transition.put(split[0].trim(), split[1].trim().charAt(0));
    }

    @Override
    public String toString() {
        return "[" + left + right + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chemical chemical = (Chemical) o;
        return left == chemical.left && right == chemical.right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
