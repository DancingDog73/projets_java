package modele.gameElements.powerups;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



/**
 * Power-up qui augmente les munitions du joueur qui le ramasse.
 * 
 */
public class Ammunition extends PowerUp{


    /**
     * Constructeur pour une recharge de munitions.
     *
     * @param x la position X du power-up sur la grille.
     * @param y la position Y du power-up sur la grille.
     */
    public Ammunition(int x, int y){
        super(x,y, Ammunition.loadImage());
    }

    public static Image loadImage() {
        try {
            return ImageIO.read(new File("resources/images/ammo.png"));
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    
}