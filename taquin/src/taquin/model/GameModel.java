package taquin.model;


import java.util.*;
import java.lang.*;
import util.mvc.AbstractListenable;



public class GameModel<E> extends AbstractListenable {

    
    private int length;
    public E[][] ground;
    private E blank;
    private List<E> identity;

    
    public GameModel (List<E> pieces) {

        this.length = (int)Math.sqrt(pieces.size());
        this.ground = (E[][]) new Object[length][length];
        this.layThePieces(pieces);
        this.blank = pieces.get(pieces.size()-1);
        this.ground[length-1][length-1] = this.blank;

        
        this.identity = this.getPieces();        
        this.shuffle();

    }


    public void layThePieces (List<E> pieces) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                this.ground[i][j] = pieces.get(i*length+j);
            }
        }
    }

    
    public void shuffle () {
        
        for (int i = 0; i < Math.pow(this.length*this.length,3); i++) {

            List<int[]> neighbors = this.getNeighbors(this.blank);            
            int[] randomPosition = neighbors.get(new Random().nextInt(neighbors.size()));
            this.move(this.ground[randomPosition[0]][randomPosition[1]]);

        }

    }
    
    
    public List<E> getPieces () {

        List<E> pieces = new ArrayList<>();

        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.length; j++) {
                pieces.add(this.ground[i][j]);
            }
        }

        return pieces;

    }
    
    
    public boolean isPieceBlank (E piece) {
        return piece == this.blank;
    }

    
    public int[] getPosition (E piece) {

        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.length; j++) {
                if (this.ground[i][j] == piece) {
                    return new int[]{i,j};
                }
            }
        }

        return new int[]{};

    }


    public int[] up(E piece){

        int[] piecePosition = this.getPosition(piece);
        if (piecePosition.length != 0 && piecePosition[0]-1 >= 0) {
            return new int[]{piecePosition[0]-1, piecePosition[1]};
        }
        return new int[]{};

    }

    public int[] left(E piece){

        int[] piecePosition = this.getPosition(piece);
        if (piecePosition.length != 0 && piecePosition[1]-1 >= 0) {
            return new int[]{piecePosition[0], piecePosition[1]-1};
        }
        return new int[]{};

    }

    public int[] down(E piece){

        int[] piecePosition = this.getPosition(piece);
        if (piecePosition.length != 0 && piecePosition[0]+1 < this.length) {
            return new int[]{piecePosition[0]+1, piecePosition[1]};
        }
        return new int[]{};

    }


    public int[] right(E piece){

        int[] piecePosition = this.getPosition(piece);
        if (piecePosition.length != 0 && piecePosition[1]+1 < this.length) {
            return new int[]{piecePosition[0], piecePosition[1]+1};
        }
        return new int[]{};

    }

    
    public List<int[]> getNeighbors(E piece){

        List<int[]> neighbors = new ArrayList<>();

        if (this.up(piece).length != 0){
            neighbors.add(this.up(piece));
        }
        if (this.down(piece).length != 0){
            neighbors.add(this.down(piece));
        }
        if(this.left(piece).length != 0){
            neighbors.add(this.left(piece));
        }
        if(this.right(piece).length != 0){
            neighbors.add(this.right(piece));
        }
        
        return neighbors;

    }


    public boolean canPieceBeMoved (E piece) {

        int[] positionOfBlank = this.getPosition(this.blank);
        int[] positionOfNumber = this.getPosition(piece);
        List<int[]> neighbors = this.getNeighbors(piece);

        for (int[] neighbor: neighbors){
            if(neighbor[0] == positionOfBlank[0] && neighbor[1] == positionOfBlank[1]){
                return true;
            }
        }
        return false;

    }

    
    public void move (E piece) {

        if (this.canPieceBeMoved(piece)) {

            List<E> pieces = this.getPieces();
            int[] piecePosition = this.getPosition(piece);
            int[] blankPosition = this.getPosition(this.blank);

            Collections.swap(pieces, piecePosition[0]*length+piecePosition[1], blankPosition[0]*length+blankPosition[1]);
            this.layThePieces(pieces);
            this.fireChanges();

        }

    }

    public E[][] getGround(){
        return this.ground;
    }

    public boolean isSolved(){
        return this.identity.equals(this.getPieces());
    }


    @Override
    public String toString () {
        String representation = "\n";
        for (E[] line : this.ground) {
            for (E column : line) {
                if (!this.isPieceBlank(column)) {
                    if (column instanceof Integer){
                        int col = (int) column;
                        if(length >= 10){
                            if(col < 10){
                                representation +=" "+ column + " ";
                            }
                            else{
                                representation += column + " ";
                            }
                        }
                        else if (length >= 100){
                            if(col < 10){
                                representation +="  "+ column + " ";
                            }
                            else if(col < 100){
                                representation += " "+column + " ";
                            }
                            else{
                                representation += column + " ";
                            }
                        }
                        else {
                            representation += column + " ";
                        }
                    }else {
                        representation += column + " ";
                    }
                    
                } else {
                    if (length >=10){
                        representation += "   ";
                    } else if (length >= 100){
                        representation += "    ";
                    }else {
                        representation += "  ";
                    }
                    
                }
            }
            representation += "\n";
        }
        return representation;
    }


    public static void main (String[] argv){
        List<Integer> l = new ArrayList<>();
        for(int i = 1; i < 101 ;i++){
            l.add(i);
        } 

        GameModel g = new GameModel(l);
        System.out.println(g);
        System.out.println(g.length);
    }
    

    
}