package playerModele;

import java.util.*;

/**
 * La classe PlayerHuman représente un joueur contrôlé par un utilisateur.
 * Elle hérite de la classe abstraite Player et implémente la méthode `takeTurn`,
 * qui permet de prendre une décision basée sur les entrées utilisateur.
 */
public class PlayerHuman extends Player {

    private static final Scanner scanner = new Scanner(System.in);
    private boolean turnComplete = false;

    /**
     * Constructeur de la classe `PlayerHuman`.
     * Initialise le joueur humain avec ses propriétés spécifiques.
     *
     * @param x Position X initiale sur la grille
     * @param y Position Y initiale sur la grille
     * @param name Nom du joueur
     * @param energy Niveau initial d'énergie (points de vie)
     * @param ammo Quantité initiale de munitions
     * @param numberOfBomb Quantité initiale de bombes
     * @param numberOfMine Quantité initiale de mines
     * @param range 
     */
    public PlayerHuman(int x, int y, String name, int energy, int ammo, int numberOfBomb, int numberOfMine, int range) {
        super(x, y, name, energy, ammo, numberOfBomb, numberOfMine, range);
    }

    /**
     * Permet au joueur humain de prendre une décision durant son tour.
     * Affiche un menu d'actions et exécute l'action choisie par l'utilisateur.
     *
     */
    @Override
    public synchronized void takeTurn() {
        
        System.out.println(this.getName() + "'s turn. Waiting for input...");
        try {
            while (!this.turnComplete) {
                wait(); // Wait for GUI to signal turn completion
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.turnComplete = false;
    }

    public synchronized void provideInput() {
        this.turnComplete = true;
        notify(); // Wake up `takeTurn` method
    }
   
}
