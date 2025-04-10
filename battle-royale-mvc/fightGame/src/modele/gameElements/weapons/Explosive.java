package modele.gameElements.weapons;

import java.awt.Image;

import util.GameElement;


/**
 * Classe abstraite représentant un élément explosif dans le jeu (par exemple, une bombe ou une mine).
 * Les explosifs ont des propriétés spécifiques, comme des dégâts infligés et leur visibilité.
 */
public abstract class Explosive extends GameElement {
    protected int damage;
    protected boolean visibleToAll;
    

    /**
     * Constructeur de la classe Explosive.
     *
     * @param x            La position X de l'explosif sur la grille.
     * @param y            La position Y de l'explosif sur la grille.
     * @param damage       Les dégâts infligés par l'explosif.
     * @param visibleToAll Indique si l'explosif est visible pour tous les joueurs.
     */
    public Explosive(int x, int y, Image image, int damage, boolean visibleToAll) {
        super(x, y, image);
        this.damage = damage;
        this.visibleToAll = visibleToAll;
    }


    /**
     * Vérifie si l'explosif est visible pour tous les joueurs.
     *
     * @return true si l'explosif est visible pour tous, false sinon.
     */
    public boolean isVisibleToAll(){
        return this.visibleToAll;
    }

    /**
     * Modifie la visibilité de l'explosif.
     *
     * @param newValue Nouvelle valeur pour la visibilité (true = visible, false = caché).
     */

    public void setVisibleToAll(boolean newValue){
        this.visibleToAll = newValue;
    }

    public int getDamage(){
        return this.damage;
    }

    /**
     * Méthode abstraite représentant l'explosion de l'explosif.
     * Les classes filles devront implémenter cette méthode pour définir le comportement
     * spécifique de l'explosion (par exemple, des dégâts sur une zone, une animation, etc.).
     */
    public void explode(){
        fireChangement();
    }

    

}
