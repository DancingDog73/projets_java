package modele;


import playerModele.*;
import modele.gameElements.*;
import modele.gameElements.weapons.*;
import modele.strategies.fillingstrategies.*;
import modele.gameElements.powerups.*;
import util.GameElement;
import util.mvc.*;
import java.util.*;


/**
 * La classe Grid représente la grille de jeu où les joueurs, les bombes, les power-ups et d'autres éléments interagissent.
 * Elle fournit des fonctionnalités pour gérer les éléments de la grille, gérer les actions des joueurs, les explosions et l'état du jeu.
 */
public class Grid extends AbstractModeleEcoutable implements EcouteurModele, GridInterface{

    private GameElement tab[][]; //Grille 2D contenant les éléments de jeu
    private int size; //Taille de la grille (nombre de cases par dimension, grille carrée). 
    private List<Player> players; //Liste des joueurs encore en jeu. 
    private List<Bomb> bombs; //Liste des bombes actives sur la grille. 
    private Player ActifPlayer; //Joueur actuellement actif (celui qui doit jouer son tour).
    private List<Player> shieldedPlayers;
    private List<PowerUp> powerUps;
    private List<Mine> mines;
    private GridFillingStrategy fillingStrategy;

    /**
     * Constructeur pour créer une nouvelle grille avec une taille donnée, une liste de joueurs et une stratégie de remplissage.
     * 
     * @param size La taille de la grille (la grille sera de size x size).
     * @param players Liste des joueurs pour initialiser la partie.
     * @param fillingStrategy La stratégie de remplissage de la grille (par exemple, murs, power-ups).
     */
    public Grid(int size, List<Player> players, GridFillingStrategy fillingStrategy){
        this.size = size;
        this.players = new ArrayList<>();
        this.bombs = new ArrayList<>();
        this.ActifPlayer = players.get(0);
        this.tab = new GameElement[size][size];
        this.shieldedPlayers = new ArrayList<>();
        this.mines = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.fillingStrategy = fillingStrategy;

        // Initialise chaque case de la grille à null (vide)
        for(int lig = 0; lig < size; lig++){
            for(int col = 0; col < size; col++){
                this.tab[lig][col] = new Empty(lig, col);
            }
        }

        this.printGrid();


        // Ajoute les joueurs à leurs positions initiales
        for(Player player: players){
            this.addElement(player, player.getX(), player.getY());
        }
        
        this.fillingStrategy.fillGrid(this);
        
        

    }


    /**
     * Vérifie si un joueur est présent aux coordonnées spécifiées.
     *
     * @param x La coordonnée x à vérifier.
     * @param y La coordonnée y à vérifier.
     * @return true si un joueur est présent à cet endroit, sinon false.
     */
    @Override
    public boolean isPlayerPresent(int x, int y) {
        if(isWithinBound(x, y)){
            if (this.tab[y][x] instanceof Player) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si un mur est présent aux coordonnées spécifiées.
     *
     * @param x La coordonnée x à vérifier.
     * @param y La coordonnée y à vérifier.
     * @return true si un mur est présent à cet endroit, sinon false.
     */
    @Override
    public boolean isWallPresent(int x, int y) {
        if(isWithinBound(x, y)){
            return  (this.tab[y][x] instanceof Wall);  
        }
        return false;
    }

    /**
     * Récupère l'élément du jeu aux coordonnées spécifiées.
     *
     * @param x La coordonnée x pour récupérer l'élément.
     * @param y La coordonnée y pour récupérer l'élément.
     * @return l'élément du jeu à cette position ou null si hors limites.
     */
    @Override
    public GameElement getElement(int x, int y) {
        if(isWithinBound(x, y)) return tab[y][x];
        return null;
    }

    /**
     * Gère le minuteur des bombes et déclenche des explosions lorsque les bombes sont prêtes à exploser.
     */
    @Override
    public void bombTimer() {
        List<Bomb> explodeNext = new ArrayList<>();
        for(Bomb bomb: this.bombs){
            bomb.tick();
            if(bomb.isReadyToExplode()){
                explodeNext.add(bomb);
            }
        }
        for(Bomb bomb: explodeNext){
            bomb.explode();
            removeElement(bomb.getX(), bomb.getY());
            this.bombs.remove(bomb);
        }
    }

    /**
     * Récupère le joueur aux coordonnées spécifiées.
     *
     * @param x La coordonnée x pour récupérer le joueur.
     * @param y La coordonnée y pour récupérer le joueur.
     * @return le joueur à cette position ou null si aucun joueur n'est là.
     */
    @Override
    public Player getPlayer(int x, int y) {
        if(isWithinBound(x, y)){
            if (this.tab[y][x] instanceof Player) {
                return (Player) this.tab[y][x];
            }
        }
        return null;
    }

    /**
     * Ajoute un élément de jeu à la grille aux coordonnées spécifiées.
     *
     * @param element L'élément de jeu à ajouter.
     * @param x La coordonnée x où placer l'élément.
     * @param y La coordonnée y où placer l'élément.
     */
    @Override
    public void addElement(GameElement element, int x, int y) {
        if (isWithinBound(x, y) && this.tab[y][x] instanceof Empty) {
            this.tab[y][x] = element;
            element.setX(x);
            element.setY(y);
            element.ajoutEcouteur(this);
            if(element instanceof Bomb){
                List<Bomb> updatedBombs = new ArrayList<>(this.bombs);
                updatedBombs.add((Bomb) element);
                this.bombs = updatedBombs;
            }
            if(element instanceof Player){
                List<Player> updatedPlayers = new ArrayList<>(this.players);
                updatedPlayers.add((Player) element);
                this.players = updatedPlayers;
            }if(element instanceof PowerUp){
                List<PowerUp> updatedPowerUps = new ArrayList<>(this.powerUps);
                updatedPowerUps.add((PowerUp) element);
                this.powerUps = updatedPowerUps;
            }if(element instanceof Mine){
                List<Mine> updatedMines = new ArrayList<>(this.mines);
                updatedMines.add((Mine) element);
                this.mines = updatedMines;
            }
        }

    }

     /**
     * Retourne la grille complète du jeu sous forme de tableau 2D.
     *
     * @return Un tableau 2D d'éléments de jeu représentant la grille.
     */
    @Override
    public GameElement[][] returnGrid() {
        return this.tab;
    }

    /**
     * Déclenche l'explosion d'une bombe aux coordonnées spécifiées et inflige des dégâts aux joueurs à proximité.
     *
     * @param x La coordonnée x de la bombe.
     * @param y La coordonnée y de la bombe.
     * @param damage La quantité de dégâts à infliger aux joueurs proches de l'explosion.
     */
    @Override
    public void explodeBomb(int x, int y, int damage) {
        if(isWithinBound(x, y)){
            if(this.tab[y][x] instanceof Bomb){
                int[] dxs = {-1, 0, 1, -1, 1, -1, 0, 1};
                int[] dys = {-1, -1, -1, 0, 0, 1, 1, 1};
                for(int i=0; i<8; i++){
                    int targetX = x + dxs[i];
                    int targetY = y + dys[i];
                    if(isWithinBound(targetX, targetY) && isPlayerPresent(targetX, targetY)){
                        Player player = getPlayer(targetX, targetY);
                        if(!player.isShieldActive()){
                            player.reduceEnergy(damage);
                            System.out.println(player.getName() + " est touché par l'explosion");
                        }
                    }
                }
                removeElement(x, y);
            }
        }
    }

    /**
     * Déclenche l'explosion d'une mine aux coordonnées spécifiées et inflige des dégâts au joueur sur la mine
     *
     * @param x La coordonnée x de la mine.
     * @param y La coordonnée y de la mine.
     * @param damage La quantité de dégâts à infliger au joueur proche de l'explosion.
     */
    public void explodeMine(int x, int y, int damage){
        if(isWithinBound(x, y)){
            if(this.tab[y][x] instanceof Mine){
                Player player = ((Mine) this.getElement(x, y)).getPlayerImpact();
                player.reduceEnergy(20);
                removeElement(x, y);
                
            }
        }
    }


     /**
     * Retourne le tableau interne représentant la grille du jeu.
     * 
     * @return Un tableau 2D représentant le plateau de la grille.
     */
    @Override
    public GameElement[][] getTab() {
        return this.tab;
    }

        /**
     * Retourne la taille de la grille de jeu.
     * 
     * @return La taille de la grille de jeu.
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * Retourne la liste des joueurs présents dans le jeu.
     * 
     * @return La liste des joueurs présents dans le jeu.
     */
    @Override
    public List<Player> getPlayerList() {
        return this.players;
    }

    /**
     * Supprime un élément de la grille à une position donnée.
     * 
     * @param x La position X de l'élément à supprimer.
     * @param y La position Y de l'élément à supprimer.
     */
    @Override
    public void removeElement(int x, int y) {
        if (isWithinBound(x, y) && this.tab[y][x] != null) {
            GameElement element = this.tab[y][x];
            if(element instanceof Bomb){
                this.bombs.remove(element);
            }else if(element instanceof Player){
                this.players.remove(element);
            }
            this.tab[y][x] = new Empty(x, y);
        }

    }

    /**
     * Gère le passage au joueur suivant dans le tour.
     */
    @Override
    public void nextPlayer() {
        if(this.players.size() > 0){
            int currentIndex = this.players.indexOf(this.ActifPlayer);
            this.ActifPlayer = this.players.get((currentIndex + 1) % this.players.size());
            System.out.println("Joueur actif : " + this.ActifPlayer.getName());
        }

    }

    /**
     * Retourne le joueur actuellement actif dans le jeu.
     * 
     * @return Le joueur actif dans le jeu.
     */
    @Override
    public Player getActifPlayer() {
        return this.ActifPlayer;
    }

    /**
     * Gère l'impact d'un joueur sur un élément du jeu à une position donnée.
     * Si l'élément est une bombe ou une mine, il est traité en conséquence (explosion et suppression).
     * 
     * @param player Le joueur qui subit l'impact (bien que ce paramètre ne soit pas utilisé dans cette méthode).
     * @param x La coordonnée x de la position de l'élément.
     * @param y La coordonnée y de la position de l'élément.
     */
    public void handleImpact(Player player, int x, int y){
        GameElement element = getElement(x, y);
        if(element instanceof Bomb){
            Bomb bomb = (Bomb) element;
            bomb.explode(); 
            removeElement(x, y); 
            this.bombs.remove(bomb); 
        }  else if (element instanceof Mine) {
            Mine mine = (Mine) element;
            mine.setPlayerImpact(player);
            mine.explode(); 
            removeElement(x, y);
        }
        
    }


    /**
     * Gère l'activation d'un power-up par un joueur à une position donnée.
     * Si l'élément est un power-up, l'effet correspondant est appliqué au joueur
     * (augmentation des munitions ou de l'énergie), et l'élément est supprimé.
     * 
     * @param player Le joueur qui collecte le power-up.
     * @param x La coordonnée x de la position du power-up.
     * @param y La coordonnée y de la position du power-up.
     */
    public void handlePowerUp(Player player, int x, int y) {
        GameElement element = getElement(x, y);
        if (element instanceof PowerUp) {
            PowerUp powerUp = (PowerUp) element;
            if(powerUp instanceof Ammunition){
                player.addAmmo(5);
            } else {
                player.increaseEnergy(25);
            }
            removeElement(x, y); 
        }
    }



    /**
     * Déplace un joueur vers une nouvelle position sur la grille si possible.
     *
     * @param player Le joueur à déplacer.
     * @param newX La nouvelle coordonnée x pour le joueur.
     * @param newY La nouvelle coordonnée y pour le joueur.
     * @return true si le déplacement a réussi, sinon false.
     */
    public boolean movePlayer(Player player, int newX, int newY) {
        if (isWithinBound(player.getX(), player.getY()) && isWithinBound(newX, newY) && !(this.isWallPresent(newX, newY)) && !(this.isPlayerPresent(newX, newY))) { 
            handleImpact(player, newX, newY);
            handlePowerUp(player, newX, newY);
            this.tab[player.getY()][player.getX()] = new Empty(player.getX(), player.getY());
            this.tab[newY][newX] = player;
            player.setX(newX);
            player.setY(newY);
            return true;
        }
        return false; 
    }

    /**
     * Gère le tir d'un joueur dans une direction donnée, en prenant en compte la portée du tir et les obstacles (murs).
     * Selon la direction spécifiée ("vertical" ou "horizontal"), le tir affecte les cases à proximité du joueur.
     * 
     * Si un mur est rencontré, le tir est arrêté dans cette direction. Si la case n'est pas un mur, la méthode
     * `handleShotDamage` est appelée pour gérer l'impact du tir.
     * 
     * @param player Le joueur qui effectue le tir.
     * @param direction La direction du tir, qui peut être "vertical" ou "horizontal".
     */
    public void handleShot(Player player, String direction){
        int range = player.getRange();
        System.out.println("there we land");
        switch(direction){
            case "vertical":
                boolean continueTop = true; // Contrôle si on peut continuer à tirer vers le haut
                boolean continueDown= true; // Contrôle si on peut continuer à tirer vers le bas
                for(int i = 1; i <= range; i++){
                    if(isWithinBound(player.getX(),player.getY()+i)){
                        if(isWallPresent(player.getX(),player.getY()+i)) continueDown = false;
                        if(continueDown) handleShotDamage(player.getX(),player.getY()+i);
                    }
                    if(isWithinBound(player.getX(),player.getY()-i)){
                        if(isWallPresent(player.getX(),player.getY()-i)) continueTop = false;
                        if(continueTop) handleShotDamage(player.getX(),player.getY()-i);
                    }
                }    
                break;
            case "horizontal":
                boolean continueRight = true; // Contrôle si on peut continuer à tirer vers la droite
                boolean continueLeft = true; // Contrôle si on peut continuer à tirer vers la gauche
                for(int i = 1; i <= range; i++){
                    if(isWithinBound(player.getX()+i,player.getY())){
                        if(isWallPresent(player.getX()+i,player.getY())) continueRight = false;
                        if(continueRight) handleShotDamage(player.getX()+i,player.getY());
                    }
                    if(isWithinBound(player.getX()-i,player.getY())){
                        if(isWallPresent(player.getX()-i, player.getY())) continueLeft = false;
                        if(continueLeft) handleShotDamage(player.getX()-i,player.getY());
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * Gère les dégâts infligés à un joueur lorsqu'il est touché par un tir.
     * Si un joueur est présent à la position spécifiée, son énergie est réduite de 20.
     * Un message est affiché pour indiquer que le joueur a été touché, ainsi que son niveau d'énergie actuel.
     * 
     * @param x La coordonnée X de la case où le tir a frappé.
     * @param y La coordonnée Y de la case où le tir a frappé.
     */
    public void handleShotDamage(int x, int y){
        if(isPlayerPresent(x, y)){
            Player player = getPlayer(x, y);
            player.reduceEnergy(20);
            System.out.println(player.getName() + " got shot, energy level : " + player.getEnergy());
        }
    }

    /**
     * Permet de placer un explosif à une position donnée sur le terrain.
     * Si la position est valide (dans les limites du terrain et la case est vide), l'explosif est placé.
     * Le type d'explosif est spécifié en tant que chaîne de caractères ("Bomb" ou "Mine").
     * Un message est affiché pour indiquer que l'explosif a été placé avec succès.
     * 
     * @param explosiveType Le type d'explosif à placer (soit "Bomb", soit "Mine").
     * @param x La coordonnée X de la position où placer l'explosif.
     * @param y La coordonnée Y de la position où placer l'explosif.
     * @param playerName Le nom du joueur qui place l'explosif.
     * @return `true` si l'explosif a été placé avec succès, `false` si le placement a échoué.
     */
    public boolean placeExplosive(String explosiveType, int x, int y, Player player){
        if(isWithinBound(x, y) && getElement(x, y) instanceof Empty){
            Explosive explosive = null;
            switch (explosiveType) {
                case "Bomb":
                    explosive = new Bomb(x, y);
                    player.removeExplosive(explosiveType);
                    player.addPlacedExplosive(explosive);
                    break;
                case "Mine":
                    explosive = new Mine(x, y);
                    player.removeExplosive(explosiveType);
                    player.addPlacedExplosive(explosive);
                    break;
                default:
                    System.out.println("Type d'explosif invalide.");
                    return false;
            }
            addElement(explosive, x, y);
            System.out.println(player.getName() + " a placé une " + explosiveType + " à la position (" + x + ", " + y + ").");
            return true;
        }
        return true;

    }


    /**
     * Méthode appelée pour mettre à jour le modèle en fonction de l'action effectuée par une entité du jeu.
     * Cette méthode gère les actions suivantes : 
     * - Déplacement du joueur (avec réduction d'énergie)
     * - Tir du joueur (dans les directions verticale et horizontale)
     * - Placement d'un explosif autour du joueur (en fonction de l'orientation)
     * - Activation du bouclier du joueur
     * - Explosion d'une bombe ou d'une mine
     *
     * La méthode vérifie le type de l'objet source (Player, Bomb, ou Mine) et agit en conséquence.
     * 
     * @param source L'objet source ayant déclenché l'événement de mise à jour du modèle. Il peut être de type Player, Bomb, ou Mine.
     */
    @Override
    public void modeleMisAJour(Object source) {
        if(source instanceof Player){
            Player player = (Player) source;
            if(player.getDirection() != ""){
                switch (player.getDirection()) {
                    case "up":
                        if(movePlayer(player, player.getX(), player.getY()-1)) player.reduceEnergy(5);
                        break;
                    case "down":
                        if(movePlayer(player, player.getX(), player.getY()+1)) player.reduceEnergy(5);
                        break;
                    case "left":
                        if(movePlayer(player, player.getX()-1, player.getY())) player.reduceEnergy(5);
                        break;
                    case "right":
                        if(movePlayer(player, player.getX()+1, player.getY())) player.reduceEnergy(5);
                        break;
                    default:
                        break;
                } 
            }else if(player.getShootDirection() != ""){
                switch(player.getShootDirection()){
                    case "vertical":
                        handleShot(player, "vertical");
                        break;
                    case "horizontal":
                        handleShot(player, "horizontal");
                        break;
                    default:
                        break;
                }
            }else if(player.getExplosiveType() != ""){
                int[] dxs = {-1, 0, 1, -1, 1, -1, 0, 1};
                int[] dys = {-1, -1, -1, 0, 0, 1, 1, 1};
                String explosiveType = player.getExplosiveType();
                switch (player.getExplosiveOrientation()) {
                    case 0:
                        placeExplosive(explosiveType, player.getX() + dxs[0], player.getY() + dys[0], player);
                        break;
                    case 1:
                        placeExplosive(explosiveType, player.getX() + dxs[1], player.getY() + dys[1], player);
                        break;
                    case 2:
                        placeExplosive(explosiveType, player.getX() + dxs[2], player.getY() + dys[2], player);
                        break;
                    case 3:
                        placeExplosive(explosiveType, player.getX() + dxs[3], player.getY() + dys[3], player);
                        break;
                    case 4:
                        placeExplosive(explosiveType, player.getX() + dxs[4], player.getY() + dys[4], player);
                        break;
                    case 5:
                        placeExplosive(explosiveType, player.getX() + dxs[5], player.getY() + dys[5], player);
                        break;
                    case 6:
                        placeExplosive(explosiveType, player.getX() + dxs[6], player.getY() + dys[6], player);
                        break;
                    case 7:
                        placeExplosive(explosiveType, player.getX() + dxs[7], player.getY() + dys[7], player);   
                        break;
                    default:
                        break;
                }

            }else if(player.isShieldActive()){
                this.shieldedPlayers.add(player);
            }
        }else if(source instanceof Bomb){
            Bomb bomb = (Bomb) source;
            explodeBomb(bomb.getX(), bomb.getY(), bomb.getDamage());
        }else if(source instanceof Mine){
            Mine mine = (Mine) source;
            explodeMine(mine.getX(), mine.getY(), mine.getDamage());
        }
    }

   
    /**
     * Supprime les joueurs qui ne sont plus en vie (énergie &lt;= 0) de la grille et de la liste.
     */
    public void removeDeadPlayers(){
        Iterator<Player> iterator = this.players.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if(!player.isAlive()){
                this.tab[player.getY()][player.getX()] = new Empty(player.getY(), player.getX());
                iterator.remove();
                System.out.println("Le joueur " + player.getName() + " est mort et a été retiré du jeu.");
            }
        }
    }

    /**
     * Vérifie si les coordonnées (x, y) sont à l'intérieur des limites du plateau de jeu.
     * Le plateau est de taille carrée, et les coordonnées doivent être comprises entre 0 et la taille du plateau - 1.
     *
     * @param x La coordonnée horizontale.
     * @param y La coordonnée verticale.
     * @return true si les coordonnées sont valides et à l'intérieur du plateau, false sinon.
     */
    public boolean isWithinBound(int x, int y){
        return (x < this.size && x >= 0)  && (y < this.size && y >= 0);
    }

    /**
     * Retourne la liste des joueurs vivants.
     * Cette méthode parcourt la liste des joueurs et ajoute à la liste des joueurs vivants ceux qui sont encore en vie.
     *
     * @return La liste des joueurs vivants.
     */
    public List<Player> getAlivePlayers() {
        List<Player> alivePlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.isAlive()) {
                alivePlayers.add(player);
            }
        }
        return alivePlayers;
    }

    /**
     * Vérifie si la partie est terminée.
     * La partie se termine lorsque 1 joueur ou moins est encore en vie. Si un seul joueur est vivant, celui-ci est déclaré vainqueur.
     *
     * @return true si la partie est terminée, false sinon.
     */
    public boolean isGameOver(){
        if(getAlivePlayers().size() <= 1){
            if(getAlivePlayers().size() == 1){
                System.out.println("La partie est terminée, "+ getAlivePlayers().get(0).getName() + " a gagné.");
            } else {
                System.out.println("La partie est terminée.");
            }
            return true;
        }
        return false;
    }

    /**
     * Met à jour l'état du jeu. Cette méthode est appelée à chaque tour du jeu.
     * Elle gère plusieurs aspects du jeu, comme le décompte du temps des bombes, la suppression des joueurs morts,
     * l'activation et la désactivation des boucliers des joueurs, et le passage au joueur suivant.
     */
    public void update(){
        this.bombTimer();
        this.removeDeadPlayers();
        for(Player player: this.players){
            player.deactivateShield();
        }
        for(Player player: this.shieldedPlayers){
            player.activateShield();
        }
        this.shieldedPlayers = new ArrayList<>();
        this.nextPlayer();
        this.printGrid();
        

    }

    /**
     * Vérifie si la case à la position (x, y) est vide.
     * La case est considérée comme vide si elle est à l'intérieur des limites du plateau et n'y contient aucun élément.
     *
     * @param x La coordonnée horizontale.
     * @param y La coordonnée verticale.
     * @return true si la case est vide, false sinon.
     */
    public boolean isEmpty(int x, int y) {
        return isWithinBound(x, y) && tab[x][y] == null;
    }

    /**
     * Retourne la liste des ennemis d'un joueur donné.
     * Les ennemis sont tous les autres joueurs, sauf celui passé en paramètre.
     *
     * @param player Le joueur pour lequel on souhaite obtenir la liste des ennemis.
     * @return La liste des ennemis du joueur spécifié.
     */
    public List<Player> getEnemies(Player player){
        List<Player> players = new ArrayList<>();
        players.remove(player);
        return players;

    }

    /**
     * Retourne la liste des PowerUps disponibles dans le jeu.
     *
     * @return La liste des PowerUps.
     */
    public List<PowerUp> getPowerUp(){
        return this.powerUps;
    }

    /**
     * Retourne la liste des bombes visibles par tous les joueurs.
     * Une bombe est ajoutée à la liste si elle est définie comme visible pour tous les joueurs.
     *
     * @return La liste des bombes visibles par tous les joueurs.
     */
    public List<Bomb> getBombsForPlayers(){
        List<Bomb> bombs = new ArrayList<>();
        for(Bomb bomb: this.bombs){
            if(bomb.isVisibleToAll()) bombs.add(bomb);
        }
        return bombs;
    }

    /**
     * Retourne la liste des mines visibles par tous les joueurs.
     * Une mine est ajoutée à la liste si elle est définie comme visible pour tous les joueurs.
     *
     * @return La liste des mines visibles par tous les joueurs.
     */
    public List<Mine> getMinesForPlayers(){
        List<Mine> mines = new ArrayList<>();
        for(Mine mine: this.mines){
            if(mine.isVisibleToAll()) mines.add(mine);
        }
        return mines;
    }



    

    public void printGrid() {
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                if (this.tab[x][y] instanceof Player) {
                    System.out.print("P "); // Player
                } else if (this.tab[x][y] instanceof Mine) {
                    System.out.print("M "); // Mine
                } else if (this.tab[x][y] instanceof Bomb) {
                    System.out.print("B "); // Bomb
                }else if (this.tab[x][y] instanceof Wall) {
                    System.out.print("W "); // Bomb
                }else if (this.tab[x][y] instanceof EnergyPill) {
                    System.out.print("E "); // 
                }else if (this.tab[x][y] instanceof Ammunition) {
                    System.out.print("A "); // Bomb
                }else if (this.tab[x][y] instanceof Empty) {
                    System.out.print(". "); // Case vide
                }
            }
            System.out.println();
        }
    }







}