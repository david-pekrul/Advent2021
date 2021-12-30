package day12;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day12 {
    public static void main(String[] args) {

        CaveSystem caveSystem = new CaveSystem.Builder().setInput(realInput).build();

        Set<CavePath> paths = caveSystem.getAllPaths(false);
        System.out.println("Answer 1: " + paths.size());

        Set<CavePath> paths2 = caveSystem.getAllPaths(true);

        System.out.println("Answer 2: " + paths2.size());

    }

    static String test1 = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end""";

    static String test2 = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc""";

    static String test3 = """
            fs-end
            he-DX
            fs-he
            start-DX
            pj-DX
            end-zg
            zg-sl
            zg-pj
            pj-he
            RW-he
            fs-DX
            pj-RW
            zg-RW
            start-pj
            he-WI
            zg-he
            pj-fs
            start-RW""";

    static String realInput = """
            start-kc
            pd-NV
            start-zw
            UI-pd
            HK-end
            UI-kc
            pd-ih
            ih-end
            start-UI
            kc-zw
            end-ks
            MF-mq
            HK-zw
            LF-ks
            HK-kc
            ih-HK
            kc-pd
            ks-pd
            MF-pd
            UI-zw
            ih-NV
            ks-HK
            MF-kc
            zw-NV
            NV-ks""";

    static String part2TestOutput = """
            start,A,b,A,b,A,c,A,end
            start,A,b,A,b,A,end
            start,A,b,A,b,end
            start,A,b,A,c,A,b,A,end
            start,A,b,A,c,A,b,end
            start,A,b,A,c,A,c,A,end
            start,A,b,A,c,A,end
            start,A,b,A,end
            start,A,b,d,b,A,c,A,end
            start,A,b,d,b,A,end
            start,A,b,d,b,end
            start,A,b,end
            start,A,c,A,b,A,b,A,end
            start,A,c,A,b,A,b,end
            start,A,c,A,b,A,c,A,end
            start,A,c,A,b,A,end
            start,A,c,A,b,d,b,A,end
            start,A,c,A,b,d,b,end
            start,A,c,A,b,end
            start,A,c,A,c,A,b,A,end
            start,A,c,A,c,A,b,end
            start,A,c,A,c,A,end
            start,A,c,A,end
            start,A,end
            start,b,A,b,A,c,A,end
            start,b,A,b,A,end
            start,b,A,b,end
            start,b,A,c,A,b,A,end
            start,b,A,c,A,b,end
            start,b,A,c,A,c,A,end
            start,b,A,c,A,end
            start,b,A,end
            start,b,d,b,A,c,A,end
            start,b,d,b,A,end
            start,b,d,b,end
            start,b,end""";

    static Set<String> getPart2Test(){
        Set<String> s = new HashSet<>();
        Scanner scanner = new Scanner(part2TestOutput);
        while(scanner.hasNext()) {
            s.add(scanner.nextLine());
        }
        return s;
    }
}
