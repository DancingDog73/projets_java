package model;
import model.game.*;
import model.player.*;

public class Main {
    public static void main(String[] args) {
        // Création des joueurs
        /*Player playerOne = new PlayerRandom(); // Joueur 1 aléatoire
        Player playerTwo = new PlayerRandom(); // Joueur 2 aléatoire*/
        
        Player playerOne = new PlayerMCTS(10, Math.sqrt(2), 1, false); // Joueur 1 aléatoire
        Player playerTwo = new PlayerMCTS(10, Math.sqrt(2), 2, true);

        // Création de la grille et de l'état initial
        Grid grid = new Grid(8);
        State initialState = new State(grid);

        // Création du jeu
        Game game = new Game(playerOne, playerTwo, initialState);

        // Lancement de la partie
        System.out.println("Début de la partie Hex !");
        game.play();

        // Affichage de l'état final
        System.out.println("Partie terminée !");
        System.out.println("État final de la grille :");
        System.out.println(game.getCurrentState().getGrid());

        // Vérification du gagnant
        
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println("Le joueur gagnant est : " + (winner == playerOne ? "Joueur 1" : "Joueur 2"));
        } else {
            System.out.println("Pas de gagnant !");
        }
            
    }
}
