package day14;

import java.util.List;

public class Day14 {
    public static void main(String[] args) {
        PolymerChain chain = new PolymerChain(realInput);

//        for(int i = 0; i < testOutputsByDay.size(); i++){
//            System.out.println("Computed: " + chain);
//            System.out.println("Actual:   " + new PolymerChain(testOutputsByDay.get(i)).toString());
//            System.out.println();
//            chain = chain.next();
//       }

        for(int i = 0; i < 10; i++){
            chain = chain.next();
        }
        System.out.println("Answer 1: " + chain.getAnswer());


        chain = new PolymerChain(realInput);
        for(int i = 0; i < 40; i++){
            chain = chain.next();
        }
        System.out.println("Answer 2: " + chain.getAnswer());
    }

    static String testInput = """
            NNCB
                        
            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C""";

    static String realInput = """
            CHBBKPHCPHPOKNSNCOVB
                        
            SP -> K
            BB -> H
            BH -> S
            BS -> H
            PN -> P
            OB -> S
            ON -> C
            HK -> K
            BN -> V
            OH -> F
            OF -> C
            SN -> N
            PF -> H
            CF -> F
            HN -> S
            SK -> F
            SS -> C
            HH -> C
            SO -> B
            FS -> P
            CB -> V
            NK -> F
            KK -> P
            VN -> H
            KF -> K
            PS -> B
            HP -> B
            NP -> P
            OO -> B
            FB -> V
            PO -> B
            CN -> O
            HC -> B
            NN -> V
            FV -> F
            BK -> K
            VC -> K
            KV -> V
            VF -> V
            FO -> O
            FK -> B
            HS -> C
            OV -> F
            PK -> F
            VV -> S
            NH -> K
            SH -> H
            VB -> H
            NF -> P
            OK -> B
            FH -> F
            CO -> V
            BC -> K
            PP -> S
            OP -> V
            VO -> C
            NC -> F
            PB -> F
            KO -> O
            BF -> C
            VS -> K
            KN -> P
            BP -> F
            KS -> V
            SB -> H
            CH -> N
            HF -> O
            CV -> P
            NB -> V
            FF -> H
            OS -> S
            CS -> S
            KC -> F
            NS -> N
            NV -> O
            SV -> V
            BO -> V
            BV -> V
            CC -> F
            CK -> H
            KP -> C
            KH -> H
            KB -> F
            PH -> P
            VP -> P
            OC -> F
            FP -> N
            HV -> P
            HB -> H
            PC -> N
            VK -> H
            HO -> V
            CP -> F
            SF -> N
            FC -> P
            NO -> K
            VH -> S
            FN -> F
            PV -> O
            SC -> N""";

    static String testDay1 = "NCNBCHB";
    static String testDay2 = "NBCCNBBBCBHCB";

    static List<String> testOutputsByDay = List.of("NNCB", "NCNBCHB" ,"NBCCNBBBCBHCB","NBBBCNCCNBBNBNBBCHBHHBCHB","NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB");

}
