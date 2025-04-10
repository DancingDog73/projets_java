package blocksworld.demos.cli;

import java.util.*;
import modelling.*;
import planning.*;
import cp.*;
import blocksworld.model.*;
import blocksworld.demos.util.*;


public class DemoCP {

    private static final String SEPARATOR = "----------------------------------------------------------------------------------------------------------";


    public static void printHeader () {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("BLOCKS WORLD - SATISFACTION DE CONSTRAINTES");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    public static void printManual () {
        printHeader();
        System.out.println("Indiquez en arguments le nombre de blocs et de piles de votre monde puis le\nsolver et les contraintes que vous voulez utiliser selon les paramètres qui suivent :\n");
        System.out.println("1 -> Pas de contraintes\n2 -> Contraintes de base \n3 -> Constraintes de base et de croissance\n4 -> Contraintes de base et de décroissance\n5 -> Contraintes de base et de régularité\n6 -> Toutes contraintes (base, croissance et régularité)\n");
        System.out.println("1 -> BackTrack Solver\n2 -> MAC Solver \n3 -> MAC Heuristic Solver (DomainSizeVariable et RandomValue)\n4 -> MAC Heuristic Solver (NbConstraintsVariable et RandomValue)\n");
        System.out.println(SEPARATOR);
        System.out.println("\nPar exemple :\n\tjava -cp ../build blocksworld.demos.cli.DemoCP 4 3 2 1\nvous donne la solution calculée par le Backtrack Solver pour un monde de 4 blocs et3 piles\nauquel on impose les contraintes de bases\n");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    private static void runSolver (Solver solver, String description) {
        
        long start = System.nanoTime();
        Map<Variable,Object> instantiation = solver.solve();
        long end = System.nanoTime();
        long time = (end-start) / 1000000;
        
        printHeader();
        System.out.printf("\t%s\n", description);
        System.out.println(SEPARATOR);
        System.out.printf("Temps d'exécution         : %d ms\n", time);
        System.out.println(SEPARATOR);
        BlocksWorldUtil.printState((instantiation == null ? new HashMap<>() : instantiation), "Solution");
        System.out.println(SEPARATOR);
        System.out.println();
    }


    public static void main (String[] args) {

        try {

            int blocksCount = Integer.parseInt(args[0]);
            int stacksCount = Integer.parseInt(args[1]);
            int chosenConstraints = Integer.parseInt(args[2]);
            int chosenSolver = Integer.parseInt(args[3]);

            WorldVariables worldVariables = new WorldVariables(blocksCount, stacksCount);
            Set<Variable> variables = worldVariables.getVariables();
            Set<Constraint> constraints = new HashSet<>();

            switch (chosenConstraints) {
                case 1:
                    break;
                case 2:
                    constraints.addAll(BlocksWorldUtil.getBasicConstraints(blocksCount, stacksCount));
                    break;
                case 3:
                    constraints.addAll(BlocksWorldUtil.getGrowthIConstraints(blocksCount, stacksCount));
                    break;
                case 4:
                    constraints.addAll(BlocksWorldUtil.getGrowthDConstraints(blocksCount, stacksCount));
                    break;
                case 5:
                    constraints.addAll(BlocksWorldUtil.getRegularityConstraints(blocksCount, stacksCount));
                    break;
                case 6:
                    constraints.addAll(BlocksWorldUtil.getAllConstraints(blocksCount, stacksCount));
                    break;
                default:
                    printManual();
            }

            Solver solver = new BacktrackSolver(variables, constraints);
            String description = "BACKTRACK SOLVER";

            switch (chosenSolver) {
                case 1:
                    break;
                case 2:
                    solver = new MACSolver(variables, constraints);
                    description = "MAC SOLVER";
                    break;
                case 3:
                    solver = new HeuristicMACSolver(variables, constraints, new DomainSizeVariableHeuristic(false), new RandomValueHeuristic(new Random()));
                    description = "HEURISTIC MAC SOLVER (plus petit domaine et random value)";
                    break;
                case 4:
                    solver = new HeuristicMACSolver(variables, constraints, new NbConstraintsVariableHeuristic(constraints, false), new RandomValueHeuristic(new Random()));
                    description = "HEURISTIC MAC SOLVER (moins de contraintes et random value)";
                    break;
                default:
                    printManual();
            }

            runSolver(solver, description);

        } catch (Exception e) {
            printManual();
        }


    }

}