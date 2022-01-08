package day16;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Packet {
    public int version;
    //    public int operatorId;
    List<Packet> subpackets;
    public long literal;
//    public IntegerOps opFunc;

    static final Map<Integer, String> typeIdToOperator = new HashMap<>();

    static {
        typeIdToOperator.put(0, "+");
        typeIdToOperator.put(1, "*");
        typeIdToOperator.put(2, "min");
        typeIdToOperator.put(3, "max");
        typeIdToOperator.put(4, "int");
        typeIdToOperator.put(5, ">");
        typeIdToOperator.put(6, "<");
        typeIdToOperator.put(7, "==");
    }

    public interface IntegerOps {
        int apply(int a, int b);
    }


    public Packet(int v, long l) {
        this.version = v;
        this.literal = l;
        subpackets = List.of();
    }

    public Packet(int v, List<Packet> p) {
        this.version = v;
        this.subpackets = p;
        this.literal = -1;
    }

    public abstract long calc();

    public int getVersionSum() {
        int sum = version;
        for (Packet subpacket : subpackets) {
            sum += subpacket.getVersionSum();
        }
        return sum;
    }


    public static class Factory {

        public Packet build(int v, long literal) {
            return new LiteralPacket(v, literal);
        }

        public Packet build(int v, int o, List<Packet> subPackets) {
            switch (o) {
                case 0:
                    return new SumPacket(v, subPackets);
                case 1:
                    return new MultiplyPacket(v, subPackets);
                case 2:
                    return new MinPacket(v, subPackets);
                case 3:
                    return new MaxPacket(v, subPackets);
                case 5:
                    return new GreaterThanPacket(v, subPackets);
                case 6:
                    return new LessThanPacket(v, subPackets);
                case 7:
                    return new EqualsPacket(v, subPackets);
            }
            throw new RuntimeException("Unrecognized Operation Type: " + o);
        }


    }

    private static class SumPacket extends Packet {
        public SumPacket(int v, List<Packet> p) {
            super(v, p);
        }

        @Override
        public long calc() {
            if (this.subpackets.size() == 1) {
                return this.subpackets.get(0).calc();
            }
            List<Long> calcStream = this.subpackets.stream().map(Packet::calc).collect(Collectors.toList());
            long answer = calcStream.stream().reduce(0l, Long::sum);
            if (answer < 0) {
                throw new RuntimeException("answer is negative");
            }
            return answer;
        }
    }

    private static class MultiplyPacket extends Packet {
        public MultiplyPacket(int v, List<Packet> p) {
            super(v, p);
        }

        @Override
        public long calc() {
            List<Long> calcStream = this.subpackets.stream().map(Packet::calc).collect(Collectors.toList());
            long answer = calcStream.stream().reduce(1l, (a, b) -> a * b);
            if (answer < 0) {
                throw new RuntimeException("answer is negative");
            }
            return answer;
        }
    }

    private static class MinPacket extends Packet {
        public MinPacket(int v, List<Packet> p) {
            super(v, p);
        }

        @Override
        public long calc() {
            if (this.subpackets.size() == 1) {
                return this.subpackets.get(0).calc();
            }
            List<Long> calcStream = this.subpackets.stream().map(Packet::calc).collect(Collectors.toList());
            long answer = calcStream.stream().reduce(Long.MAX_VALUE, (a, b) -> Long.min(a, b));
            if (answer < 0) {
                throw new RuntimeException("answer is negative");
            }
            return answer;
        }
    }

    private static class MaxPacket extends Packet {
        public MaxPacket(int v, List<Packet> p) {
            super(v, p);
        }

        @Override
        public long calc() {
            if (this.subpackets.size() == 1) {
                return this.subpackets.get(0).calc();
            }
            List<Long> calcStream = this.subpackets.stream().map(Packet::calc).collect(Collectors.toList());
            long answer = calcStream.stream().reduce(0l, (a, b) -> Long.max(a, b));
            if (answer < 0) {
                throw new RuntimeException("answer is negative");
            }
            return answer;
        }
    }

    private static class GreaterThanPacket extends Packet {
        public GreaterThanPacket(int v, List<Packet> p) {
            super(v, p);
        }

        @Override
        public long calc() {
            if (subpackets.size() > 2) {
                throw new RuntimeException("too many subpackets");
            }
            long left = subpackets.get(0).calc();
            long right = subpackets.get(1).calc();
            return left > right ? 1 : 0;
        }
    }

    private static class LessThanPacket extends Packet {
        public LessThanPacket(int v, List<Packet> p) {
            super(v, p);
        }

        @Override
        public long calc() {
            if (subpackets.size() > 2) {
                throw new RuntimeException("too many subpackets");
            }
            long left = subpackets.get(0).calc();
            long right = subpackets.get(1).calc();
            return left < right ? 1 : 0;
        }
    }

    private static class EqualsPacket extends Packet {
        public EqualsPacket(int v, List<Packet> p) {
            super(v, p);
        }

        @Override
        public long calc() {
            if (subpackets.size() > 2) {
                throw new RuntimeException("too many subpackets");
            }
            long left = subpackets.get(0).calc();
            long right = subpackets.get(1).calc();
            return left == right ? 1 : 0;
        }
    }

    private static class LiteralPacket extends Packet {
        public LiteralPacket(int version, long literal) {

            super(version, literal);
            if (literal < 0) {
                throw new RuntimeException("literal is negative");
            }
        }

        @Override
        public long calc() {
            return this.literal;
        }
    }

}
