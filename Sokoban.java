package de.eneller.uulm.pvs;

public class Sokoban {


    public Sokoban(char[][] playingField){
        System.out.println("Welcome to Sokoban!");
    }

    public int[] findPlayer(char[][] playingField){
        for (int i = 0; i< playingField.length;i++){
            for (int j=0; j<playingField[i].length;j++){
                if(playingField[i][j]=='@'){
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1,-1};
    }

    public String sokobanToString(char[][] playingField){
        String s = "";
        for (int i=0;i<playingField.length;i++){
            for(int j=0;j<playingField[i].length;j++){
                s = s +playingField[i][j];
            }
            s= s + System.lineSeparator();
        }
        return s;
    }

    public boolean move(char [][] playingField, char dir){
        boolean b = false;
        int[] ppos = findPlayer(playingField);
        switch(dir){

            case 'n':
                if(playingField[ppos[0]-1][ppos[1]]=='.'){
                    playingField[ppos[0]-1][ppos[1]] = '@';//move player
                    playingField[ppos[0]][ppos[1]] = '.'; //free old position
                    b= true;
                }
                else{
                    if (ppos[0]>1 && playingField[ppos[0]-1][ppos[1]]=='$' && playingField[ppos[0]-2][ppos[1]]=='.'){
                        playingField[ppos[0]-2][ppos[1]]= '$' ;// move chest
                        playingField[ppos[0]-1][ppos[1]] = '@';//move player
                        playingField[ppos[0]][ppos[1]] = '.'; //free old position
                        b= true;
                    }
                }
                break;
            case 'e':
                if(playingField[ppos[0]][ppos[1]+1]=='.'){
                    playingField[ppos[0]][ppos[1]+1] = '@';//move player
                    playingField[ppos[0]][ppos[1]] = '.'; //free old position
                    b= true;
                }
                else{
                    if (ppos[1]<playingField[ppos[0]].length-1 && playingField[ppos[0]][ppos[1]+1]=='$' && playingField[ppos[0]][ppos[1]+2]=='.'){
                        playingField[ppos[0]][ppos[1]+2]= '$' ;// move chest
                        playingField[ppos[0]][ppos[1]+1] = '@';//move player
                        playingField[ppos[0]][ppos[1]] = '.'; //free old position
                        b= true;
                    }
                }
                break;
            case 's':
                if(playingField[ppos[0]+1][ppos[1]]=='.'){
                    playingField[ppos[0]+1][ppos[1]] = '@';//move player
                    playingField[ppos[0]][ppos[1]] = '.'; //free old position
                    b= true;
                }
                else{
                    if (ppos[0]<playingField.length-1 && playingField[ppos[0]+1][ppos[1]]=='$' && playingField[ppos[0]+2][ppos[1]]=='.'){
                        playingField[ppos[0]+2][ppos[1]]= '$' ;// move chest
                        playingField[ppos[0]+1][ppos[1]] = '@';//move player
                        playingField[ppos[0]][ppos[1]] = '.'; //free old position
                        b= true;
                    }
                }
                break;
            case 'w':
                if(playingField[ppos[0]][ppos[1]-1]=='.'){
                    playingField[ppos[0]][ppos[1]-1] = '@';//move player
                    playingField[ppos[0]][ppos[1]] = '.'; //free old position
                    b= true;
                }
                else{
                    if (ppos[1]>1&& playingField[ppos[0]][ppos[1]-1]=='$' && playingField[ppos[0]][ppos[1]-2]=='.'){
                        playingField[ppos[0]][ppos[1]-2]= '$' ;// move chest
                        playingField[ppos[0]][ppos[1]-1] = '@';//move player
                        playingField[ppos[0]][ppos[1]] = '.'; //free old position
                        b= true;
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dir);
        }
    return  b;
    }

}