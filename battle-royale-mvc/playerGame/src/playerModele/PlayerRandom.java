package playerModele;

import java.util.*;


/**
 * La classe `PlayerRandom` représente un joueur contrôlé par une intelligence artificielle.
 * Elle hérite de la classe abstraite `Player` et implémente la méthode `takeTurn`,
 * qui choisit une action aléatoire à effectuer lors du tour de l'IA.
 */
public class PlayerRandom extends Player {

    private Random random;


    /**
     * Constructeur de la classe `PlayerRandom`.
     * Initialise le joueur IA avec ses propriétés spécifiques.
     *
     * @param x Position X initiale sur la grille
     * @param y Position Y initiale sur la grille
     * @param name Nom de l'IA
     * @param energy Niveau initial d'énergie (points de vie)
     * @param ammo Quantité initiale de munitions
     * @param numberOfBomb Quantité initiale de bombes
     * @param numberOfMine Quantité initiale de mines
     * @param range 
     */
    public PlayerRandom(int x, int y, String name, int energy, int ammo, int numberOfBomb, int numberOfMine, int range) {
        super(x, y, name, energy, ammo,numberOfBomb, numberOfMine, range);
        this.random = new Random();
    }

    /**
     * Permet à l'IA de prendre une décision automatiquement.
     * Une action est choisie aléatoirement parmi 5 possibilités.
     */
    @Override
    public void takeTurn() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        int choice = random.nextInt(5); // Choix aléatoire entre 0 et 4

        switch (choice) {
            case 0:
                moveLeft();
                break;

            case 1:
                moveRight();
                break;
            case 2:
                this.activateShield();
                System.out.println(this.getName() + " active son bouclier.");
                break;

            case 3:
                System.out.println(this.getName() + " ne fait rien.");
                break;

            case 4:
                shootHorizontal();
                System.out.println(this.getName() + " tire !");
                break;
            case 5:
                moveUp();
                break;
            case 6:
                moveDown();
                break;
            case 7:
                shootVertical();
                System.out.println(this.getName() + " tire !");
                break;

            default:
                System.out.println(this.getName() + " passe son tour.");
        }
    }

   
}
