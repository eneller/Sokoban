package de.uulm.sp.pvs.util;

/**
 * @author eneller
 * The game logic of the sokoban game
 */
public class Sokoban {
    private char[][] playingField;

    /**
     * At the moment just a placeholder
     * until i know what the following assignments are
     *
     * @param playingField will probably create a game instance using the given playing field
     */

    public Sokoban(char[][] playingField) {
        System.out.println("Welcome to Sokoban!");
        this.playingField = playingField;
    }

    /**
     * @return an int array of -y ,x coordinates of the player symbol in the 2d chararray
     */
    public int[] findPlayer() {

        for (int i = 0; i < this.playingField.length; i++) {
            for (int j = 0; j < this.playingField[i].length; j++) {
                if (this.playingField[i][j] == '@' || this.playingField[i][j] == '+') {//find player on free field or on goal
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * @return a printable String containing line breaks to print the playing field in console
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.playingField.length; i++) {
            for (int j = 0; j < this.playingField[i].length; j++) {
                s = s + this.playingField[i][j];
            }
            s = s + System.lineSeparator();
        }
        return s;
    }

    /**
     * moves the player symbol in the given playing field by checking two cases: if the next field is free or it is a crate and the following is free
     *
     * @param dir the direction in which to move, north east south west
     * @return true, if the player was moved in the specified direction, false if not
     * @throws IllegalStateException if an Illegal character is entered
     */
    public boolean move(String dir) {
        boolean moveSuccessful = false;
        dir = dir.toLowerCase();
        int[] ppos = findPlayer();
        int ypos = ppos[0];
        int ydir = 0;
        int xpos = ppos[1];
        int xdir = 0;
        char current = playingField[ypos][xpos];
        switch (dir) {

            case "u":
                ydir = -1;
                break;
            case "r":
                xdir = 1;
                break;
            case "d":
                ydir = 1;
                break;
            case "l":
                xdir = -1;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + dir);
        }
        char first = playingField[ypos + ydir][xpos + xdir];
        char second = 'E';
        if (ypos + (2 * ydir) < playingField.length && xpos + (2 * xdir) < playingField[ypos].length) {
            second = playingField[ypos + (2 * ydir)][xpos + (2 * xdir)];
        }
        //first attempt to move the player
        switch (first) {
            case '.':
                playingField[ypos + ydir][xpos + xdir] = '+';
                moveSuccessful = true;
                break;
            case ' ':
                playingField[ypos + ydir][xpos + xdir] = '@';
                moveSuccessful = true;
                break;
            case '*':
                switch (second) {
                    case ' ':
                        playingField[ypos + (2 * ydir)][xpos + (2 * xdir)] = '$';
                        playingField[ypos + ydir][xpos + xdir] = '+';
                        moveSuccessful = true;
                        break;
                    case '.':
                        playingField[ypos + (2 * ydir)][xpos + (2 * xdir)] = '*';
                        playingField[ypos + ydir][xpos + xdir] = '+';
                        moveSuccessful = true;
                        //default uses predefined moveSuccessful = false
                }
                break;
            case '$':
                switch (second) {
                    case ' ':
                        playingField[ypos + (2 * ydir)][xpos + (2 * xdir)] = '$';
                        playingField[ypos + ydir][xpos + xdir] = '@';
                        moveSuccessful = true;
                        break;
                    case '.':
                        playingField[ypos + (2 * ydir)][xpos + (2 * xdir)] = '*';
                        playingField[ypos + ydir][xpos + xdir] = '@';
                        moveSuccessful = true;
                        break;
                    //default uses predefined moveSuccessful = false
                }
                break;
        }

        if (moveSuccessful) {//free the field
            switch (current) {
                case '@':
                    playingField[ypos][xpos] = ' ';
                    break;
                case '+':
                    playingField[ypos][xpos] = '.';
                    break;
            }
        }
        return moveSuccessful;
    }
}

