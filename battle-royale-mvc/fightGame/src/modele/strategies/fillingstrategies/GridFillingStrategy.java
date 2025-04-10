package modele.strategies.fillingstrategies;

import modele.Grid;

/**
 * Interface GridFillingStrategy.
 * Définit une stratégie pour remplir une grille (Grid) avec des éléments spécifiques
 * tels que des murs, des bonus, ou d'autres objets selon une logique donnée.
 */
public interface GridFillingStrategy {
    
    /**
     * Remplit la grille spécifiée en fonction de la stratégie implémentée.
     *
     * @param grid la grille à remplir.
     */
    void fillGrid(Grid grid);
}
