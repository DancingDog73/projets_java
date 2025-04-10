package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import model.game.Game;
import model.game.Grid;
import model.game.State;
import model.player.Player;
import model.player.PlayerMCTS;

public class Demo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hex Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Player playerOne = new PlayerMCTS(10, Math.sqrt(2)); // Joueur 1 aléatoire
        Player playerTwo = new PlayerMCTS(10, Math.sqrt(2));

        // Création de la grille et de l'état initial
        Grid grid = new Grid();
        State initialState = new State(grid);

        Game game = new Game(playerOne, playerTwo, initialState);
        HexGameView hexGameView = new HexGameView(game);
        HexGameMenu hexGameMenu = new HexGameMenu(game);
        game.addListener(hexGameView);
        frame.setLayout(new BorderLayout());
        frame.add(hexGameMenu, BorderLayout.NORTH);
        frame.add(hexGameView, BorderLayout.CENTER);

        frame.setSize(1100, 740);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
