package model.result;

/**
 * Représente les résultats d'une partie.
 */
public class GameResult {

    private int gridSize;    
    private int budgetPlayer1; 
    private int budgetPlayer2;  
    private int winner;     
    private long duration;
    private int moves;
    private String algorithmPlayer1;
    private String algorithmPlayer2;
    private int nodeCountPlayer1;
    private int nodeCountPlayer2;
    private boolean ravePlayerOne;
    private boolean ravePlayerTwo;
    private long seedPlayerOne;
    private long seedPlayerTwo;

     /**
     * Constructeur pour initialiser les résultats d'une partie.
     * 
     * @param gridSize Taille de la grille du jeu
     * @param budgetPlayer1 Budget du joueur 1
     * @param budgetPlayer2 Budget du joueur 2
     * @param winner Le gagnant de la partie (1 pour le joueur 1, 2 pour le joueur 2)
     * @param duration Durée de la partie en millisecondes
     * @param moves Nombre de mouvements effectués
     * @param algorithmPlayer1 Algorithme utilisé par le joueur 1
     * @param algorithmPlayer2 Algorithme utilisé par le joueur 2
     * @param nodeCountPlayer1 Nombre de nœuds explorés par le joueur 1
     * @param nodeCountPlayer2 Nombre de nœuds explorés par le joueur 2
     * @param ravePlayerOne Indicateur si RAVE est activé pour le joueur 1
     * @param ravePlayerTwo Indicateur si RAVE est activé pour le joueur 2
     * @param seedPlayerOne Graine utilisée pour le joueur 1
     * @param seedPlayerTwo Graine utilisée pour le joueur 2
     */
    public GameResult(int gridSize, int budgetPlayer1, int budgetPlayer2, int winner, long duration, int moves, String algorithmPlayer1,
    String algorithmPlayer2, int nodeCountPlayer1, int nodeCountPlayer2, boolean ravePlayerOne, boolean ravePlayerTwo, long seedPlayerOne, long seedPlayerTwo) {
        this.gridSize = gridSize;
        this.budgetPlayer1 = budgetPlayer1;
        this.budgetPlayer2 = budgetPlayer2;
        this.winner = winner;
        this.duration = duration;
        this.moves = moves;
        this.algorithmPlayer1 = algorithmPlayer1;
        this.algorithmPlayer2 = algorithmPlayer2;
        this.nodeCountPlayer1 = nodeCountPlayer1;
        this.nodeCountPlayer2 = nodeCountPlayer2;
        this.ravePlayerOne = ravePlayerOne;
        this.ravePlayerTwo = ravePlayerTwo;
        this.seedPlayerOne = seedPlayerOne;
        this.seedPlayerTwo = seedPlayerTwo;
    }


    public int getGridSize() {
        return gridSize;
    }

    public int getBudgetPlayer1() {
        return budgetPlayer1;
    }

    public int getBudgetPlayer2() {
        return budgetPlayer2;
    }

    public int getWinner() {
        return winner;
    }

    public long getDuration() {
        return duration;
    }

    public int getMoves() {
        return moves;
    }


    public String getAlgorithmPlayer1() {
        return algorithmPlayer1;
    }


    public String getAlgorithmPlayer2() {
        return algorithmPlayer2;
    }


    public int getNodeCountPlayer1() {
        return nodeCountPlayer1;
    }


    public int getNodeCountPlayer2() {
        return nodeCountPlayer2;
    }


    public boolean isRavePlayerOne() {
        return ravePlayerOne;
    }


    public boolean isRavePlayerTwo() {
        return ravePlayerTwo;
    }


    public long getSeedPlayerOne() {
        return seedPlayerOne;
    }


    public long getSeedPlayerTwo() {
        return seedPlayerTwo;
    }

    public String toCSV() {
        return gridSize + "," + budgetPlayer1 + "," + budgetPlayer2 + "," + winner + "," + duration + "," + moves 
        + "," + algorithmPlayer1 + "," + algorithmPlayer2 + "," + nodeCountPlayer1 + "," + nodeCountPlayer2 + "," + ravePlayerOne + "," + ravePlayerTwo + "," + seedPlayerOne + "," + seedPlayerTwo;
    }

   
    


}