package model.strategies;
import model.game.*;
import model.player.*;


import java.util.*;

/**
 * Implémente une stratégie aléatoire pour choisir un coup dans le jeu.
 * Cette stratégie sélectionne un coup valide au hasard parmi les options possibles.
 */
public class RandomStrategy implements Strategy{

    private long randomSeed;
    private Random random;
    private static String name = "Random";

    public RandomStrategy(){
        this.randomSeed = System.currentTimeMillis();
        this.random = new Random(randomSeed);
    }


    /**
     * Trouve le meilleur coup selon la stratégie aléatoire.
     * 
     * @param initialState L'état initial du jeu.
     * @param player       Le joueur pour lequel trouver un coup.
     * @param opponent     L'adversaire du joueur.
     * @return Un coup choisi aléatoirement parmi les coups possibles.
     */
    @Override
    public Move findBestMove(State initialState, Player player, Player opponent){
        List<Move> moves = initialState.getMoves(player);
        int randomIndex = random.nextInt(moves.size()); 
        return moves.get(randomIndex); 
    }

    /**
     * Retourne le nom de la stratégie.
     * 
     * @return Le nom de la stratégie ("Random").
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retourne la graine utilisée pour initialiser le générateur aléatoire.
     * 
     * @return La graine du générateur aléatoire.
     */
    @Override
    public long getRandomSeed(){
        return this.randomSeed;
    }


}