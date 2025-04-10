package modele.strategies.playerstrategies;

import java.util.List;

import modele.Grid;
import playerModele.Player;
import modele.gameElements.powerups.*;
import modele.gameElements.weapons.*;

/**
 * Classe abstraite définissant les stratégies que les joueurs peuvent adopter.
 * Cette classe fournit des méthodes utilitaires pour calculer les mouvements et localiser
 * les éléments les plus proches (ennemis, bonus, bombes, etc.) sur la grille.
 */
public abstract class PlayerStrategy {

    /**
     * Méthode abstraite définissant la logique de mouvement d'un joueur.
     * Doit être implémentée par chaque classe concrète de stratégie.
     *
     * @param player Le joueur qui suit cette stratégie.
     * @param grid   La grille de jeu contenant tous les éléments (joueurs, power-ups, obstacles...).
     */
    public abstract void computeMove(Player player, Grid grid);

    /**
     * Trouve le bonus (PowerUp) le plus proche du joueur.
     *
     * @param player Le joueur qui cherche le bonus.
     * @param grid   La grille contenant tous les bonus.
     * @return Le bonus le plus proche ou {@code null} s'il n'y a aucun bonus.
     */
    protected PowerUp findNearestPowerUp(Player player, Grid grid) {
        List<PowerUp> powerUps = grid.getPowerUp();
        PowerUp closest = null;
        int minDistance = Integer.MAX_VALUE;

        for (PowerUp powerUp : powerUps) {
            int distance = Math.abs(powerUp.getX() - player.getX()) + Math.abs(powerUp.getY() - player.getY());
            if (distance < minDistance) {
                minDistance = distance;
                closest = powerUp;
            }
        }
        return closest;
    }

    /**
     * Trouve l'ennemi le plus proche du joueur.
     *
     * @param player Le joueur cherchant un ennemi.
     * @param grid   La grille contenant tous les ennemis.
     * @return L'ennemi le plus proche ou {@code null} s'il n'y a aucun ennemi.
     */
    protected Player findNearestEnemy(Player player, Grid grid) {
        List<Player> enemies = grid.getEnemies(player);
        Player closest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Player enemy : enemies) {
            int distance = Math.abs(enemy.getX() - player.getX()) + Math.abs(enemy.getY() - player.getY());
            if (distance < minDistance) {
                minDistance = distance;
                closest = enemy;
            }
        }
        return closest;
    }

    /**
     * Trouve la bombe la plus proche du joueur.
     *
     * @param player Le joueur cherchant une bombe.
     * @param grid   La grille contenant toutes les bombes.
     * @return La bombe la plus proche ou {@code null} s'il n'y a aucune bombe.
     */
    protected Bomb findNearestBomb(Player player, Grid grid) {
        List<Bomb> bombs = grid.getBombsForPlayers();
        Bomb closest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Bomb bomb : bombs) {
            int distance = Math.abs(bomb.getX() - player.getX()) + Math.abs(bomb.getY() - player.getY());
            if (distance < minDistance) {
                minDistance = distance;
                closest = bomb;
            }
        }
        return closest;
    }

    /**
     * Trouve la mine la plus proche du joueur.
     *
     * @param player Le joueur cherchant une mine.
     * @param grid   La grille contenant toutes les mines.
     * @return La mine la plus proche ou {@code null} s'il n'y a aucune mine.
     */
    protected Mine findNearestMine(Player player, Grid grid) {
        List<Mine> mines = grid.getMinesForPlayers();
        Mine closest = null;
        int minDistance = Integer.MAX_VALUE;

        for (Mine mine : mines) {
            int distance = Math.abs(mine.getX() - player.getX()) + Math.abs(mine.getY() - player.getY());
            if (distance < minDistance) {
                minDistance = distance;
                closest = mine;
            }
        }
        return closest;
    }

    /**
     * Déplace le joueur vers une position cible.
     *
     * @param player   Le joueur à déplacer.
     * @param targetX  La position cible en X.
     * @param targetY  La position cible en Y.
     */
    protected void moveTowards(Player player, int targetX, int targetY) {
        if (player.getX() < targetX) {
            player.moveRight();
        } else if (player.getX() > targetX) {
            player.moveLeft();
        } else if (player.getY() < targetY) {
            player.moveDown();
        } else if (player.getY() > targetY) {
            player.moveUp();
        }
    }

    /**
     * Déplace le joueur à l'opposé d'une position cible (éloignement).
     *
     * @param player   Le joueur à déplacer.
     * @param targetX  La position cible en X.
     * @param targetY  La position cible en Y.
     */
    protected void moveAway(Player player, int targetX, int targetY) {
        if (player.getX() < targetX) {
            player.moveLeft();
        } else if (player.getX() > targetX) {
            player.moveRight();
        } else if (player.getY() < targetY) {
            player.moveUp();
        } else if (player.getY() > targetY) {
            player.moveDown();
        }
    }
}
