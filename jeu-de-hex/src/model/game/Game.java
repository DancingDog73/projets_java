package model.game;
import model.player.*;
import java.util.*;
import model.result.*;

import listeners.*;


/**
 * La classe Game représente un jeu générique où deux joueurs s'affrontent dans des états successifs.
 * Elle gère les joueurs, les états du jeu, les mouvements possibles, ainsi que le déroulement de la partie.
*/
public class Game extends AbstractListenableModel {

    private State currentState;
    private List<State> states;
    private Player playerOne;
    private Player playerTwo;
    private Player currentPlayer;
    private Player winner;
    private int moveCount;
    private GameResult result;
    private boolean printGrid;
    
    /**
     * Constructeur de la classe Game.
     * Initialise les joueurs playerOne et playerTwo, l'état initial.
     * 
     * @param playerOne Le premier joueur
     * @param playerTwo Le deuxième joueur
     * @param initialState L'état initial du jeu
     */
    public Game(Player playerOne, Player playerTwo, State initialState) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentPlayer = playerOne;
        this.currentState = initialState;
        this.states = new ArrayList<>(); this.states.add(initialState);
        this.moveCount = 0;
        this.result = null;
        this.printGrid = false;
    }

     /**
     * Récupère tous les mouvements possibles pour le joueur actuel.
     * Les mouvements correspondent aux cases vides sur la grille.
     * 
     * @return Une liste de mouvements possibles
     */
    public List<Move> getMoves(){
        int size = currentState.getGridSize();
        List<Move> moves = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (currentState.getGrid().getGrid()[y][x] == 0) {
                    moves.add(new Move(currentPlayer, new int[]{x, y}));
                }
            }
        }
        return moves;
    }

    /**
     * Récupère le joueur dont c'est actuellement le tour de jouer.
     * 
     * @return Le joueur actuel
     */
    public Player getPlayer(){
        return currentPlayer;
    }

     /**
     * Récupère l'adversaire du joueur actuel.
     * 
     * @return Le joueur adverse
     */
    public Player getOpponent(){
        if(currentPlayer.equals(1)){
            return playerTwo;
        }
        return playerOne;
    }

    /**
     * Change le joueur actuel pour passer au joueur suivant.
     */
    private void togglePlayer() {
        currentPlayer = (currentPlayer.equals(1)) ? playerTwo : playerOne;
    }

     

    /**
     * Méthode qui permet aux joueurs de jouer une partie normale à tour de rôle.
     * Les mouvements sont fournis par les stratégies des joueurs.
     */
    public void play(){
        long startTime = System.nanoTime(); 
        while (currentState.isFinished() == 0) {
            Player player = getPlayer();
            Move move = player.play(currentState, getOpponent());
            states.add(currentState);
            if(printGrid){
                System.out.println(currentState.getGrid());
            }
            currentState  = currentState.next(move);
            moveCount++;
            togglePlayer();
            this.firechangement();
        }
        long duration = System.nanoTime() - startTime;
        togglePlayer();
        this.winner = currentPlayer;
        this.result = new GameResult(this.currentState.getGrid().getSize(), playerOne.getBudget(), playerTwo.getBudget(), winner.getId(), duration, this.moveCount, playerOne.getStrategy(), playerTwo.getStrategy(), playerOne.getNodeCount(), playerTwo.getNodeCount(), playerOne.isUsingRave(), playerTwo.isUsingRave(), playerOne.getRandomSeed(), playerTwo.getRandomSeed());
        this.firechangement();
       
    }

    /**
     * Méthode qui permet de jouer le jeu tour par tour manuellement.
     * Les mouvements sont fournis par les stratégies des joueurs.
     */
    public void next() {
        if (currentState.isFinished() == 0) {
            Player player = getPlayer();
            Move move = player.play(currentState, getOpponent());
            states.add(currentState);
            currentState  = currentState.next(move);
            togglePlayer();
        } else {
            togglePlayer();
            this.winner = currentPlayer;
        }
        if (currentState.isFinished() != 0) {
            togglePlayer();
            this.winner = currentPlayer;
        }
        this.firechangement();
    }

     /**
     * Récupère l'état actuel du jeu.
     * 
     * @return L'état actuel
     */
    public State getCurrentState() {
        return this.currentState;
    }

    
    /**
     * Récupère le gagnant de la partie.
     * 
     * @return Le joueur gagnant
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Définit un nouvel état courant pour le jeu.
     * 
     * @param currentState Le nouvel état
     */
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
        this.firechangement();
    }

    /**
     * Reinitialise le jeu
     * 
     */
    public void reset() {
        this.setCurrentState(this.states.get(0));
        this.states.clear(); this.states.add(this.currentState);
    }

    /**
     * Retourne en arriere
     * @return
     */
    public void previous() {
        this.setCurrentState(this.states.remove(this.states.size() - 1));
        this.firechangement();
    }

    /**
     * Retourne le résultat d'une partie 
     * @return Le résultat de la partie
     */
    public GameResult getGameResult(){
        return this.result;
    }


    /**
     * Active ou désactive l'affichage de la grille dans le terminal
     */
    public void togglePrintGrid(){
        this.printGrid = !this.printGrid;
    }

    public void setPlayerOne(Player player) {
        this.playerOne = player;
    }

    public void setPlayerTwo(Player player) {
        this.playerTwo = player;
    }


}