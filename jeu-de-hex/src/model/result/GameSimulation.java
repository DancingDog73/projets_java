package model.result;

import model.game.*;
import model.player.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Classe permettant de lancer des dimulation du jeu de hex en spécifiant certains paramètres.
 */
public class GameSimulation {

    private static int player1Wins = 0;
    private static int player2Wins = 0;
    
    
    /**
     * Lance les simulations depuis la ligne de commande
     */
    public static void runFromCommandLine(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java GameSimulation <nbSimulations> <budget1> <budget2> ");
            return;
        }
        
        try {
            int nbSimulations = Integer.parseInt(args[0]);
            int totalBudget = Integer.parseInt(args[1]);
            int proportionPlayerOne = Integer.parseInt(args[2]);
            int gridSize = Integer.parseInt(args[3]);
            double explorationParam = Math.sqrt(2);
            
            repeatSameSimulation(nbSimulations, totalBudget, proportionPlayerOne, gridSize, explorationParam, false, false);
        } catch (NumberFormatException e) {
            System.out.println("Erreur: Format de nombre invalide - " + e.getMessage());
        }
    }

    /**
     * Lance les simulations depuis un fichier de configuration XML
     */
    public static void runFromXMLConfig(String xmlPath) {
        try {
            // Vérifie l'existence du fichier
            File configFile = new File(xmlPath);
            if (!configFile.exists()) {
                System.err.println("Fichier de configuration non trouvé: " + xmlPath);
                return;
            }

            // Parse le XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(configFile);
            doc.getDocumentElement().normalize();

            // Extraction des valeurs avec validation
            NodeList simNodes = doc.getElementsByTagName("nbSimulations");
            NodeList budget1Nodes = doc.getElementsByTagName("budget1");
            NodeList budget2Nodes = doc.getElementsByTagName("budget2");
            

            // Vérifie que tous les paramètres sont présents
            if (simNodes.getLength() == 0 || budget1Nodes.getLength() == 0 || 
                budget2Nodes.getLength() == 0 ) {
                throw new IllegalArgumentException("Paramètres de configuration manquants");
            }

            // Conversion et validation des valeurs
            int nbSimulations = Integer.parseInt(simNodes.item(0).getTextContent());
            int budget1 = Integer.parseInt(budget1Nodes.item(0).getTextContent());
            int budget2 = Integer.parseInt(budget2Nodes.item(0).getTextContent());
            double explorationParam = Math.sqrt(2);

            if (nbSimulations <= 0 || budget1 <= 0 || budget2 <= 0 || explorationParam <= 0) {
                throw new IllegalArgumentException("Tous les paramètres doivent être positifs");
            }

            runSimulations(nbSimulations, budget1, budget2, explorationParam);
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture de la configuration XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

     /**
     * Lance une simulation unique avec des paramètres spécifiques pour les deux joueurs.
     * @param simIndex Index de la simulation (pour les logs).
     * @param budget1 Budget du joueur 1.
     * @param budget2 Budget du joueur 2.
     * @param explorationParam Paramètre d'exploration pour MCTS.
     * @return Résultat de la simulation.
     */
    private static String runSingleSimulationRandom(int simIndex, int budget1, int budget2, double explorationParam){
        Random random = new Random();
        // Calculs de budgets aléatoires pour les joueurs en fonction de la distribution gaussienne
        double mean1 = budget1 * 0.75; 
        double stdDev1 = budget1 * 0.2; 
        int randomBudget1 = (int) Math.max(budget1 / 2, Math.min(budget1, mean1 + stdDev1 * random.nextGaussian()));
        double mean2 = budget2 * 0.75;
        double stdDev2 = budget2 * 0.2;
        int randomBudget2 = (int) Math.max(budget2 / 2, Math.min(budget2, mean2 + stdDev2 * random.nextGaussian()));
        Player playerOne = new PlayerMCTS(randomBudget1, explorationParam, 1, random.nextBoolean());
        Player playerTwo = new PlayerMCTS(randomBudget2, explorationParam, 2, random.nextBoolean());
        
        Grid grid = new Grid(7 + random.nextInt(8));
        State initialState = new State(grid);
        Game game = new Game(playerOne, playerTwo, initialState);
        
        //Exécution du jeu et enregistrement des résultats
        game.play();
        GameResultManager.saveGameResult(game.getGameResult());
        if(game.getWinner().getId() == 1){
            player1Wins++;
        } else {
            player2Wins++;
        }
        
        return "Simulation " + (simIndex + 1) + " completed. Winner: Player " + game.getWinner().getId();
    }


    private static String runSingleSimulation(int simIndex, int totalBudget, int proportionPlayerOne, int gridSize, double explorationParam, boolean ravePlayerOne, boolean ravePlayerTwo){
        // Calculs de budgets aléatoires pour les joueurs en fonction de la distribution gaussienne
        int proportionOne = (proportionPlayerOne < 1 || proportionPlayerOne > 99) ? 60 : proportionPlayerOne;
        int budget1 = (totalBudget * proportionOne)/100;
        int budget2 = totalBudget - budget1;
        Player playerOne = new PlayerMCTS(budget1, explorationParam, 1, ravePlayerOne);
        Player playerTwo = new PlayerMCTS(budget2, explorationParam, 2, ravePlayerTwo);
        
        Grid grid = new Grid(gridSize);
        State initialState = new State(grid);
        Game game = new Game(playerOne, playerTwo, initialState);
        
        //Exécution du jeu et enregistrement des résultats
        game.play();
        GameResultManager.saveGameResult(game.getGameResult());
        if(game.getWinner().getId() == 1){
            player1Wins++;
        } else {
            player2Wins++;
        }
        
        return "Simulation " + (simIndex + 1) + " completed. Winner: Player " + game.getWinner().getId();
    }

    

    private static void repeatSameSimulation(int nbSimulations, int totalBudget, int proportionPlayerOne, int gridSize, double explorationParam, boolean ravePlayerOne, boolean ravePlayerTwo){
        long totalStartTime = System.currentTimeMillis();
        System.out.println("Starting " + nbSimulations + " simulations...");

        // Détermination du nombre de cœurs disponibles pour paralléliser les tâches
        int availableCores = Runtime.getRuntime().availableProcessors(); 
        ExecutorService executor = Executors.newFixedThreadPool(availableCores); 

        List<Future<String>> results = new ArrayList<>();

        //Souission des simulations à l'exécuteur
        for (int i = 0; i < nbSimulations; i++) {
            final int simIndex = i; 
            Future<String> result = executor.submit(() -> {
                return runSingleSimulation(simIndex, totalBudget, proportionPlayerOne, gridSize, explorationParam, ravePlayerOne, ravePlayerTwo);
            });
            results.add(result);

        }

        // Récupération des résultats
        for (Future<String> result : results) {
            try {
                System.out.println(result.get()); 
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown(); 
        System.out.println("All simulations completed.");
        long totalDuration = System.currentTimeMillis() - totalStartTime;
        
        // Affichage des statistiques finales
        System.out.println("\n========== Statistiques Finales ==========");
        System.out.println("Nombre total de simulations: " + nbSimulations);
        System.out.println("Victoires Joueur 1: " + player1Wins + " (" + 
            String.format("%.1f%%", (player1Wins * 100.0 / nbSimulations)) + ")");
        System.out.println("Victoires Joueur 2: " + player2Wins + " (" + 
            String.format("%.1f%%", (player2Wins * 100.0 / nbSimulations)) + ")");
        System.out.println("Durée totale: " + (totalDuration / 1000.0) + " secondes");
        System.out.println("Durée moyenne par simulation: " + 
            String.format("%.2f", (totalDuration / (double)nbSimulations / 1000.0)) + " secondes");
        System.out.println("=====================================");
        player1Wins = 0;
        player2Wins = 0;
    }


    /**
     * Lance les simulations en parallèle en utilisant un ExecutorService pour une exécution rapide.
     * @param nbSimulations Nombre de simulations à effectuer.
     * @param budget1 Budget du joueur 1.
     * @param budget2 Budget du joueur 2.
     * @param explorationParam Paramètre d'exploration pour MCTS.
     */
    private static void runSimulations(int nbSimulations, int budget1, int budget2, double explorationParam) {
        long totalStartTime = System.currentTimeMillis();
        System.out.println("Starting " + nbSimulations + " simulations...");

        // Détermination du nombre de cœurs disponibles pour paralléliser les tâches
        int availableCores = Runtime.getRuntime().availableProcessors(); 
        ExecutorService executor = Executors.newFixedThreadPool(availableCores); 

        List<Future<String>> results = new ArrayList<>();

        //Souission des simulations à l'exécuteur
        for (int i = 0; i < nbSimulations; i++) {
            final int simIndex = i; 
            Future<String> result = executor.submit(() -> {
                return runSingleSimulationRandom(simIndex, budget1, budget2, explorationParam);
            });
            results.add(result);

        }

        // Récupération des résultats
        for (Future<String> result : results) {
            try {
                System.out.println(result.get()); 
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown(); 
        System.out.println("All simulations completed.");
        long totalDuration = System.currentTimeMillis() - totalStartTime;
        
        // Affichage des statistiques finales
        System.out.println("\n========== Statistiques Finales ==========");
        System.out.println("Nombre total de simulations: " + nbSimulations);
        System.out.println("Victoires Joueur 1: " + player1Wins + " (" + 
            String.format("%.1f%%", (player1Wins * 100.0 / nbSimulations)) + ")");
        System.out.println("Victoires Joueur 2: " + player2Wins + " (" + 
            String.format("%.1f%%", (player2Wins * 100.0 / nbSimulations)) + ")");
        System.out.println("Durée totale: " + (totalDuration / 1000.0) + " secondes");
        System.out.println("Durée moyenne par simulation: " + 
            String.format("%.2f", (totalDuration / (double)nbSimulations / 1000.0)) + " secondes");
        System.out.println("=====================================");
        player1Wins = 0;
        player2Wins = 0;
    }

    public static void generateStats(boolean ravePlayerOne, boolean ravePlayerTwo, int startingBudget){
        int[] proportions = {10,20,30,40,50,60,70,80,90};
        int currentTotalBudget = startingBudget;
        for (int i = 13; i < 15; i++){
            for(int proportion: proportions){
                System.out.println("DEBUT D'UN LOT DE SIMULATION. TAILLE DE LA GRILLE :" + i +". BUDGET TOTAL" + currentTotalBudget + " PROPORTION JOUEUR 1: " + proportion);
                repeatSameSimulation(20, currentTotalBudget, proportion, i, Math.sqrt(2), ravePlayerOne, ravePlayerTwo);
                
            }
            currentTotalBudget = (int) (currentTotalBudget * 1.2);
        }
        
    }

    /**
     * Méthode principale pour lancer les simulations selon les paramètres fournis en ligne de commande ou à partir du fichier XML.
     */
    public static void main(String[] args) {
    
        if (args.length != 6) {
            System.out.println("Usage: java Main <nbSimulations> <totalBudget> <proportionPlayerOne> <gridSize> <ravePlayerOne> <ravePlayerTwo>");
            System.out.println("  <nbSimulations> : Nombre de simulations à exécuter");
            System.out.println("  <totalBudget> : Budget total à répartir entre les joueurs");
            System.out.println("  <proportionPlayerOne> : Proportion du budget alloué au joueur 1 (en pourcentage)");
            System.out.println("  <gridSize> : Taille de la grille pour les simulations");
            System.out.println("  <ravePlayerOne> : Activer RAVE pour le joueur 1 (true/false)");
            System.out.println("  <ravePlayerTwo> : Activer RAVE pour le joueur 2 (true/false)");
            return;
        }

        
            // Lecture des paramètres depuis la ligne de commande
            int nbSimulations = Integer.parseInt(args[0]);
            int totalBudget = Integer.parseInt(args[1]);
            int proportionPlayerOne = Integer.parseInt(args[2]);
            int gridSize = Integer.parseInt(args[3]);
            boolean ravePlayerOne = Boolean.parseBoolean(args[4]);
            boolean ravePlayerTwo = Boolean.parseBoolean(args[5]);

            // Appel de la méthode pour lancer les simulations
            GameSimulation.repeatSameSimulation(nbSimulations, totalBudget, proportionPlayerOne, gridSize, Math.sqrt(2), ravePlayerOne, ravePlayerTwo);

        
        GameResultManager.shutDown();
    }
}