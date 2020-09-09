package de.uulm.sp.pvs;

import de.uulm.sp.pvs.util.Sokoban;
import de.uulm.sp.pvs.util.SokobanLevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {
    public final Logger logger = Logger.getGlobal();
    public static void main(String[] args) throws Exception {
        char[][] c = new char[7][];
        c[0] = "######a".toCharArray();
        c[1] = "#.....#".toCharArray();
        c[2] = "#..$..#".toCharArray();
        c[3] = "#.$@$.#".toCharArray();
        c[4] = "#..$..#".toCharArray();
        c[5] = "#.....#".toCharArray();
        c[6] = "#######".toCharArray();

        SokobanLevel sl = new SokobanLevel("src/main/resources/lvlexample.xml");


        Sokoban s = new Sokoban(sl.getPlayingField());
        //Sokoban t = new Sokoban(c);

        runSokoban(s);


    }

    public static void runSokoban(Sokoban s) {
        Scanner scn = new Scanner(System.in);
        while (true) {
            System.out.println(s);
            System.out.println("Move your Player @ in one of the directions U/R/D/L or exit");
            if (scn.hasNext()) {
                String str = scn.next();
                if (str.equals("exit")) {
                    return;
                }
                try {
                    if (s.move(str)) {
                        System.out.println("Moved player in direction " + str);
                    } else {
                        System.out.println("Failed to move player in direction " + str);
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Entered illegal command");
                }


            }
        }

    }

    public static File getResourceAsFile(String resourcePath) {
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                return null;
            }

            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
