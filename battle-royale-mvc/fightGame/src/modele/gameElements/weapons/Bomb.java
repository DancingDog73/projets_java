package modele.gameElements.weapons;


import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Classe représentant une bombe dans le jeu.
 * La bombe est un explosif avec un minuteur qui inflige des dégâts aux joueurs lorsqu'elle explose.
 */
public class Bomb extends Explosive {
    private int timer;
    
    /**
     * Constructeur de la classe Bomb.
     * Une bombe est initialisée avec des dégâts de 10, une visibilité désactivée, et un minuteur par défaut de 5 tours.
     *
     * @param x La position X de la bombe sur la grille.
     * @param y La position Y de la bombe sur la grille.
     */
    public Bomb(int x, int y) {
        super(x, y, Bomb.loadImage(), 10, false);
        this.timer = 5;
    }

    public static Image loadImage() {
        try {
            return ImageIO.read(new File("resources/images/bomb.png"));
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    
    
    /**
     * Décrémente le minuteur de la bombe. 
     * Si le minuteur atteint zéro, la bombe est prête à exploser.
     */
    public void tick(){
        if(this.timer > 0)
            this.timer--;
    }

    /**
     * Vérifie si la bombe est prête à exploser.
     *
     * @return true si le minuteur est égal à zéro, sinon false.
     */
    public boolean isReadyToExplode(){
        return this.timer == 0;
    }

    /**
     * Retourne la valeur actuelle du minuteur de la bombe.
     *
     * @return Le nombre de tours restants avant l'explosion.
     */
    public int getTimer(){
        return this.timer;
    }

    /**
     * Modifie la valeur du minuteur de la bombe.
     *
     * @param newTimer La nouvelle valeur du minuteur.
     */
    public void setTimer(int newTimer){
        this.timer = newTimer;
    }

   

    @Override
    public String toString() {
        return "Bomb{" +
                "position=(" + getX() + ", " + getY() + ")" +
                ", timer=" + timer +
                ", visibleToAll=" + visibleToAll +
                '}';
    }
}
