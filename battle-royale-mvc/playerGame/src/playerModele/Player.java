package playerModele;


import util.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.imageio.ImageIO;

public abstract class Player extends GameElement {

    private String name; // Nom du joueur
    private int energy; // Niveau d'énergie
    private boolean shieldActive; //Indique si le bouclier est actif
    private Map<String, Integer> explosives; // Dictionnaire des explosives et de leur quantité
    private List<Object> placedExplosives;
    private int ammo; //Quantité de munitions
    private  int range;
    private String direction;
    private String shootDirection;
    private String explosiveType;
    private int explosiveOrientation;
    


    /**
     * Constructeur pour créer un joueur avec des caractéristiques initiales.
     *
     * @param x                La coordonnée x de la position du joueur.
     * @param y                La coordonnée y de la position du joueur.
     * @param name             Le nom du joueur.
     * @param energy           Le niveau d'énergie du joueur.
     * @param ammo             Le nombre de munitions du joueur.
     * @param numberOfBomb     Le nombre de bombes que le joueur possède.
     * @param numberOfMine     Le nombre de mines que le joueur possède.
     * @param range            La portée d'attaque du joueur.
     */
    public Player(int x, int y, String name, int energy, int ammo, int numberOfBomb, int numberOfMine, int range){
        super(x, y, Player.loadImage());
        this.name = name;
        this.energy = energy;
        this.shieldActive = false;
        this.explosives = new HashMap<>();
        explosives.put("Bomb", numberOfBomb);
        explosives.put("Mine", numberOfMine);
        this.ammo = ammo;
        this.range = range;
        this.direction = "";
        this.shootDirection = "";
        this.explosiveType = "";
        this.explosiveOrientation = 0;
        this.placedExplosives = new ArrayList<>();

    }


    /**
     * Charge l'image du joueur à partir d'un fichier dans les ressources.
     *
     * @return L'image représentant le joueur.
     */
    public static Image loadImage() {
        try {
            InputStream inputStream = Player.class.getClassLoader().getResourceAsStream("resources/images/player.png");
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


     /**
     * Récupère la portée d'attaque du joueur.
     *
     * @return La portée du joueur.
     */
    public int getRange(){
        return this.range;
    }


    /**
     * Récupère la direction de tir du joueur.
     *
     * @return La direction du tir.
     */
    public String getShootDirection(){
        return this.shootDirection;
    }


    /**
     * Récupère la direction actuelle du joueur.
     *
     * @return La direction du joueur.
     */
    public String getDirection(){
        return this.direction;
    }


    /**
     * Récupère le type d'explosif que le joueur utilise (Bombe ou Mine).
     *
     * @return Le type d'explosif.
     */
    public String getExplosiveType(){
        return this.explosiveType;
    }

    /**
     * Récupère l'orientation de l'explosif que le joueur utilise.
     *
     * @return L'orientation de l'explosif.
     */
    public int getExplosiveOrientation(){
        return this.explosiveOrientation;
    }


    /**
     * Obtient le nom du joueur.
     * 
     * @return Nom du joueur
     */
    public String getName(){
        return this.name;
    }

    /**
     * Obtient le niveau d'énergie actuel du joueur.
     * 
     * @return Niveau d'énergie
     */
    public int getEnergy(){
        return this.energy;
    }

    /**
     * Active le bouclier pour protéger le joueur lors du prochain tour.
     */
    public void activateShield(){
        this.shieldActive = true;
    }

    /**
     * Désactive le bouclier du joueur.
     */
    public void deactivateShield(){
        this.shieldActive = false;
    }

        /**
     * Vérifie si le bouclier du joueur est actif.
     * 
     * @return true si le bouclier est actif, false sinon
     */
    public boolean isShieldActive(){
        return this.shieldActive;
    }

    /**
     * Obtient la quantité de munitions disponible pour le joueur.
     * 
     * @return Nombre de munitions
     */
    public int getAmmo(){
        return this.ammo;
    }

    /**
     * Définit la quantité de munitions du joueur.
     * 
     * @param ammo Quantité de munitions
     */
    public void setAmmo(int ammo){
        this.ammo = ammo;
    }

    public void addAmmo(int amount){
        this.ammo += amount;
    }

    public void resetAction(){
        this.direction = "";
        this.shootDirection = "";
        this.explosiveType = "";
        this.explosiveOrientation = 0;
    }


    /**
     * Obtient le dictionnaire des explosifs avec leurs quantités disponibles.
     * 
     * @return Map des explosifs et leurs quantités
     */
    public Map<String, Integer> getExplosives(){
        return this.explosives;
    }

    /**
     * Réduit l'énergie du joueur d'une certaine quantité (par exemple après un impact).
     * Assure que l'énergie ne devienne pas négative.
     * 
     * @param amount Quantité d'énergie à soustraire
     */
    public void reduceEnergy(int amount){
        if(this.energy < amount){
            this.energy = 0;
        }else{
            this.energy -= amount;
        }
        
    }

    /**
     * Augmente l'énergie du joueur d'une certaine quantité.
     * 
     * @param amount Quantité d'énergie à ajouter
     */
    public void increaseEnergy(int amount){
        this.energy += amount;
    }

    /**
     * Vérifie si le joueur a perdu le jeu
     * 
     * @return true si je joueur est vivant, false sinon.
     */
    public boolean isAlive(){
        return this.energy > 0;
    }


    /**
     * Tire verticalement avec une portée fixe.
     * Vérifie les murs et les joueurs présents sur la trajectoire.
     * Réduit la quantité de munitions si le tir est valide.
     * 
     * 
     */
    public void shootVertical(){
        shoot("vertical");
    }

    /**
     * Tire horizontalement avec une portée fixe.
     * Vérifie les murs et les joueurs présents sur la trajectoire.
     * Réduit la quantité de munitions si le tir est valide.
     * 
     */
    public void shootHorizontal(){
        shoot("horizontal");
    }


    /**
     * Tire dans une direction donnée à condition que le joueur ait assez de munitions
     * @param direction la direction dans laquelle le joueur tire(horizontale ou verticale)
     */
    public void shoot(String direction){
        if(this.ammo <= 0){ 
            System.out.println(this.name + " doesn't have enough ammo to shoot");
            return;
        }
        ammo--;
        System.out.println(this.name + " tire verticalement avec une portée de " + range);
        resetAction();
        this.shootDirection = direction;
        fireChangement();
    }


    /**
     * Déplace le joueur dans une direction
     * @param direction la direction dans laquelle le joueur de déplace
     */
    public void move(String direction){
        if (this.energy >= 5) {
            resetAction();
            this.direction = direction;
            System.out.println(this.name + "se déplace");
            fireChangement();
        } else {
            System.out.println(this.name + " ne peut pas se déplacer.");
        }
    }

    /**
     * Déplace le joueur d'une case vers la droite.
     */
    public void moveDown(){
       move("down");
    }

    /**
     * Déplace le joueur d'une case vers la gauche, si la position X est supérieure à 0.
     */
    public void moveUp(){
        move("up");
    }

    /**
     * Déplace le joueur d'une case vers le haut, si la position Y est supérieure à 0.
     */
    public void moveLeft(){
        move("left");
    }

    /**
     * Déplace le joueur d'une case vers le bas.
     */
    public void moveRight(){
        move("right");
    }


    /**
     * Retire un explosif de l'inventaire du joueur
     * @param explosiveType le type d'explosifs à retirer
     */
    public void removeExplosive(String explosiveType){
        this.explosives.put(explosiveType, this.explosives.get(explosiveType) - 1);
    }

    public void addPlacedExplosive(Object explosive) {
        this.placedExplosives.add(explosive);
    }

    public List<Object> getPlacedExplosives() {
        return this.placedExplosives;
    }

   /**
    * Place un explosive sur la grille
    * @param explosiveType Le type d'explosifs à placer sur la grille 
    * @param orientation La direction dans laquelle il faut placer l'explosif
    * @return true si l'action est possible, false sinon
    */
    public boolean placeExplosive(String explosiveType, int orientation){
        if(!this.explosives.containsKey(explosiveType) || this.explosives.get(explosiveType) <= 0){
            System.out.println(this.name + " n'a pas de"+ explosiveType  +"disponibles !");
            return false;
        }
        resetAction();
        this.explosiveType = explosiveType;
        this.explosiveOrientation = orientation;
        fireChangement();
        return true;
    }

    

    /**
     * Méthode abstraite qui permet au joueur de jouer son tour 
     */
    public abstract void takeTurn();

}