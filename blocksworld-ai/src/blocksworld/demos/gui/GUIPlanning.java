package blocksworld.demos.gui;

import java.util.*;
import java.util.List;
import blocksworld.model.*;
import blocksworld.demos.util.*;
import modelling.*;
import planning.*;
import planning.Action;
import cp.*;
import bwmodel.*;
import bwui.*;
import javax.swing.*;
import blocksworld.heuristics.*;


public class GUIPlanning {

    private static final String SEPARATOR = "---------------------------------------------------------------------------------------------------------------------------------------";

    public static void printManual () {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("BLOCKS WORLD - HANOÏ SIMULATION");
        System.out.println(SEPARATOR);
        System.out.println("La configuration des tours de hanoï a pour état initial tous les blocs sur la première pile, les autres étant libres\net pour état final tous les blocs sur la dernière pile, les autres étant libres.\nElle interdit par ailleurs de placer un bloc sur un bloc plus petit (Contraintes n°4)");
        System.out.println(SEPARATOR);
        System.out.println();
        System.out.println("Indiquez en arguments le nombre de blocs et de piles de votre monde puis le\nplanner, les contraintes que vous voulez utiliser et le mode de simulation selon les paramètres qui suivent :\n");
        System.out.println("1 -> DFS Planner\n2 -> BFS Planner \n3 -> Dijkstra Planner\n4 -> A* Planner (Null Heuristique)\n5 -> A* Planner (Heuristique sur le nombre de blocs mal placés)\n6 -> A* Planner (Heuristique sur la teneur du piège d'un bloc mal placé)\n");
        System.out.println("1 -> Pas de contraintes\n2 -> Contraintes de base \n3 -> Constraintes de base et de croissance\n4 -> Contraintes de base et de décroissance (choisir pour une tour de hanoï classique)\n5 -> Contraintes de base et de régularité\n6 -> Toutes contraintes (base, croissance et régularité)\n");
        System.out.println("n -> Le nombre de secondes à attendre après chaque étape (facultatif, 1 par défaut)");
        System.out.println(SEPARATOR);
        System.out.println("\nPar exemple :\n\tjava -cp ../build:../lib/blocksworld.jar blocksworld.demos.gui.GUIPlanning 5 3 6 4 2\nvous simule la résolution d'une tour de hanoï de 5 blocs et 3 piles\nobtenu avec le A* Planner (Heuristique sur la teneur du piège d'un bloc mal placé)\nen lui imposant les contraintes de base et de décroissance\nles étapes de la simulation étant espacées de 2 secondes");
        System.out.println(SEPARATOR);
        System.out.println();
    }


    private final static BWState<Integer> makeBWState (Map<Variable,Object> instantiation, int blocksCount, BlocksWorldStructure variableBuilder) {
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(blocksCount);
        for (int b = 0; b < blocksCount; b++) {
            Variable onB = variableBuilder.onBlockVariable(b); // get instance of Variable for "on_b"
            int under = (int) instantiation.get(onB);
            if (under >= 0) { // if the value is a block (as opposed to a stack)
                builder.setOn(b, under);
            }
        }
        BWState<Integer> state = builder.getState();
        return state;
    }


    public static void main (String[] args) {

        try {

            int blocksCount = Integer.parseInt(args[0]);
            int stacksCount = Integer.parseInt(args[1]);
            int chosenPlanner = Integer.parseInt(args[2]);
            int chosenConstraints = Integer.parseInt(args[3]);

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

            WorldVariables variableBuilder = new WorldVariables(blocksCount, stacksCount);
            Map<Variable,Object> initialState = BlocksWorldUtil.getHanoiInitialState(blocksCount, stacksCount);
            Map<Variable,Object> finalState = BlocksWorldUtil.getHanoiFinalState(blocksCount, stacksCount);
            Goal goal = new BasicGoal(finalState);
            WorldActions actionsBuilder = new WorldActions(blocksCount, stacksCount);
            Set<Action> actions = actionsBuilder.getActions();

            Planner planner = new DFSPlanner(initialState, actions, goal, constraints);

            switch (chosenPlanner) {
                case 1:
                    break;
                case 2:
                    planner = new BFSPlanner(initialState, actions, goal, constraints);
                    break;
                case 3:
                    planner = new DijkstraPlanner(initialState, actions, goal, constraints);
                    break;
                case 4:
                    planner = new AStarPlanner(initialState, actions, goal, constraints, new NullHeuristic());
                    break;
                case 5:
                    planner = new AStarPlanner(initialState, actions, goal, constraints, new MisplacedBlocksCountHeuristic(finalState, blocksCount, stacksCount));
                    break;
                case 6:
                    planner = new AStarPlanner(initialState, actions, goal, constraints, new MoveCountHeuristic(finalState, blocksCount, stacksCount));
                    break;
                default:
                    printManual();
            }

            List<Action> plan = planner.plan();
            if (plan == null) {
                plan = new ArrayList<>();
            }

            BWIntegerGUI gui = new BWIntegerGUI(blocksCount);
            JFrame frame = new JFrame("SIMULATION HANOI (" + blocksCount + " blocs et " + stacksCount + " piles)");
            BWState<Integer> bwState = makeBWState(initialState, blocksCount, variableBuilder);
            BWComponent<Integer> component = gui.getComponent(bwState);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(component);
            frame.pack();
            frame.setSize(1200, 800);
            frame.setVisible(true);
            
            // Playing plan
            Map<Variable,Object> state = new HashMap<>(initialState);
            for (Action a: plan) {
                System.out.println(a);
                try { Thread.sleep(args.length > 4 && BlocksWorldUtil.isInt(args[4].trim()) ? Integer.parseInt(args[4].trim())*1000 : 1000); }
                catch (InterruptedException e) { e.printStackTrace(); }
                state=a.successor(state);
                component.setState(makeBWState(state, blocksCount, variableBuilder));
            }
            System.out.println("Simulation of plan: done.");

        } catch (Exception e) {
            printManual();
        }


    }

}