package modele;
import util.mvc.*;
import playerModele.*;
import java.util.*;
import util.GameElement;
import modele.gameElements.weapons.*;
import modele.gameElements.Empty;

/**
 * Classe représentant un proxy pour l'accès à la grille du jeu. Le proxy intercepte les appels
 * aux méthodes de la grille et applique des comportements spécifiques, comme la dissimulation
 * des éléments comme les bombes et les mines.
 * Il permet de contrôler l'accès aux éléments de la grille sans modifier directement la grille réelle.
 * Cette classe implémente l'interface {@link GridInterface} et agit comme un intermédiaire entre le joueur
 * et la grille du jeu.
 */
public class GridProxy extends AbstractModeleEcoutable implements GridInterface{
    
    private GridInterface instance;
    private Player player;


    /**
     * Constructeur pour créer un proxy de la grille.
     *
     * @param player Le joueur qui utilise ce proxy.
     * @param instance L'instance réelle de la grille à laquelle le proxy accède.
     */
    public GridProxy(Player player, GridInterface instance ){
        this.instance = instance;
        this.player = player;
    }


     /**
     * Retourne une représentation en chaîne de caractères de la grille.
     *
     * @return Une chaîne de caractères représentant l'état de la grille.
     */
    @Override
    public String toString(){
        return this.instance.toString();
    }


    /**
     * Vérifie si un joueur est présent à une position donnée.
     *
     * @param x La coordonnée x de la position à vérifier.
     * @param y La coordonnée y de la position à vérifier.
     * @return true si un joueur est présent à cette position, false sinon.
     */
    @Override
    public boolean isPlayerPresent(int x, int y){
        return this.instance.isPlayerPresent(x, y);
    }


     /**
     * Vérifie si un mur est présent à une position donnée.
     *
     * @param x La coordonnée x de la position à vérifier.
     * @param y La coordonnée y de la position à vérifier.
     * @return true si un mur est présent à cette position, false sinon.
     */
    @Override
    public boolean isWallPresent(int x, int y){
        return this.instance.isWallPresent(x, y);
    }


    /**
     * Récupère l'élément à une position donnée dans la grille.
     * Si l'élément est une bombe ou une mine, il renvoie un élément vide pour les cacher.
     *
     * @param x La coordonnée x de la position de l'élément.
     * @param y La coordonnée y de la position de l'élément.
     * @return L'élément à la position donnée, ou un élément vide si c'est une bombe ou une mine.
     */
    @Override
    public GameElement getElement(int x, int y){
        GameElement element =  this.instance.getElement(x, y);

        if (element instanceof Bomb || element instanceof Mine) {
            if (!this.player.getPlacedExplosives().contains(element)) {
                return new Empty(x, y);
            }
        }
        
        return element;
    }

    /**
     * Lance le minuteur pour les bombes.
     */
    @Override
    public void bombTimer(){
        this.instance.bombTimer();
    }


    /**
     * Récupère le joueur à une position donnée dans la grille.
     *
     * @param x La coordonnée x de la position du joueur.
     * @param y La coordonnée y de la position du joueur.
     * @return Le joueur à la position donnée.
     */
    @Override
    public Player getPlayer(int x, int y){
        return this.instance.getPlayer(x, y);
    }

    public Player getProxyPlayer() {
        return this.player;
    }

    /**
     * Ajoute un élément à une position donnée dans la grille.
     *
     * @param element L'élément à ajouter.
     * @param x La coordonnée x de la position où ajouter l'élément.
     * @param y La coordonnée y de la position où ajouter l'élément.
     */
    @Override
    public void addElement(GameElement element, int x, int y){
        this.instance.addElement(element, x, y);
    }


    /**
     * Retourne la grille sous forme de tableau bidimensionnel d'éléments du jeu.
     *
     * @return Un tableau bidimensionnel représentant la grille du jeu.
     */
    @Override
    public GameElement[][] returnGrid(){
        return this.instance.returnGrid();
    }


    /**
     * Fait exploser une bombe à une position donnée et inflige des dégâts.
     *
     * @param x La coordonnée x de la bombe.
     * @param y La coordonnée y de la bombe.
     * @param damage Le nombre de dégâts infligés par l'explosion.
     */
    @Override
    public void explodeBomb(int x, int y, int damage){
        this.instance.explodeBomb(x, y, damage);
    }

    /**
     * Retourne le tableau interne de la grille.
     *
     * @return Un tableau représentant les éléments de la grille.
     */
    @Override
    public GameElement[][] getTab(){
        return this.instance.getTab();
    }

    /**
     * Retourne la taille de la grille.
     *
     * @return La taille de la grille.
     */
    @Override
    public int getSize(){
        return this.instance.getSize();
    }


    /**
     * Retourne la liste des joueurs présents dans le jeu.
     *
     * @return La liste des joueurs.
     */
    @Override
    public List<Player> getPlayerList(){
        return this.instance.getPlayerList();
    }

     /**
     * Retire un élément à une position donnée de la grille.
     *
     * @param x La coordonnée x de l'élément à retirer.
     * @param y La coordonnée y de l'élément à retirer.
     */
    @Override
    public void removeElement(int x, int y){
        this.instance.removeElement(x, y);
    }

     /**
     * Passe au joueur suivant dans la grille.
     */
    @Override
    public void nextPlayer(){
        this.instance.nextPlayer();
    }

    /**
     * Récupère le joueur actif actuellement dans le jeu.
     *
     * @return Le joueur actif.
     */
    @Override
    public Player getActifPlayer(){
        return this.instance.getActifPlayer();
    }
}
