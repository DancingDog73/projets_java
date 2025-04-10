package modele;

import java.util.*;
import util.*;
import playerModele.*;

/**
 * Interface représentant une grille de jeu dans le cadre d'un jeu avec des joueurs et des éléments interactifs.
 * Cette interface définit les méthodes nécessaires pour interagir avec la grille, y ajouter ou retirer des éléments,
 * et gérer les actions des joueurs.
 */
public interface GridInterface {
    
    /**
     * Vérifie si un joueur est présent à une position spécifique dans la grille.
     * 
     * @param x La position X sur la grille.
     * @param y La position Y sur la grille.
     * @return true si un joueur est présent à la position (x, y), sinon false.
     */
    public boolean isPlayerPresent(int x, int y); 

    /**
     * Vérifie si un mur est présent à la position spécifiée dans la grille.
     * 
     * @param x La position X sur la grille.
     * @param y La position Y sur la grille.
     * @return true si un mur est présent à la position (x, y), sinon false.
     */
    public boolean isWallPresent(int x, int y); 

    /**
     * Retourne l'élément du jeu situé à une position spécifique dans la grille.
     * 
     * @param x La position X sur la grille.
     * @param y La position Y sur la grille.
     * @return L'élément du jeu à la position (x, y).
     */
    public GameElement getElement(int x, int y);

    /**
     * Met à jour le timer de toutes les bombes présentes sur la grille.
     */
    public void bombTimer();

    /**
     * Retourne le joueur situé à une position spécifique dans la grille.
     * 
     * @param x La position X sur la grille.
     * @param y La position Y sur la grille.
     * @return Le joueur situé à la position (x, y), ou null si aucun joueur n'est à cette position.
     */
    public Player getPlayer(int x, int y); 

    /**
     * Ajoute un élément du jeu à la grille à une position spécifique.
     * 
     * @param element L'élément du jeu à ajouter à la grille.
     * @param x La position X où ajouter l'élément.
     * @param y La position Y où ajouter l'élément.
     */
    public void addElement(GameElement element, int x, int y); 

    /**
     * Retourne la représentation complète de la grille de jeu sous forme de tableau 2D.
     * 
     * @return Un tableau 2D représentant la grille du jeu.
     */
    public GameElement[][] returnGrid(); 

    /**
     * Gère l'explosion d'une bombe à la position spécifiée.
     * 
     * @param x La position X où la bombe explose.
     * @param y La position Y où la bombe explose.
     * @param damage Le dommage causé par l'explosion de la bombe.
     */
    public void explodeBomb(int x, int y, int damage); 

    /**
     * Retourne le tableau interne représentant la grille du jeu.
     * 
     * @return Un tableau 2D représentant le plateau de la grille.
     */
    public GameElement[][] getTab(); 

    /**
     * Retourne la taille de la grille de jeu.
     * 
     * @return La taille de la grille de jeu.
     */
    public int getSize(); 

    /**
     * Retourne la liste des joueurs présents dans le jeu.
     * 
     * @return La liste des joueurs présents dans le jeu.
     */
    public List<Player> getPlayerList(); 

    /**
     * Supprime un élément de la grille à une position donnée.
     * 
     * @param x La position X de l'élément à supprimer.
     * @param y La position Y de l'élément à supprimer.
     */
    public void removeElement(int x, int y); 

    /**
     * Gère le passage au joueur suivant dans le tour.
     */
    public void nextPlayer(); 

    /**
     * Retourne le joueur actuellement actif dans le jeu.
     * 
     * @return Le joueur actif dans le jeu.
     */
    public Player getActifPlayer(); 
}
