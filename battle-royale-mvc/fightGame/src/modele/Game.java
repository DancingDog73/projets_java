package modele;

import java.util.*;
import playerModele.*;
import util.mvc.AbstractModeleEcoutable;
import modele.strategies.fillingstrategies.*;
import modele.strategies.playerstrategies.*;
import modele.factories.playerfactories.*;

import view.GameFrame;
public class Game extends AbstractModeleEcoutable {

    private Grid grid;
    private List<Player> players;



    public Game(int gridSize, List<Player> players,  GridFillingStrategy fillingStrategy){
        this.players = players;
        this.grid = new Grid(gridSize, players, fillingStrategy);
        for(Player player: this.players ){
            if(player instanceof PlayerWithStrategy) ((PlayerWithStrategy) player).setGrid(grid);
        }
    }

    public Grid getGrid() {
        return this.grid;
    }

    public void start(){
        System.out.println("=== Bienvenue dans le jeu de stratégie ! ===");
        System.out.println("Début de la partie avec " + players.size() + " joueurs.");
        grid.printGrid();
        while(!grid.isGameOver()) {
            Player activePlayer = grid.getActifPlayer();
            activePlayer.takeTurn();
            grid.update();
            this.fireChangement();
        }
    }
     public static void main(String[] argv){
        List<Player> players = new ArrayList<>();
        
        
        
        ConfigLoader loader = new ConfigLoader();
        loader.parse("src/modele/config.xml");
        GameConfig config = loader.getConfig();
        // Créer la factory des joueurs
        PlayerFactory playerFactory = new PlayerFactory(config.getPlayers());

        // Créer des joueurs à partir de leurs types
        Player player1 = playerFactory.createPlayer("Balanced", "Player1", null, 0, 0);
        Player player2 = playerFactory.createPlayer("Tanked", "Player2", null, 4, 4);
        Player player3 = playerFactory.createPlayer("Armed", "Player3", new AdvancedStrategy(), 0, 8);
        players.add(player1);
        players.add(player2);
        players.add(player3);

       
        GridFillingStrategy fillingStrategy = new RandomGridFillingStrategy();

        Game game = new Game(Config.GRID_HEIGHT, players, fillingStrategy);
        game.ajoutEcouteur(new GameFrame(game.getGrid(), true));

        int i = 0;
        for (Player player : players) {
            GridInterface gridProxy = new GridProxy(player, game.getGrid());
            GameFrame playerGameFrame = new GameFrame(gridProxy, false);
            playerGameFrame.setLocation(i*(playerGameFrame.getWidth()+50), 100);
            game.ajoutEcouteur(playerGameFrame);
            i++;
        }

        game.start();

    }
}