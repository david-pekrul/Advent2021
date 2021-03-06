package day16;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PacketParser {

    static final Map<String, String> hexToBinary = new HashMap<>();
    static final Packet.Factory packetFactory = new Packet.Factory();


    static {
        hexToBinary.put("0", "0000");
        hexToBinary.put("1", "0001");
        hexToBinary.put("2", "0010");
        hexToBinary.put("3", "0011");
        hexToBinary.put("4", "0100");
        hexToBinary.put("5", "0101");
        hexToBinary.put("6", "0110");
        hexToBinary.put("7", "0111");
        hexToBinary.put("8", "1000");
        hexToBinary.put("9", "1001");
        hexToBinary.put("A", "1010");
        hexToBinary.put("B", "1011");
        hexToBinary.put("C", "1100");
        hexToBinary.put("D", "1101");
        hexToBinary.put("E", "1110");
        hexToBinary.put("F", "1111");


    }


    public static Packet parse(String rawInput) {
        StringBuilder sb = new StringBuilder();
        for (char c : rawInput.toCharArray()) {
            sb.append(hexToBinary.get(("" + c).toUpperCase()));
        }

        String rawBinary = sb.toString();

        LinkedList<Character> bits = new LinkedList<>();
        for (char c : rawBinary.toCharArray()) {
            bits.add(c);
        }

        return parse(bits);
    }

    private static Packet parse(LinkedList<Character> remainingBits) {
        //packet always starts with 3 bits as the version
        //packet then has 3 bits as the operator
        int version = (int)binaryToDecimal(takeNBits(remainingBits, 3));
        int typeId = (int) binaryToDecimal(takeNBits(remainingBits, 3));

        if (typeId == 4) { //this is a literal value
            return packetFactory.build(version,parseLiteral(remainingBits));
        }

        char i = remainingBits.pop();
        List<Packet> subPackets = new LinkedList<>();

        if (i == '0') {
            int numberOfBitsInSubpackets = (int) binaryToDecimal(takeNBits(remainingBits, 15));
            LinkedList<Character> subList = createSubList(remainingBits, numberOfBitsInSubpackets);
            while (!subList.isEmpty()) {
                subPackets.add(parse(subList));
                if (subList.stream().allMatch(x -> x.equals('0'))) {
                    break;
                }
            }
        } else {
            //i == 1
            //the next 11 bits are the binary for how many subpackets there are
            long numberOfSubpackets = binaryToDecimal(takeNBits(remainingBits, 11));
            for (int p = 0; p < numberOfSubpackets; p++) {
                Packet subPacket = parse(remainingBits);
                subPackets.add(subPacket);
            }
        }

        return packetFactory.build(version,typeId,subPackets);
    }

    public static long binaryToDecimal(String binary) {
        long num = 0;
        for (int i = binary.length() - 1; i >= 0; i--) {
            if (binary.charAt(i) == '1') {
                num += Math.pow(2, binary.length() - 1 - i);
            }
        }
        return num;
    }


    public static String takeNBits(LinkedList<Character> bits, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(bits.pop());
        }
        return sb.toString();
    }

    public static LinkedList<Character> createSubList(LinkedList<Character> bits, int n) {
        LinkedList<Character> subList = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            subList.add(bits.pop());
        }
        return subList;
    }

    public static long parseLiteral(LinkedList<Character> bits) {
        StringBuilder sb = new StringBuilder();
        while (bits.pop().equals('1')) {
            for (int i = 0; i < 4; i++) {
                sb.append(bits.pop());
            }
        }
        //the while loop just popped a set of 5 that started with a 0
        for (int i = 0; i < 4; i++) {
            sb.append(bits.pop());
        }
        return binaryToDecimal(sb.toString());
    }

}
