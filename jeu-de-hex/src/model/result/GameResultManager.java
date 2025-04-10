package model.result;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Classe responsable de la gestion des résultats de jeu.
 * Elle permet d'ajouter, de sauvegarder de manière asynchrone, et de charger les résultats depuis un fichier CSV.
 */
public class GameResultManager {
    // Chemin du fichier où les résultats de jeu sont stockés
    private static final String FILE_PATH = "src/model/result/rave-27-28csv";  
    // Taille du lot à enregistrer avant de flusher les résultats
    private static final int BATCH = 10;
    // Tampon pour stocker les résultats avant de les écrire dans le fichier
    private static final Queue<String> resultBuffer = new ConcurrentLinkedQueue<>();
    // Pool de threads pour gérer l'écriture asynchrone des résultats
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);


    /**
     * Sauvegarde un résultat de jeu dans la file d'attente. Si le tampon atteint la taille du lot, 
     * les résultats sont écrits de manière asynchrone.
     * 
     * @param result Le résultat de jeu à sauvegarder
     */
    public static void saveGameResult(GameResult result) {
        resultBuffer.add(result.toCSV());
        // Si le tampon contient suffisamment de résultats, on les écrit en arrière-plan
        if (resultBuffer.size() >= BATCH) {
            flushResultsAsync(); 
        }
    }

     /**
     * Effectue l'écriture des résultats en arrière-plan de manière asynchrone.
     * Les résultats sont récupérés depuis le tampon et écrits dans le fichier CSV.
     */
    public static void flushResultsAsync(){
        if(resultBuffer.isEmpty()) return;

        executor.submit(() -> {
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))){
                while(!resultBuffer.isEmpty()){
                    writer.write(resultBuffer.poll());
                    writer.newLine();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Arrête l'exécution de l'ExecutorService en attendant la fin de l'écriture des résultats.
     * 
     * La méthode s'assure que tous les résultats sont écrits avant de fermer les ressources.
     */
    public static void shutDown(){
        flushResultsAsync(); //Onn écrit les résultats restants
        executor.shutdown(); //Arrêt du pool de threads
        try{
            // Attendre que tous les threads terminent l'exécution
            executor.awaitTermination(BATCH, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

     /**
     * Charge les résultats de jeu depuis le fichier CSV.
     * Chaque ligne est  convertie en un objet GameResult.
     * 
     * @return Une liste d'objets GameResult représentant les résultats chargés.
     */
    public static List<GameResult> loadGameResults() {
        List<GameResult> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if(values.length != 14){
                    System.err.println("Ligne ignorée (format incorrect) : " + line);
                    continue;
                }
                try {
                    int gridSize = Integer.parseInt(values[0]);
                    int budgetPlayer1 = Integer.parseInt(values[1]);
                    int budgetPlayer2 = Integer.parseInt(values[2]);
                    int winner = Integer.parseInt(values[3]);
                    long duration = Long.parseLong(values[4]);
                    int moves = Integer.parseInt(values[5]);
                    String algorithmPlayer1 = values[6];
                    String algorithmPlayer2 = values[7];
                    int nodeCountPlayer1 =  Integer.parseInt(values[8]);
                    int nodeCountPlayer2 =  Integer.parseInt(values[9]);
                    boolean ravePlayerOne = Boolean.parseBoolean(values[10]);
                    boolean ravePlayerTwo = Boolean.parseBoolean(values[11]);
                    long seedPlayerOne = Long.parseLong(values[12]);
                    long seedPlayerTwo = Long.parseLong(values[13]);
                    results.add(new GameResult(gridSize, budgetPlayer1, budgetPlayer2, winner, duration,  moves, algorithmPlayer1, algorithmPlayer2, nodeCountPlayer1, nodeCountPlayer2, ravePlayerOne, ravePlayerTwo, seedPlayerOne, seedPlayerTwo));
                } catch(NumberFormatException e){
                    System.err.println("Erreur de parsing : " + e.getMessage() + " (Ligne ignorée : " + line + ")");
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Initialise le fichier CSV en créant un en-tête si le fichier n'existe pas encore.
     */
    public static void initializeCSV() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("gridSize,budgetPlayer1,budgetPlayer2,winner,duration,movesCount,algorithmPlayer1,algorithmPlayer2,nodeCountPlayer1,nodeCountPlayer2,ravePlayerOne,ravePlayerTwo,seedPlayerOne,seedPlayerTwo");
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Méthode principale pour tester la gestion des résultats de jeu.
     * 
     */    
    public static void main(String[] args) {
        // Initialisation du fichier CSV
        GameResultManager.initializeCSV();

        // Exemple de résultat de partie
        GameResult result = new GameResult(14, 100, 100, 1, 5000, 196, "MCTS", "MCTS", 1000, 1000, false, false, 1000, 1000); // taille 14x14, budget 100, joueur 1 gagne, durée 5000ms
        GameResultManager.saveGameResult(result);
        shutDown();

        // Chargement des résultats
        List<GameResult> results = GameResultManager.loadGameResults();
        for (GameResult r : results) {
            System.out.println("Grille: " + r.getGridSize() + "x" + r.getGridSize() + ", Budget Joueur 1: " + r.getBudgetPlayer1() + ", Budget Joueur 2: " + r.getBudgetPlayer2() + ", Vainqueur: " + (r.getWinner() == 1 ? "Joueur 1" : "Joueur 2") + ", Durée: " + r.getDuration() + "ms");
        }
    }
    
}
