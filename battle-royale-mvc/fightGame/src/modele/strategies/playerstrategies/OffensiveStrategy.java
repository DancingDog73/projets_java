package modele.strategies.playerstrategies;

import java.util.Random;

import modele.Grid;
import modele.gameElements.powerups.PowerUp;
import modele.gameElements.weapons.Bomb;
import playerModele.Player;

/**
 * Stratégie offensive pour un joueur.
 * Cette stratégie privilégie les actions d'attaque envers les ennemis proches, 
 * la collecte de power-ups, l'évitement des bombes, et le placement d'explosifs.
 */
public class OffensiveStrategy extends PlayerStrategy {

    /**
     * Calcule et exécute le prochain mouvement du joueur selon une stratégie offensive.
     *
     * @param player Le joueur contrôlé par cette stratégie.
     * @param grid   La grille contenant tous les éléments du jeu.
     */
    @Override
    public void computeMove(Player player, Grid grid) {
        Random random = new Random();

        // Étape 1 : Chercher l'ennemi le plus proche
        Player target = findNearestEnemy(player, grid);

        // Si un ennemi est à portée et sur une même ligne/colonne, tirer
        if (target != null && player.getAmmo() > 0) {
            if (target.getX() == player.getX() && Math.abs(target.getY() - player.getY()) <= player.getRange()) {
                player.shootVertical(); // Tir vertical vers l'ennemi
                return;
            } else if (target.getY() == player.getY() && Math.abs(target.getX() - player.getX()) <= player.getRange()) {
                player.shootHorizontal(); // Tir horizontal vers l'ennemi
                return;
            }
        }

        // Étape 2 : Activer le bouclier si l'énergie est basse
        if (player.getEnergy() < 50) {
            player.activateShield();
        }

        // Étape 3 : Éviter les bombes proches
        Bomb bomb = findNearestBomb(player, grid);
        if (bomb != null) {
            int distance = Math.abs(bomb.getX() - player.getX()) + Math.abs(bomb.getY() - player.getY());
            if (distance <= 2) {
                moveAway(player, bomb.getX(), bomb.getY()); // Éviter la bombe
                return;
            }
        }

        // Étape 4 : Se déplacer vers le power-up le plus proche
        PowerUp targetPowerUp = findNearestPowerUp(player, grid);
        if (targetPowerUp != null) {
            moveTowards(player, targetPowerUp.getX(), targetPowerUp.getY());
            return;
        }

        // Étape 5 : Placer un explosif aléatoire (bombe ou mine) dans une direction aléatoire
        String[] explosives = {"Bomb", "Mine"};
        player.placeExplosive(explosives[random.nextInt(explosives.length)], random.nextInt(8)); // 8 directions possibles
    }
}
