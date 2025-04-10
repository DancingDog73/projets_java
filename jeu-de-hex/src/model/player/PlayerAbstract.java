package model.player;
import model.game.*;
import model.strategies.RandomStrategy;
import model.strategies.Strategy;
import model.strategies.mcts.MCTS;




/**
 * Classe abstraite représentant un joueur dans le jeu.
 * Implémente l'interface Player et fournit des fonctionnalités de base partagées
 * par toutes les implémentations de joueurs.
 */
public abstract class PlayerAbstract implements Player {

    private static int playerCount = 0;
    private Strategy strategy;
    private int id;

    /**
     * Constructeur de PlayerAbstract.
     * Initialise le joueur avec une stratégie donnée. Si la stratégie est null,
     * une stratégie aléatoire par défaut est utilisée.
     * 
     * @param strategy La stratégie utilisée par le joueur.
     */
    public PlayerAbstract(Strategy strategy) {
        this.strategy = strategy;
        if (playerCount >= 2) {
            playerCount = 0; // Réinitialise le compteur si on dépasse 2 joueurs
        }
        playerCount++; 
        this.id = playerCount; 
        if(playerCount >= 2){
            resetPlayerCount();
        }
    }

    /**
     * Constructeur alternatif permettant de spécifier directement l'identifiant du joueur.
     * 
     * @param strategy La stratégie utilisée par le joueur.
     * @param id       L'identifiant unique du joueur.
     */
    public PlayerAbstract(Strategy strategy, int id) {
        this.strategy = strategy;
        this.id = id; 
        
    }

    /**
     * Réinitialise le compteur de joueurs à zéro.
     */
    public static void resetPlayerCount() {
        playerCount = 0;
    }


     /**
     * Joue un coup en utilisant la stratégie associée au joueur.
     * 
     * @param initialState L'état initial du jeu.
     * @param opponent      L'adversaire du joueur.
     * @return Le meilleur coup à jouer selon la stratégie.
     */
    @Override
    public Move play(State initialState, Player opponent) {
        return strategy.findBestMove(initialState, this, opponent);
    }

    /**
     * Retourne l'identifiant unique du joueur.
     * 
     * @return L'identifiant du joueur.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Vérifie si deux objets représentent le même joueur.
     * Deux joueurs sont considérés égaux s'ils ont le même identifiant.
     * 
     * @param o L'objet à comparer avec ce joueur.
     * @return true si l'objet est égal à ce joueur, sinon false.
     */
    @Override
    public boolean equals(Object o) {
        // Vérifie si l'objet est un entier correspondant à l'ID du joueur.
        return this.id == (int) o;
    }

    /**
     * Retourne le nom de la stratégie utilisée par le joueur.
     * 
     * @return Le nom de la stratégie.
     */
    @Override
    public String getStrategy(){
        return this.strategy.getName();
    }


    /**
     * Retourne le budget de simulations si la stratégie est une MCTS.
     * 
     * @return Le nombre de simulations pour la stratégie MCTS, ou 0 si ce n'est pas le cas.
     */
    @Override
    public int getBudget(){
        if(strategy instanceof MCTS){
            return ((MCTS) strategy).getSimulations();
        }
        return 0;
    }


     /**
     * Retourne le nombre de nœuds explorés si la stratégie est une MCTS.
     * 
     * @return Le nombre de nœuds explorés, ou 0 si la stratégie n'est pas une MCTS.
     */
    @Override
    public int getNodeCount(){
        if(strategy instanceof MCTS){
            return ((MCTS) strategy).getNodeCount();
        }
        return 0;
    }


    /**
     * Retourne la graine aléatoire utilisée par la stratégie du joueur.
     * 
     * @return La graine aléatoire utilisée.
     */
    @Override
    public long getRandomSeed(){
        return strategy.getRandomSeed();
    }


    /**
     * Indique si la stratégie utilise l'optimisation RAVE.
     * 
     * @return true si la stratégie utilise RAVE, false sinon.
     */
    @Override 
    public boolean isUsingRave(){
        if(strategy instanceof RandomStrategy){
            return false;
        }
        if(!((MCTS) strategy).isUsingRave()){
            return false;
        }
        return true;
    } 

}
