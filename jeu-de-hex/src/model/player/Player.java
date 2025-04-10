package model.player;
import model.game.*;


/**
 * Interface représentant un joueur dans le jeu.
 * Définit les méthodes essentielles que tout joueur doit implémenter.
 */
public interface Player {
    /**
     * Joue un coup en fonction de l'état actuel du jeu et de l'adversaire.
     * 
     * @param initialState L'état initial du jeu.
     * @param opponent L'adversaire du joueur.
     * @return Le meilleur coup à jouer sous forme d'un objet Move.
     */
    public Move play(State initialState, Player opponent);
    
    /**
     * Retourne l'identifiant unique du joueur.
     * 
     * @return L'ID du joueur.
     */
    public int getId();
    
    /**
     * Retourne le budget du joueur (utilisé pour certaines stratégies comme MCTS).
     * 
     * @return Le budget du joueur (nombre de simulations, par exemple).
     */
    public int getBudget();
    
    
    /**
     * Retourne le nombre de nœuds explorés par le joueur (pour MCTS).
     * 
     * @return Le nombre de nœuds explorés.
     */
    public int getNodeCount();
    
    /**
     * Retourne le nom de la stratégie utilisée par le joueur.
     * 
     * @return Le nom de la stratégie sous forme de chaîne de caractères.
     */
    public String getStrategy();
    
     /**
     * Retourne la graine aléatoire utilisée par la stratégie du joueur.
     * 
     * @return La valeur de la graine aléatoire.
     */
    public long getRandomSeed();
    
    /**
     * Indique si la stratégie MCTS du joueur utilise l'optimisation RAVE.
     * 
     * @return true si RAVE est activé, false sinon.
     */
    public boolean isUsingRave();
}