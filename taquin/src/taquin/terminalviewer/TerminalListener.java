package taquin.terminalviewer;

import taquin.model.*;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TerminalListener{
    private GameModel<E> gameModel = new GameModel(new ArrayList<> (List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)));
    private int[] currentElem = new int[]{0, 0};
    public TerminalListener(GameModel<E> gameModel){
        this.gameModel = gameModel;
        this.currentElem = currentElem;
        
    }

    public int getKey(){
        try{ 
            int currentChar = System.in.read();
            return currentChar;
        } catch(IOException e ){}

        return -1;
    }

    public void moveCurrentElement(){
        System.out.println(this.gameModel);
        while(!this.gameModel.isSolved()){ 
            int move = this.getKey();
            if (move == 122 && !(this.gameModel.up(this.gameModel.ground[this.currentElem[0]][this.currentElem[1]]).equals(new Object[]{}))){
                int[] upPos = this.gameModel.up(this.gameModel.ground[this.currentElem[0]][this.currentElem[1]]);
                System.out.println("Ancienne Position : " + Arrays.toString(this.currentElem));
                System.out.println("Nouvelle Position : " + Arrays.toString(upPos));
                
                this.currentElem = upPos;
                if (this.gameModel.canPieceBeMoved(this.gameModel.ground[currentElem[0]][currentElem[1]])){
                    
                    this.gameModel.move(this.currentElem);
                }
                
                System.out.println(this.gameModel);

            } else if (move == 115 && !(this.gameModel.down(this.gameModel.ground[this.currentElem[0]][this.currentElem[1]]).equals(new Object[]{}))){
                int[] pos = this.gameModel.down(this.gameModel.ground[this.currentElem[0]][this.currentElem[1]]);
                System.out.println("Ancienne Position : " + Arrays.toString(this.currentElem));
                System.out.println("Nouvelle Position : " + Arrays.toString(pos));
                this.currentElem = pos;
                if (this.gameModel.canPieceBeMoved(this.gameModel.ground[currentElem[0]][currentElem[1]])){
                    this.gameModel.move(this.gameModel.ground[currentElem[0]][currentElem[1]]);
                }
                //this.currentElem = pos;
                System.out.println(this.gameModel);
            
            } else if (move == 113 && !(this.gameModel.left(this.gameModel.ground[this.currentElem[0]][this.currentElem[1]]).equals(new Object[]{}))){
                int[] pos = this.gameModel.left(this.gameModel.ground[this.currentElem[0]][this.currentElem[1]]);
                System.out.println("Ancienne Position : " + Arrays.toString(this.currentElem));
                System.out.println("Nouvelle Position : " + Arrays.toString(pos));
                System.out.println(this.gameModel); 
                this.currentElem = pos;
                if (this.gameModel.canPieceBeMoved(this.gameModel.ground[currentElem[0]][currentElem[1]])){
                    this.gameModel.move(this.gameModel.ground[currentElem[0]][currentElem[1]]);
                }
                //this.currentElem = pos;
                System.out.println(this.gameModel);
            } else if (move == 100 && !(this.gameModel.right(this.gameModel.ground[this.currentElem[0]][this.currentElem[1]]).equals(new Object[]{}))){
                int[] pos = this.gameModel.right(this.gameModel.ground[this.currentElem[0]][this.currentElem[1]]);
                System.out.println("Ancienne Position : " + Arrays.toString(this.currentElem));
                System.out.println("Nouvelle Position : " + Arrays.toString(pos));
                System.out.println(this.gameModel);
                
                this.currentElem = pos;
                if (this.gameModel.canPieceBeMoved(this.gameModel.ground[currentElem[0]][currentElem[1]])){
                    this.gameModel.move(this.gameModel.ground[currentElem[0]][currentElem[1]]);
                }
                //this.currentElem = pos;
                System.out.println(this.gameModel);
            }

            
        }

        System.out.println("Yeeep, Résolu champion");

    }
    public static void main(String[] args){
        TerminalListener tl = new TerminalListener();
        //System.out.println(tl.getKey());
        tl.moveCurrentElement();


    }


}