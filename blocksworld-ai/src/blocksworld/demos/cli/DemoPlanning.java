package blocksworld.demos.cli;

import java.util.*;
import modelling.*;
import planning.*;
import blocksworld.model.*;
import blocksworld.heuristics.*;
import blocksworld.demos.util.*;


public class DemoPlanning {

    private static final String SEPARATOR = "----------------------------------------------------------------------------------------";

     /**
     * Affiche l'en-tête général de la démonstration.
     * Fournit une description du problème des tours de Hanoï et des états initial et final.
     */
    public static void printHeader () {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("BLOCKS WORLD PLANNIFICATION DEMO (TOURS DE HANOÏ)");
        System.out.println(SEPARATOR);
        System.out.println("\nNos états initial et final sont ceux de la tour de Hanoï.\nPour l'état inititial, tous les blocs sont placés sur la première pile du plus grand\n(tout en bas) au plus petit (tout en haut) et toutes les autres piles sont libres.\nPour l'état final, les blocs sont disposés de la même manière que pour l'état inittial,\nmais sur la dernière pile ; toutes les autres piles étant libres.\n");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    //Affiche le manuel d'utilisation
    public static void printManual () {
        printHeader();
        System.out.println("Indiquez en arguments le nombre de blocs et de piles de votre monde puis le\nplannificateur que vous voulez utiliser selon les paramètres qui suivent :\n");
        System.out.println("1 -> DFS Planner\n2 -> BFS Planner \n3 -> Dijkstra Planner\n4 -> A* Planner (Null Heurisqtique)\n5 -> A* Planner (Heuristique sur le nombre de blocs mal placés)\n6 -> A* Planner (Heuristique sur la teneur du piège d'un bloc mal placé)\n");
        System.out.println(SEPARATOR);
        System.out.println("\nPar exemple :\n\tjava -cp ../build blocksworld.demos.cli.DemoPlanning 4 3 2\nvous donne le plan calculé par le BFS Planner pour un monde de 4 blocs et 3 piles\n");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    //Exécute un planificateur donné, mesure le temps d'exécution et affiche les résultats.
    private static void runPlanner (Planner planner, String description) {
        
        planner.activateNodeCount(true);
        long start = System.nanoTime();
        List<Action> plan = planner.plan();
        long end = System.nanoTime();
        long time = (end-start) / 1000000;
        
        printHeader();
        System.out.println(description);
        System.out.println(SEPARATOR);
        System.out.printf("Temps d'exécution         : %d ms\n", time);
        System.out.printf("Nombre de noeuds explorés : %d\n", planner.getExploredNodes());
        System.out.println(SEPARATOR);
        BlocksWorldUtil.printObjects((plan == null ? new HashSet<>() : new HashSet<>(plan)), "PLAN");
        System.out.println(SEPARATOR);
        System.out.println();
    }


    public static void main (String[] args) {

        try {

            int blocksCount = Integer.parseInt(args[0]);
            int stacksCount = Integer.parseInt(args[1]);
            int chosenPlanner = Integer.parseInt(args[2]);

            Map<Variable,Object> initialState = BlocksWorldUtil.getHanoiInitialState(blocksCount, stacksCount);
            Map<Variable,Object> finalState = BlocksWorldUtil.getHanoiFinalState(blocksCount, stacksCount);
            Goal goal = new BasicGoal(finalState);

            WorldActions actionsBuilder = new WorldActions(blocksCount, stacksCount);
            Set<Action> actions = actionsBuilder.getActions();

            // Par défaut, utiliser le DFS Planner
            Planner planner = new DFSPlanner(initialState, actions, goal);
             // Sélectionner le planificateur en fonction de l'argument fourni
            switch (chosenPlanner) {
                case 1:
                    runPlanner(planner, "DFS Planner");
                    break;
                case 2:
                    planner = new BFSPlanner(initialState, actions, goal);
                    runPlanner(planner, "BFS Planner");
                    break;
                case 3:
                    planner = new DijkstraPlanner(initialState, actions, goal);
                    runPlanner(planner, "Dijkstra Planner");
                    break;
                case 4:
                    planner = new AStarPlanner(initialState, actions, goal, new NullHeuristic());
                    runPlanner(planner, "A* Planner (Heuristique Nulle)");
                    break;
                case 5:
                    planner = new AStarPlanner(initialState, actions, goal, new MisplacedBlocksCountHeuristic(finalState, blocksCount, stacksCount));
                    runPlanner(planner, "A* Planner (Heuristique sur le nombre de blocs mal placés)");
                    break;
                case 6:
                    planner = new AStarPlanner(initialState, actions, goal, new MoveCountHeuristic(finalState, blocksCount, stacksCount));
                    runPlanner(planner, "A* Planner (Heuristique sur la teneur du piège d'un bloc mal placé)");
                    break;
                default:
                    printManual();
            }

        } catch (Exception e) {
            printManual();
        }

    }

}