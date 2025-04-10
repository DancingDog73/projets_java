package modele.strategies.playerstrategies;

import playerModele.*;
import modele.Grid;

/**
 * Classe représentant un joueur utilisant une stratégie spécifique pour déterminer ses actions.
 * Cette classe permet au joueur d'effectuer des actions selon la stratégie qui lui est attribuée.
 */
public class PlayerWithStrategy extends Player {

    private PlayerStrategy playerStrategy; // La stratégie du joueur
    private Grid grid; // La grille de jeu sur laquelle le joueur évolue

    /**
     * Constructeur pour initialiser un joueur avec une stratégie.
     * 
     * @param x La position X du joueur.
     * @param y La position Y du joueur.
     * @param name Le nom du joueur.
     * @param energy L'énergie initiale du joueur.
     * @param ammo Le nombre de munitions initial du joueur.
     * @param numberOfBomb Le nombre de bombes initial du joueur.
     * @param numberOfMine Le nombre de mines initial du joueur.
     * @param range La portée de l'attaque du joueur.
     * @param playerStrategy La stratégie du joueur qui déterminera ses actions.
     */
    public PlayerWithStrategy(int x, int y, String name, int energy, int ammo, int numberOfBomb, int numberOfMine, int range, PlayerStrategy playerStrategy) {
        super(x, y, name, energy, ammo, numberOfBomb, numberOfMine, range);
        this.playerStrategy = playerStrategy;
        this.grid = null;
    }

    /**
     * Définit la grille de jeu sur laquelle le joueur évolue.
     * 
     * @param grid La grille de jeu à assigner au joueur.
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /**
     * Effectue le tour du joueur en utilisant sa stratégie pour calculer le mouvement.
     * Cette méthode invoque la méthode `computeMove` de la stratégie assignée au joueur.
     */
    @Override
    public void takeTurn() {
        this.playerStrategy.computeMove(this, grid);
    }
}
