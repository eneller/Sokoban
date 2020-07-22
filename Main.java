package de.eneller.uulm.pvs;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        char [][] sokoban = new char [7][];
        sokoban [0] = "#######" . toCharArray ();
        sokoban [1] = "#.....#" . toCharArray ();
        sokoban [2] = "#..$..#" .toCharArray();
        sokoban [3] = "#.$@$.#" .toCharArray();
        sokoban [4] = "#..$..#" .toCharArray();
        sokoban [5] = "#.....#" . toCharArray ();
        sokoban [6] = "#######" . toCharArray ();

        Sokoban ss = new Sokoban(sokoban);
        System.out.println(ss.sokobanToString(sokoban));
        ss.move(sokoban,'w');
        ss.move(sokoban,'w');
        System.out.print(ss.sokobanToString(sokoban));
        System.out.println(Arrays.toString(ss.findPlayer(sokoban)));

    }

}
