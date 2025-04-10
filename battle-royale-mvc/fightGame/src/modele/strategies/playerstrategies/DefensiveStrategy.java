package modele.strategies.playerstrategies;

import modele.Grid;
import modele.gameElements.weapons.Bomb;
import playerModele.Player;

/**
 * Stratégie défensive pour un joueur.
 * Cette stratégie privilégie la survie en évitant les ennemis et les bombes,
 * tout en activant le bouclier lorsque l'énergie est faible.
 */
public class DefensiveStrategy extends PlayerStrategy {

    /**
     * Calcule et exécute le prochain mouvement du joueur selon une stratégie défensive.
     *
     * @param player Le joueur contrôlé par cette stratégie.
     * @param grid   La grille contenant tous les éléments du jeu.
     */
    @Override
    public void computeMove(Player player, Grid grid) {
        // Étape 1 : Chercher l'ennemi le plus proche
        Player nearestEnemy = findNearestEnemy(player, grid);

        // Si un ennemi est trop proche, s'éloigner
        if (nearestEnemy != null && isEnemyTooClose(player, nearestEnemy)) {
            moveAway(player, nearestEnemy.getX(), nearestEnemy.getY()); // Éviter l'ennemi
            return;
        }

        // Étape 2 : Activer le bouclier si l'énergie est faible
        if (player.getEnergy() < 50) {
            player.activateShield(); // Activer le bouclier pour la protection
        }

        // Étape 3 : Éviter les bombes proches
        Bomb bomb = findNearestBomb(player, grid);
        if (bomb != null) {
            int distance = Math.abs(bomb.getX() - player.getX()) + Math.abs(bomb.getY() - player.getY());
            if (distance <= 2) {
                moveAway(player, bomb.getX(), bomb.getY()); // S'éloigner de la bombe
            }
        }
    }

    /**
     * Vérifie si un ennemi est trop proche du joueur (moins de 3 cases de distance).
     *
     * @param player Le joueur contrôlé par cette stratégie.
     * @param enemy  L'ennemi à vérifier.
     * @return True si l'ennemi est trop proche, sinon False.
     */
    private boolean isEnemyTooClose(Player player, Player enemy) {
        int distance = Math.abs(enemy.getX() - player.getX()) + Math.abs(enemy.getY() - player.getY());
        return distance <= 2; // Distance rapprochée : moins de 3 cases
    }
}
