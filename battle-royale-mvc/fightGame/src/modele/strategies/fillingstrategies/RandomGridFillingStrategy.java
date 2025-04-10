package modele.strategies.fillingstrategies;

import modele.gameElements.Wall;
import modele.gameElements.powerups.*;
import modele.gameElements.weapons.*;
import modele.*;

import java.util.*;

/**
 * Implémentation de la stratégie de remplissage de grille de manière aléatoire.
 * Cette classe utilise un fichier de configuration XML pour déterminer les éléments
 * à ajouter sur la grille, comme les murs, mines, bombes, et bonus.
 */
public class RandomGridFillingStrategy implements GridFillingStrategy {

    private Random random = new Random();
    private GameConfig gameConfig;

    /**
     * Remplit la grille avec des éléments (murs, bonus, armes, etc.) de manière aléatoire.
     * La configuration de la grille est extraite d'un fichier XML via {@link ConfigLoader}.
     *
     * @param grid la grille à remplir.
     */
    @Override
    public void fillGrid(Grid grid) {
        ConfigLoader loader = new ConfigLoader();
        loader.parse("src/modele/config.xml"); // Charge la configuration depuis le fichier XML
        GameConfig config = loader.getConfig();
        this.gameConfig = config;

        int surface = config.getGridHeight() * config.getGridHeight();
        addRandomWalls((surface * config.getObstacles()) / 100, grid);

        for (int i = 0; i < config.getWallLines(); i++) {
            boolean isHorizontal = random.nextInt(2) == 0; // Détermine si la ligne sera horizontale ou verticale
            int startX = random.nextInt(grid.getSize());
            int startY = random.nextInt(grid.getSize());
            addWallLine(startX, startY, grid.getSize() / 3, isHorizontal, grid);
        }

        addRandomMines(grid);
        addRandomBombs(grid);
        addRandomEnergy(grid);
        addRandomAmmo(grid);
    }

    /**
     * Ajoute un nombre aléatoire de bombes sur la grille.
     *
     * @param grid la grille où placer les bombes.
     */
    public void addRandomBombs(Grid grid) {
        int count = random.nextInt(this.gameConfig.getBombs());
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(grid.getSize());
            int y = random.nextInt(grid.getSize());
            Bomb bomb = new Bomb(x, y);
            grid.addElement(bomb, x, y);
        }
    }

    /**
     * Ajoute un nombre aléatoire de mines sur la grille.
     *
     * @param grid la grille où placer les mines.
     */
    public void addRandomMines(Grid grid) {
        System.out.println(gameConfig.getMines());
        int count = random.nextInt(gameConfig.getMines());
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(grid.getSize());
            int y = random.nextInt(grid.getSize());
            Mine mine = new Mine(x, y);
            grid.addElement(mine, x, y);
        }
    }

    /**
     * Ajoute un nombre aléatoire de bonus d'énergie (EnergyPill) sur la grille.
     *
     * @param grid la grille où placer les bonus d'énergie.
     */
    public void addRandomEnergy(Grid grid) {
        int count = random.nextInt(gameConfig.getEnergyPill());
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(grid.getSize());
            int y = random.nextInt(grid.getSize());
            PowerUp powerUp = new EnergyPill(x, y);
            grid.addElement(powerUp, x, y);
        }
    }

    /**
     * Ajoute un nombre aléatoire de munitions (Ammunition) sur la grille.
     *
     * @param grid la grille où placer les munitions.
     */
    public void addRandomAmmo(Grid grid) {
        int count = random.nextInt(gameConfig.getAmmo());
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(grid.getSize());
            int y = random.nextInt(grid.getSize());
            PowerUp powerUp = new Ammunition(x, y);
            grid.addElement(powerUp, x, y);
        }
    }

    /**
     * Ajoute une ligne de murs sur la grille.
     * La ligne peut être horizontale ou verticale, en fonction du paramètre `isHorizontal`.
     *
     * @param startX       position de départ en X.
     * @param startY       position de départ en Y.
     * @param length       longueur de la ligne de murs.
     * @param isHorizontal indique si la ligne est horizontale (true) ou verticale (false).
     * @param grid         la grille où ajouter la ligne de murs.
     */
    public void addWallLine(int startX, int startY, int length, boolean isHorizontal, Grid grid) {
        for (int i = 0; i < length; i++) {
            int x = isHorizontal ? startX : startX + i;
            int y = isHorizontal ? startY + i : startY;
            Wall wall = new Wall(x, y);
            grid.addElement(wall, x, y);
        }
    }

    /**
     * Ajoute un nombre spécifique de murs aléatoirement répartis sur la grille.
     *
     * @param numberOfWalls le nombre de murs à ajouter.
     * @param grid          la grille où placer les murs.
     */
    public void addRandomWalls(int numberOfWalls, Grid grid) {
        int wallsAdded = 0;

        while (wallsAdded < numberOfWalls) {
            int x = random.nextInt(grid.getSize());
            int y = random.nextInt(grid.getSize());
            Wall wall = new Wall(x, y);
            grid.addElement(wall, x, y);
            wallsAdded++;
        }
    }
}
