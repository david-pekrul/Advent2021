package day16;

import java.util.List;

public class Packet {
    public int version;
    public int operatorId;
    List<Packet> subpackets;
    public int literal;

    public Packet(int v, int o, int l){
        this.version = v;
        this.operatorId = o;
        this.literal = l;
        subpackets = List.of();
    }

    public Packet(int v, int o, List<Packet> p){
        this.version = v;
        this.operatorId = o;
        this.subpackets = p;
        this.literal = Integer.MIN_VALUE;
    }

    public int getVersionSum(){
        int sum = version;
        for (Packet subpacket : subpackets) {
            sum += subpacket.getVersionSum();
        }
        return sum;
    }
}
