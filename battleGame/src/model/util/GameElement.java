package util;


import java.awt.Image;
import util.mvc.*;


/**
 * Représente un élément du jeu, tel qu'un joueur, un objet ou un ennemi, qui a une position (x, y)
 * et une image associée. Cette classe sert de base pour les autres éléments du jeu et permet de 
 * manipuler leur position et leur image.
 * Elle étend la classe {@link AbstractModeleEcoutable}, qui permet à cette classe d'être écoutée
 * par un observateur dans un modèle MVC.
 */
public abstract class GameElement extends AbstractModeleEcoutable{
    protected int x; // Position en x de l'élément du jeu
    protected int y; // Position en y de l'élément du jeu
    protected Image image; // Image représentant l'élément du jeu


    /**
     * Constructeur pour créer un élément de jeu avec une position et une image.
     *
     * @param x     La coordonnée x de l'élément du jeu.
     * @param y     La coordonnée y de l'élément du jeu.
     * @param image L'image représentant l'élément du jeu.
     */
    public GameElement(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }


    /**
     * Récupère la position de l'élément du jeu sous forme d'un tableau d'entiers [x, y].
     *
     * @return Un tableau d'entiers contenant les coordonnées x et y de l'élément du jeu.
     */
    public int[] getPos() {
        return new int[] {this.x, this.y};
    }


    /**
     * Modifie la position de l'élément du jeu.
     *
     * @param x La nouvelle coordonnée x de l'élément du jeu.
     * @param y La nouvelle coordonnée y de l'élément du jeu.
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Récupère la coordonnée x de l'élément du jeu.
     *
     * @return La coordonnée x de l'élément du jeu.
     */
    public int getX(){
        return this.x;
    }


    /**
     * Récupère la coordonnée y de l'élément du jeu.
     *
     * @return La coordonnée y de l'élément du jeu.
     */
    public int getY(){
        return this.y;
    }

    /**
     * Modifie la coordonnée x de l'élément du jeu.
     *
     * @param newX La nouvelle coordonnée x de l'élément du jeu.
     */
    public void setX(int newX){
        this.x = newX;
    }

    /**
     * Modifie la coordonnée y de l'élément du jeu.
     *
     * @param newY La nouvelle coordonnée y de l'élément du jeu.
     */
    public void setY(int newY){
        this.y = newY;
    }

    /**
     * Récupère l'image représentant l'élément du jeu.
     *
     * @return L'image représentant l'élément du jeu.
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Modifie l'image représentant l'élément du jeu.
     *
     * @param image La nouvelle image représentant l'élément du jeu.
     */
    public void setImage(Image image) {
        this.image = image;
    }
}
    