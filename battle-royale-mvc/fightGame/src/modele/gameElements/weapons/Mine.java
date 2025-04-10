package modele.gameElements.weapons;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import playerModele.Player;

/**
 * Classe représentant une mine dans le jeu.
 * La mine est un type d'explosif qui inflige des dégâts aux joueurs lorsqu'elle explose.
 */
public class Mine extends Explosive {

    private Player playerImpact;

    /**
     * Constructeur de la classe Mine.
     * Une mine est initialisée avec des dégâts de 25 et n'est pas visible pour les autres joueurs par défaut.
     *
     * @param x La position X de la mine sur la grille.
     * @param y La position Y de la mine sur la grille.
     */
    public Mine(int x, int y) {
        super(x, y, Mine.loadImage(), 25, false);
    }

    public static Image loadImage() {
        try {
            return ImageIO.read(new File("resources/images/mine.png"));
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public void setPlayerImpact(Player player ) {
        this.playerImpact = player;
    }

    public Player getPlayerImpact() {
        return this.playerImpact;
    }

    public String toString() {
        return "Mine{" +
                "position=(" + getX() + ", " + getY() + ")" +
                ", visibleToAll=" + visibleToAll +
                '}';

    }
}