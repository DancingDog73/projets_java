package model.strategies.mcts;
import model.game.*;
import model.player.*;
import model.strategies.*;

import java.util.*;

/**
 * Implémente un algorithme de Monte Carlo Tree Search (MCTS) 
 * pour trouver le meilleur coup dans un jeu donné.
 */
public class MCTS implements Strategy{

    private int simulations;
    private double exploration_param;
    private int nodeCount;
    private static String name = "MCTS";
    private double[][] qRaveGrid;
    private int[][] nRaveGrid;
    private boolean useRave;
    private Random random;
    private long randomSeed;
    
    /**
     * Constructeur de la classe MCTS.
     * 
     * @param simulations       Le nombre de simulations à effectuer.
     * @param exploration_param Le paramètre d'exploration pour l'UCT.
     */
    public MCTS(int simulations, double exploration_param){
        this.simulations = simulations;
        this.exploration_param  = exploration_param;
        this.nodeCount = 0;
        this.useRave = false; 
        this.randomSeed = System.currentTimeMillis();
        this.random = new Random(randomSeed);
    }


    /**
     * Constructeur de la classe MCTS.
     * 
     * @param simulations       Le nombre de simulations à effectuer.
     * @param exploration_param Le paramètre d'exploration pour l'UCT.
     * @param useRave Le booléen qui détermine si le joueur utilise rave ou pas.
     */
    public MCTS(int simulations, double exploration_param, boolean useRave){
        this.simulations = simulations;
        this.exploration_param  = exploration_param;
        this.nodeCount = 0;
        this.useRave = useRave;
        this.randomSeed = System.currentTimeMillis();
        this.random = new Random(randomSeed);
    }


    /**
     * Trouve le meilleur mouvement pour un joueur donné en utilisant MCTS.
     * 
     * @param initialState L'état initial du jeu.
     * @param player       Le joueur pour lequel le mouvement est calculé.
     * @param opponent     L'adversaire du joueur.
     * @return Le meilleur mouvement trouvé après les simulations.
     */
    public Move findBestMove(State initialState, Player player, Player opponent){
        int size = initialState.getGridSize();
        this.qRaveGrid = new double[size][size];
        this.nRaveGrid = new int[size][size];


        Node root = new Node(initialState, null, null, player);
        this.nodeCount++;
        for(int i=0; i < this.simulations; i++){
            Node node = select(root);
            if(node.getState().isFinished() == 0){
                expand(node, player);
            }
            SimulationResult simResult = simulate(node, player, opponent);
            backpropagate(node, simResult.result, simResult.visitedMoves);
        }
        return root.getBestChild().getMove();
    }

    /**
     * Sélectionne le nœud avec la meilleure valeur UCT en parcourant l'arbre.
     * 
     * @param node Le nœud à partir duquel effectuer la sélection.
     * @return Le nœud sélectionné.
     */
    private Node select(Node node){
        while(!node.getChildren().isEmpty()){
            if(this.useRave){
                node = Collections.max(node.getChildren(), Comparator.comparingDouble(this::uctRaveValue)); 
            } else {
                node = Collections.max(node.getChildren(), Comparator.comparingDouble(this::uctValue)); 
            }
        }
        return node;
    }

    /**
     * Calcule la valeur UCT pour un nœud donné.
     * 
     * @param node Le nœud pour lequel calculer la valeur UCT.
     * @return La valeur UCT du nœud.
     */
    private double uctValue(Node node){
        if(node.getVisits() == 0){
            return Double.MAX_VALUE;
        }
        return node.getWinRate() + this.exploration_param * Math.sqrt(Math.log(node.getFather().getVisits()) / node.getVisits());
    }

    /**
     * Calcule la valeur UCT avec RAVE pour un nœud donné.
     * 
     * @param node Le nœud pour lequel calculer la valeur UCT-RAVE.
     * @return La valeur UCT-RAVE du nœud.
     */
    private double uctRaveValue(Node node){
        if(node.getVisits() == 0){
            return Double.MAX_VALUE;
        }
        int x = node.getMoveX();
        int y = node.getMoveY();
        //int nR = nRaveGrid[x][y];
        //int n = node.getVisits() > 0 ? node.getVisits() : 1;
        //double parameter = 10.0;
        //double coeff =  (double) nR /((double) n + nR + parameter);
        double coeff = 0.3;
        double uct = uctValue(node);
        double qrave = (nRaveGrid[x][y] > 0) ? (qRaveGrid[x][y] / (double) nRaveGrid[x][y]) : 0.5;
        double result = coeff*qrave + ((1-coeff)*uct);

        
        return result;
    }

    /**
     * Étend un nœud en ajoutant tous les mouvements possibles comme enfants.
     * 
     * @param node   Le nœud à étendre.
     * @param player Le joueur pour lequel les mouvements sont calculés.
     */
    private void expand(Node node, Player player){
        for(Move move : node.getState().getMoves(player)){
            State nextState = node.getState().next(move);
            node.addChild(new Node(nextState, node, move, player));
            this.nodeCount++;
        }
    }

    /**
     * Simule une partie complète à partir d'un nœud donné, en choisissant 
     * des mouvements aléatoires jusqu'à la fin de la partie.
     * 
     * @param node     Le nœud à partir duquel commencer la simulation.
     * @param player   Le joueur pour lequel la simulation est effectuée.
     * @param opponent L'adversaire du joueur.
     * @return 1.0 si le joueur gagne, sinon 0.0.
     */
    private SimulationResult simulate(Node node, Player player, Player opponent){
        List<Move> visitedMoves = new ArrayList<>();
        State currentState = node.getState();
        Player currentPlayer = player;
        while(currentState.isFinished() == 0){
            List<Move> moves = currentState.getMoves(currentPlayer);
            Move randomMove = moves.get(random.nextInt(moves.size()));
            if(currentPlayer.equals(player.getId())){
                visitedMoves.add(randomMove);
            }
            currentState = currentState.next(randomMove);
            currentPlayer = currentPlayer.equals(player.getId()) ? opponent : player;
        }
       
        double result = currentState.isFinished() == player.getId() ? 1.0 : 0.0;
        return new SimulationResult(result, visitedMoves);
    }

    /**
     * Rétro-propage le résultat d'une simulation en mettant à jour les statistiques
     * des nœuds parcourus.
     * 
     * @param node   Le nœud à partir duquel commencer la rétropropagation.
     * @param result Le résultat de la simulation (1.0 pour une victoire, 0.0 pour une défaite).
     */
    private void backpropagate(Node node, double result, List<Move> visitedmoves) {
       
        for(Move move : visitedmoves){
            int x = move.getPosition()[0];
            int y = move.getPosition()[1];
            nRaveGrid[x][y]++;
            qRaveGrid[x][y] += result;   
        }

        while (node != null) {
            node.updateStats(result);
            node = node.getFather();
        }
    }


    public int getSimulations() {
        return simulations;
    }


    public int getNodeCount() {
        return nodeCount;
    }

    public void enableRave(){
        this.useRave = true;  // Active l'utilisation de RAVE
    }

    public void disableRave(){
        this.useRave = false; // Désactive l'utilisation de RAVE
    }

    public boolean isUsingRave(){
        return this.useRave; // Retourne si RAVE est activé ou non
    }

    @Override
    public String getName() {
        return name; // Retourne le nom de la stratégie
    }


    @Override
    public long getRandomSeed(){
        return this.randomSeed; // Retourne la graine du générateur aléatoire
    }

    class SimulationResult {
        double result; // Résultat de la simulation (1.0 pour victoire, 0.0 pour défaite)
        List<Move> visitedMoves;
    
        public SimulationResult(double result, List<Move> visitedMoves) {
            this.result = result;
            this.visitedMoves = visitedMoves;
        }

       
    }
    

    
}