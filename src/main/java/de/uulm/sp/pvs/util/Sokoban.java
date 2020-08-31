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
                if (this.playingField[i][j] == '@') {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * @return a printable String containing line breaks to print the playing field in console
     */
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
     *
     * @param dir          the direction in which to move, north east south west
     * @return true, if the player was moved in the specified direction, false if not
     * @throws IllegalStateException if an Illegal character is entered
     */
    public boolean move( String dir) {
        boolean b = false;
        dir = dir.toLowerCase();
        int[] ppos = findPlayer();
        switch (dir) {

            case "u":
                if (playingField[ppos[0] - 1][ppos[1]] == '.') {
                    playingField[ppos[0] - 1][ppos[1]] = '@';//move player
                    playingField[ppos[0]][ppos[1]] = '.'; //free old position
                    b = true;
                } else {
                    if (ppos[0] > 1 && playingField[ppos[0] - 1][ppos[1]] == '$' && playingField[ppos[0] - 2][ppos[1]] == '.') {
                        playingField[ppos[0] - 2][ppos[1]] = '$';// move chest
                        playingField[ppos[0] - 1][ppos[1]] = '@';//move player
                        playingField[ppos[0]][ppos[1]] = '.'; //free old position
                        b = true;
                    }
                }
                break;
            case "r":
                if (playingField[ppos[0]][ppos[1] + 1] == '.') {
                    playingField[ppos[0]][ppos[1] + 1] = '@';//move player
                    playingField[ppos[0]][ppos[1]] = '.'; //free old position
                    b = true;
                } else {
                    if (ppos[1] < playingField[ppos[0]].length - 1 && playingField[ppos[0]][ppos[1] + 1] == '$' && playingField[ppos[0]][ppos[1] + 2] == '.') {
                        playingField[ppos[0]][ppos[1] + 2] = '$';// move chest
                        playingField[ppos[0]][ppos[1] + 1] = '@';//move player
                        playingField[ppos[0]][ppos[1]] = '.'; //free old position
                        b = true;
                    }
                }
                break;
            case "d":
                if (playingField[ppos[0] + 1][ppos[1]] == '.') {
                    playingField[ppos[0] + 1][ppos[1]] = '@';//move player
                    playingField[ppos[0]][ppos[1]] = '.'; //free old position
                    b = true;
                } else {
                    if (ppos[0] < playingField.length - 1 && playingField[ppos[0] + 1][ppos[1]] == '$' && playingField[ppos[0] + 2][ppos[1]] == '.') {
                        playingField[ppos[0] + 2][ppos[1]] = '$';// move chest
                        playingField[ppos[0] + 1][ppos[1]] = '@';//move player
                        playingField[ppos[0]][ppos[1]] = '.'; //free old position
                        b = true;
                    }
                }
                break;
            case "l":
                if (playingField[ppos[0]][ppos[1] - 1] == '.') {
                    playingField[ppos[0]][ppos[1] - 1] = '@';//move player
                    playingField[ppos[0]][ppos[1]] = '.'; //free old position
                    b = true;
                } else {
                    if (ppos[1] > 1 && playingField[ppos[0]][ppos[1] - 1] == '$' && playingField[ppos[0]][ppos[1] - 2] == '.') {
                        playingField[ppos[0]][ppos[1] - 2] = '$';// move chest
                        playingField[ppos[0]][ppos[1] - 1] = '@';//move player
                        playingField[ppos[0]][ppos[1]] = '.'; //free old position
                        b = true;
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dir);
        }
        return b;
    }

}

