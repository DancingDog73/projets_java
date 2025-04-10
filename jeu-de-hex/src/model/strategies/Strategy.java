package model.strategies;

import model.game.*;
import model.player.*;

/**
 * Interface représentant une stratégie de jeu.
 */
public interface Strategy {
     /**
     * Trouve le meilleur coup à jouer pour un joueur donné, en fonction de l'état actuel du jeu.
     * 
     * @param initialState L'état actuel du jeu.
     * @param player       Le joueur pour lequel trouver un coup.
     * @param opponent     L'adversaire du joueur.
     * @return Le meilleur coup à jouer selon la stratégie.
     */
    public Move findBestMove(State initialState, Player player, Player opponent);
    
    /**
     * Retourne le nom de la stratégie.
     * 
     * @return Le nom de la stratégie (par exemple, "Random", "MCTS", etc.).
     */
    public String getName();

    /**
     * Retourne la graine utilisée pour initialiser le générateur aléatoire.
     * 
     * @return La graine utilisée pour la génération de nombres aléatoires.
     */
    public long getRandomSeed();
}