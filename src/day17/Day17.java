package day17;

import java.sql.Array;
import java.util.*;

public class Day17 {

    static final int targetMinX = 135;
    static final int targetMaxX = 155;
    static final int targetMaxY = -78;
    static final int targetMinY = -102;


    public static void main(String[] args) {
        Target target = new Target(targetMinX, targetMaxX, targetMinY, targetMaxY);
//        Target target = new Target(20,30, -10,-5);

        int minInitialX = Projectile.getMinXSpeed(target.minX);
        int maxInitialY = Math.abs(target.minY) + 1;
        int maxY = 0;
        int hitCounter = 0;

        for(int x = minInitialX; x < 1000; x++){
            for(int y = -maxInitialY; y < maxInitialY; y++){
                Projectile p = new Projectile(x,y);
                boolean hit = p.hasHit(target);;
                while(!hit){
                    p.step();
                    hit = p.hasHit(target);
                    if(!hit && !p.canStillHit(target)){
                       break;
                    }
                }
                if(hit){
                    hitCounter++;
                    int yHeight = sumFromOneToN(y);
                    if(yHeight > maxY){
                        maxY = yHeight;
                    }
                }
            }
        }

        System.out.println("Answer 1: " + maxY);
        System.out.println("Answer 2: " + hitCounter);

    }

    public static int sumFromOneToN(int n) {
        return (n * (n + 1)) / 2;
    }


}
