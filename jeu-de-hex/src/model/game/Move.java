package model.game;
import model.player.*;

/**
 * Représente un mouvement effectué par un joueur sur le plateau de jeu.
 */
public class Move {

    private Player player;
    private int[] position;

    /**
     * Constructeur de la classe Move.
     * 
     * @param player   Le joueur effectuant le mouvement.
     * @param position La position du mouvement, sous la forme d'un tableau [x, y].
     */
    public Move(Player player, int[] position){
        this.player = player;
        this.position = position;
    }

    /**
     * Retourne le joueur qui a effectué ce mouvement.
     * 
     * @return Le joueur ayant effectué ce mouvement.
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Retourne la position associée à ce mouvement.
     * 
     * @return Un tableau contenant les coordonnées [x, y] du mouvement.
     */
    public int[] getPosition(){
        return this.position;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'objet,
     * incluant l'identifiant du joueur et sa position.
     *
     * @return Une chaîne représentant l'objet avec l'ID du joueur et sa position.
     */
    @Override
    public String toString(){
        return "Player : "+ player.getId() +
        " Position " + position;
    }
}