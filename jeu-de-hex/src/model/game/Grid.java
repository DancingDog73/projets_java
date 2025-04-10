package model.game; 

/**
 * Représente une grille utilisée pour un jeu, avec des fonctionnalités
 * pour initialiser, manipuler et analyser l'état de la grille.
 */
public class Grid {

    private int size;
    private int[][] grid; 

    /**
     * Constructeur par défaut, crée une grille de taille 14x14.
     */
    public Grid(){
        this.size = 14;
        this.grid = initGrid();
    }

    /**
     * Constructeur qui initialise la grille avec une matrice donnée.
     * 
     * @param grid Grille existante à utiliser.
     */
    public Grid(int[][] grid){
        this.grid = grid;
        this.size = grid[0].length;
    }

    /**
     * Constructeur qui initialise une grille vide de taille spécifiée.
     * 
     * @param size Taille de la grille (nombre de lignes et colonnes).
     */
    public Grid(int size){
        this.size = size;
        this.grid = initGrid();
      
       
    }

    
    /**
     * Initialise la grille avec des cases vides (valeur 0).
     * 
     * @return Un tableau bidimensionnel représentant la grille initialisée.
     */
    public int[][] initGrid(){
        this.grid = new int[size][size];
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                grid[i][j] = 0;
            }
        }
        return grid;
    }

    /**
     * Génère une représentation sous forme de chaîne de caractères de la grille.
     * Chaque ligne est affichée avec les valeurs des cases séparées par des espaces.
     * 
     * @return Une chaîne représentant l'état de la grille.
    */
    @Override
    public String toString(){
        String chaine = "";
        String depassement = "";
        for(int i=0; i < size; i++){
            chaine+= depassement;
            for(int j=0; j < size; j++){
                chaine += grid[i][j];
                chaine += " ";
            }
            chaine += "\n";
            depassement += " ";
        }
        return chaine;
    }


    /**
     * Programme principal pour tester l'affichage de la grille.
     * 
     * @param argv Arguments de ligne de commande (non utilisés ici).
     */
    public static void main (String[] argv){
        Grid g = new Grid();
        System.out.println(g);

    }

    public int getSize() {
        return size;
    }


    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }


    /**
     * Change l'état d'une case qui vient d'être jouée
     * @param x l'abcisse de la case
     * @param y l'ordonnée de la case
     * @param number le numéro du joueur qui vient de jouer
     */
    public void toggleCell(int x, int y, int number){
        this.grid[y][x] = number;
        
    }

    /**
     * Crée une copie de la grille actuelle.
     * 
     * @return Une nouvelle instance de Grid contenant une copie des données.
    */
    public Grid clone() {
        
        int[][] clonedData = new int[size][size];
    
        for (int i = 0; i < size; i++) {
            clonedData[i] = this.grid[i].clone(); 
        }
    
        Grid clonedGrid = new Grid(clonedData);
        return clonedGrid;
    }

    /**
     * Vérifie si le joueur spécifié a gagné en reliant ses deux côtés.
     * 
     * @param player Le numéro du joueur (1 ou 2).
     * @return true si le joueur a gagné, sinon false.
    */
    public boolean hasPlayerWon(int player) {
        // Implémente un algorithme pour détecter si le joueur a relié ses deux côtés.
        // Exemple simplifié : utiliser DFS ou BFS.
        boolean[][] closed = new boolean[size][size];
          
        if (player == 1) {
            for (int x = 0 ; x < size ; x++) {
                if (x >= size) continue;
                if (player == grid[0][x]) {
                    if (DFS(x, 0, closed, 1)) {
                        return true;
                    }
                }
            }
        } else {
            for (int y = 0 ; y < size ; y++) {
                if (y >= size) continue;
                if (player == grid[y][0]) {
                    if (DFS(0, y, closed, 2)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /**
     * Implémente une recherche en profondeur (DFS) pour vérifier une condition de victoire.
     * 
     * @param x      Coordonnée x actuelle.
     * @param y      Coordonnée y actuelle.
     * @param closed Tableau pour marquer les cellules déjà visitées.
     * @param target Le joueur à vérifier (1 ou 2).
     * @return true si une connexion est trouvée, sinon false.
     */
    public boolean DFS(int x, int y, boolean[][] closed, int target) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return false;  
        }
    
        if (closed[y][x]) {
            return false;
        }
        closed[y][x] = true;

        if ((y == size-1 && target == 1) || (x == size-1 && target == 2)) {
            return true;
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, -1}};

        for (int[] dir : directions) {
            int x1= x + dir[0];
            int y1 = y + dir[1];

            if (isValid(x1, y1)) {
                if (grid[y1][x1] == target) {
                    if (DFS(x1, y1, closed, target)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Vérifie si une cellule est dans les limites valides de la grille.
     * 
     * @param x Coordonnée x de la cellule.
     * @param y Coordonnée y de la cellule.
     * @return true si les coordonnées sont valides, sinon false.
     */
    private boolean isValid(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }


}