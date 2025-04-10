package modele;

/**
 * Classe représentant la configuration d'un joueur dans le jeu.
 * Contient les informations sur les caractéristiques de base du joueur,
 * telles que son nom, son énergie, ses munitions, le nombre de bombes et de mines, 
 * ainsi que la portée de ses attaques.
 */
public class PlayerConfig {
    private String name; // Nom du joueur
    private int energy; // Quantité d'énergie initiale
    private int ammo; // Quantité initiale de munitions
    private int numberOfBomb; // Nombre initial de bombes
    private int numberOfMine; // Nombre initial de mines
    private int range; // Portée des attaques du joueur

    /**
     * Récupère le nom du joueur.
     *
     * @return le nom du joueur.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du joueur.
     *
     * @param name le nom du joueur.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Récupère la quantité d'énergie initiale du joueur.
     *
     * @return la quantité d'énergie.
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Définit la quantité d'énergie initiale du joueur.
     *
     * @param energy la quantité d'énergie.
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Récupère la quantité initiale de munitions du joueur.
     *
     * @return la quantité de munitions.
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * Définit la quantité initiale de munitions du joueur.
     *
     * @param ammo la quantité de munitions.
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    /**
     * Récupère le nombre initial de bombes du joueur.
     *
     * @return le nombre de bombes.
     */
    public int getNumberOfBomb() {
        return numberOfBomb;
    }

    /**
     * Définit le nombre initial de bombes du joueur.
     *
     * @param numberOfBomb le nombre de bombes.
     */
    public void setNumberOfBomb(int numberOfBomb) {
        this.numberOfBomb = numberOfBomb;
    }

    /**
     * Récupère le nombre initial de mines du joueur.
     *
     * @return le nombre de mines.
     */
    public int getNumberOfMine() {
        return numberOfMine;
    }

    /**
     * Définit le nombre initial de mines du joueur.
     *
     * @param numberOfMine le nombre de mines.
     */
    public void setNumberOfMine(int numberOfMine) {
        this.numberOfMine = numberOfMine;
    }

    /**
     * Récupère la portée des attaques du joueur.
     *
     * @return la portée des attaques.
     */
    public int getRange() {
        return range;
    }

    /**
     * Définit la portée des attaques du joueur.
     *
     * @param range la portée des attaques.
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Représentation textuelle de la configuration du joueur.
     *
     * @return une chaîne de caractères décrivant les caractéristiques du joueur.
     */
    @Override
    public String toString() {
        return "PlayerConfig{" +
                "name='" + name + '\'' +
                ", energy=" + energy +
                ", ammo=" + ammo +
                ", numberOfBomb=" + numberOfBomb +
                ", numberOfMine=" + numberOfMine +
                ", range=" + range +
                '}';
    }
}
