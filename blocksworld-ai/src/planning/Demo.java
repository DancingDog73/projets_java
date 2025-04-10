package planning;


import java.util.*;
import modelling.*;

public class Demo {
    public static void main (String[] argv){
        //On initialise cinq variables
        Variable a = new Variable("a", Set.of());
        Variable b = new Variable("b", Set.of());
        Variable c = new Variable("c", Set.of());
        Variable d = new Variable("d", Set.of());
        //Initialisation de l'état initial
        Map<Variable,Object> initialState = Map.of(a, 2, b, 3, c, 51, d, 17);


        //On construit chaque action qui sera disponible pour les parcours 
        Map<Variable,Object> precondition1 = Map.of(a, 2, b, 3);
        Map<Variable,Object> effect1 = Map.of(c, 12);
        BasicAction action1 = new BasicAction(precondition1, effect1, 3);
        
        Map<Variable,Object> precondition2 = Map.of(d, 17, b, 3);
        Map<Variable,Object> effect2 = Map.of(d, 5);
        BasicAction action2 = new BasicAction(precondition2, effect2, 2);

        Map<Variable,Object> precondition3 = Map.of(c, 12, d, 5);
        Map<Variable,Object> effect3 = Map.of(a, 1);
        BasicAction action3 = new BasicAction(precondition3, effect3, 1);

        Map<Variable,Object> precondition4 = Map.of(a, 0);
        Map<Variable,Object> effect4 = Map.of(a, 73);
        BasicAction action4 = new BasicAction(precondition4, effect4, 5);

        Map<Variable,Object> precondition5 = Map.of(b, 3, d, 5);
        Map<Variable,Object> effect5 = Map.of(a, 12);
        BasicAction action5 = new BasicAction(precondition5, effect5, 1);

        Map<Variable,Object> precondition6 = Map.of(c, 12);
        Map<Variable,Object> effect6 = Map.of(a, 22, b, 33, c, 44, d, 55);
        BasicAction action6 = new BasicAction(precondition6, effect6, 4);

        Map<Variable,Object> precondition7 = Map.of(b, 3);
        Map<Variable,Object> effect7 = Map.of(c, 17);
        BasicAction action7 = new BasicAction(precondition7, effect7, 7);

        Map<Variable,Object> precondition8 = Map.of(a, 22);
        Map<Variable,Object> effect8 = Map.of(a, 1, b, 3, c, 17, d, 5);
        BasicAction action8 = new BasicAction(precondition8, effect8, 1);
        //On met les actions dans un ensemble
        Set<Action> actions = Set.of(
            action1,
            action2,
            action3,
            action4,
            action5,
            action6,
            action7,
            action8
        );
        System.out.println("ETAT INITIAL : "+initialState);
        System.out.println("Ensemble des actions : "+actions);

        //Enfin on exécute chaque algorithme de parcours sur notre graphe
        //DFS
        Map<Variable,Object> goalMap = Map.of(a, 1, b, 3, c, 17, d, 5);
        BasicGoal goal = new BasicGoal(goalMap);
        DFSPlanner dfs = new DFSPlanner(initialState, actions, goal);
        dfs.activateNodeCount(true);
        System.out.println("DEBUT DFS");
        List<Action> planDFS = dfs.plan();
        System.out.println("Plan retourné : " +planDFS);
        System.out.println("DFS explored nodes : "+dfs.getExploredNodes());
        System.out.println("FIN DFS\n");


        //BFS
        BFSPlanner bfs = new BFSPlanner(initialState, actions, goal);
        bfs.activateNodeCount(true);
        System.out.println("DEBUT BFS");
        List<Action> planBFS = bfs.plan();
        System.out.println("Plan retourné : " +planBFS);
        System.out.println("BFS explored nodes : "+bfs.getExploredNodes());
        System.out.println("FIN BFS\n");

        //Dijkstra 
        DijkstraPlanner dijkstra = new DijkstraPlanner(initialState, actions, goal);
        dijkstra.activateNodeCount(true);
        System.out.println("DEBUT Dijkstra");
        List<Action> planDijkstra = dijkstra.plan();
        System.out.println("Plan retourné : " +planDijkstra);
        System.out.println("Dijkstra explored nodes : "+dijkstra.getExploredNodes());
        System.out.println("FIN Dijkstra\n");

        //Astar
        MockHeuristic heuristic = new MockHeuristic();
        AStarPlanner aStar = new AStarPlanner(initialState, actions, goal, heuristic);
        aStar.activateNodeCount(true);
        System.out.println("DEBUT ASTAR");
        List<Action> planAStar = aStar.plan();
        System.out.println("Plan retourné : " +planAStar);
        System.out.println("ASTAR explored nodes : "+aStar.getExploredNodes());
        System.out.println("FIN ASTAR\n");
        

        
    }
}