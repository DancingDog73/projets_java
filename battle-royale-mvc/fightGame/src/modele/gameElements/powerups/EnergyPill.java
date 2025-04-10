package modele.gameElements.powerups;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;





/**
 * Power-up qui augmente le niveau d'énergie du joueur qui le ramasse.
 * 
 */
public class EnergyPill extends PowerUp{


    /**
     * Constructeur pour une pastille d'énergie.
     *
     * @param x la position X du power-up sur la grille.
     * @param y la position Y du power-up sur la grille.
     */
    public EnergyPill(int x, int y){
        super(x, y, EnergyPill.loadImage());
    }

    public static Image loadImage() {
        try {
            return ImageIO.read(new File("resources/images/energy_pill.png"));
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    

    
}