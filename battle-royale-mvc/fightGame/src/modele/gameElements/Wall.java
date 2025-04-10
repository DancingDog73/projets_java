package modele.gameElements;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.GameElement;

/**
 * Classe représentant un mur dans le jeu.
 * Un mur est un élément de jeu immobile qui bloque le déplacement des joueurs et des explosions.
 */
public class Wall extends GameElement {

    /**
     * Constructeur de la classe Wall.
     * Initialise la position du mur sur la grille.
     *
     * @param x La position X du mur sur la grille.
     * @param y La position Y du mur sur la grille.
     */
    public Wall(int x, int y) {
        super(x, y, Wall.loadImage());
    }

    public static Image loadImage() {
        try {
            return ImageIO.read(new File("resources/images/wall.png"));
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    /**
     * Retourne une représentation textuelle du mur.
     *
     * @return Une chaîne de caractères indiquant qu'il s'agit d'un mur.
     */
    public String toString() {
        return "Wall";
    } 
}
