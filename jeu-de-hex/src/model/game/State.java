package model.game;
import model.player.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente l'état actuel du jeu, y compris la grille et les actions possibles.
 */
public class State {

    /**
     * La grille associée à cet état.
     */
    private Grid grid;

    /**
     * Constructeur par défaut de la classe State.
     * Initialise une nouvelle grille vide.
     */
    public State() {
        this.grid = new Grid();
    }

    /**
     * Constructeur qui permet de créer un état avec une grille existante.
     * 
     * @param grid La grille associée à cet état.
     */
    public State(Grid grid) {
        this.grid = grid;
    }

    /**
     * Génère un nouvel état basé sur un mouvement donné.
     * 
     * @param move Le mouvement à appliquer à l'état courant.
     * @return Un nouvel état résultant de l'application du mouvement.
     */
    public State next(Move move) {
        Grid newGrid = this.grid.clone();
        newGrid.toggleCell(move.getPosition()[0], move.getPosition()[1], move.getPlayer().getId());
        return new State(newGrid);
    }

    /**
     * Vérifie si le jeu est terminé et détermine le gagnant.
     * 
     * @return 1 si le joueur 1 a gagné, 2 si le joueur 2 a gagné, ou 0 si le jeu n'est pas terminé.
     */
    public int isFinished() {
        int winner = 0;
        if (grid.hasPlayerWon(1)) {
            winner = 1;
        } else if (grid.hasPlayerWon(2)) {
            winner = 2;
        }

        return winner; 
    }

    /**
     * Retourne la grille associée à cet état.
     * 
     * @return La grille de cet état.
     */
    public Grid getGrid() {
        return this.grid;
    }

    /**
     * Génère la liste des mouvements possibles pour un joueur donné.
     * 
     * @param player Le joueur pour lequel calculer les mouvements possibles.
     * @return Une liste de mouvements possibles pour le joueur.
     */
    public List<Move> getMoves(Player player) {
        List<Move> moves = new ArrayList<>();
        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                if (this.getGrid().getGrid()[y][x] == 0) { // Si la cellule est vide.
                    moves.add(new Move(player, new int[]{x, y}));
                }
            }
        }
        return moves;
    }

    public int getGridSize(){
        return this.grid.getSize();
    }
}
