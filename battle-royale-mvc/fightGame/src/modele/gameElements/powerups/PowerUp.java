package modele.gameElements.powerups;


import java.awt.Image;


import util.GameElement;


/**
 * Classe abstraite représentant un power-up dans le jeu.
 * Un power-up peut être ramassé par un joueur pour lui donner un effet temporaire ou permanent.
 */
public abstract class PowerUp extends GameElement{

    /**
     * Constructeur pour un power-up.
     *
     * @param x la position X du power-up sur la grille.
     * @param y la position Y du power-up sur la grille.
     */
    public PowerUp(int x, int y, Image image){
        super(x, y, image);
    }

    

    
}
