package de.uulm.sp.pvs;

import de.uulm.sp.pvs.util.Sokoban;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        char[][] c = new char[7][];
        c[0] = "#######".toCharArray();
        c[1] = "#.....#".toCharArray();
        c[2] = "#..$..#".toCharArray();
        c[3] = "#.$@$.#".toCharArray();
        c[4] = "#..$..#".toCharArray();
        c[5] = "#.....#".toCharArray();
        c[6] = "#######".toCharArray();
        Sokoban s = new Sokoban(c);
        Scanner scn = new Scanner(System.in);
        boolean run=true;
        while(run){
            System.out.println(s);
            System.out.println("Move your Player @ in one of the directions U/R/D/L");
            if(scn.hasNext()){
                String str = scn.next();
                if(str.equals("exit")){
                    run=false;


                }
                try{
                    if(s.move(str)){
                        System.out.println("Moved player in direction "+str);
                    }
                    else{System.out.println("Failed to move player in direction "+str);}
                }
                catch(IllegalStateException e){System.out.println("Entered illegal command");}


            }
        }
    }
}
