package model.player;
import model.strategies.RandomStrategy;

/**
 * Classe représentant un joueur utilisant une stratégie aléatoire.
 * Cette classe hérite de PlayerAbstract et applique la stratégie RandomStrategy.
 */
public class PlayerRandom extends PlayerAbstract{

    /**
     * Constructeur de PlayerRandom.
     * Initialise un joueur avec la stratégie aléatoire par défaut.
     */
    public PlayerRandom() {
        super(new RandomStrategy());
    }

    
}
