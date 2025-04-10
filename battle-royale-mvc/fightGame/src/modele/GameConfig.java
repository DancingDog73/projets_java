package modele;

import java.util.List;

/**
 * Classe représentant la configuration globale du jeu.
 * Contient les paramètres de la grille, les objets du jeu (obstacles, bonus, etc.)
 * ainsi que la liste des joueurs configurés.
 */
public class GameConfig {
    private int gridWidth; // Largeur de la grille
    private int gridHeight; // Hauteur de la grille
    private int obstacles; // Densité des obstacles
    private int bonus; // Nombre de bonus disponibles
    private int turns; // Nombre maximal de tours
    private int energyPill; // Quantité d'énergie par pilule
    private int ammo; // Quantité de munitions disponibles
    private int wallLines; // Nombre de lignes de murs
    private int bombs; // Nombre de bombes disponibles
    private int mines; // Nombre de mines disponibles
    private List<PlayerConfig> players; // Liste des configurations de joueurs

    /**
     * Récupère la largeur de la grille.
     *
     * @return largeur de la grille.
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * Définit la largeur de la grille.
     *
     * @param gridWidth largeur de la grille.
     */
    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    /**
     * Récupère la hauteur de la grille.
     *
     * @return hauteur de la grille.
     */
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * Définit la hauteur de la grille.
     *
     * @param gridHeight hauteur de la grille.
     */
    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    /**
     * Récupère la densité des obstacles sur la grille.
     *
     * @return densité des obstacles.
     */
    public int getObstacles() {
        return obstacles;
    }

    /**
     * Définit la densité des obstacles sur la grille.
     *
     * @param obstacles densité des obstacles.
     */
    public void setObstacles(int obstacles) {
        this.obstacles = obstacles;
    }

    /**
     * Récupère le nombre de bonus disponibles dans le jeu.
     *
     * @return nombre de bonus.
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Définit le nombre de bonus disponibles dans le jeu.
     *
     * @param bonus nombre de bonus.
     */
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    /**
     * Récupère le nombre maximal de tours autorisés dans le jeu.
     *
     * @return nombre de tours.
     */
    public int getTurns() {
        return turns;
    }

    /**
     * Définit le nombre maximal de tours autorisés dans le jeu.
     *
     * @param turns nombre de tours.
     */
    public void setTurns(int turns) {
        this.turns = turns;
    }

    /**
     * Récupère la liste des configurations des joueurs.
     *
     * @return liste des joueurs.
     */
    public List<PlayerConfig> getPlayers() {
        return players;
    }

    /**
     * Définit la liste des configurations des joueurs.
     *
     * @param players liste des joueurs.
     */
    public void setPlayers(List<PlayerConfig> players) {
        this.players = players;
    }

    /**
     * Définit la quantité d'énergie par pilule.
     *
     * @param energyPill quantité d'énergie.
     */
    public void setEnergyPill(int energyPill) {
        this.energyPill = energyPill;
    }

    /**
     * Récupère la quantité d'énergie par pilule.
     *
     * @return quantité d'énergie.
     */
    public int getEnergyPill() {
        return this.energyPill;
    }

    /**
     * Définit la quantité de munitions disponibles.
     *
     * @param ammo quantité de munitions.
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    /**
     * Récupère la quantité de munitions disponibles.
     *
     * @return quantité de munitions.
     */
    public int getAmmo() {
        return this.ammo;
    }

    /**
     * Définit le nombre de lignes de murs sur la grille.
     *
     * @param wallLines nombre de lignes de murs.
     */
    public void setWallLines(int wallLines) {
        this.wallLines = wallLines;
    }

    /**
     * Récupère le nombre de lignes de murs sur la grille.
     *
     * @return nombre de lignes de murs.
     */
    public int getWallLines() {
        return this.wallLines;
    }

    /**
     * Définit le nombre de bombes disponibles.
     *
     * @param bombs nombre de bombes.
     */
    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    /**
     * Récupère le nombre de bombes disponibles.
     *
     * @return nombre de bombes.
     */
    public int getBombs() {
        return this.bombs;
    }

    /**
     * Définit le nombre de mines disponibles.
     *
     * @param mines nombre de mines.
     */
    public void setMines(int mines) {
        this.mines = mines;
    }

    /**
     * Récupère le nombre de mines disponibles.
     *
     * @return nombre de mines.
     */
    public int getMines() {
        return this.mines;
    }
}
