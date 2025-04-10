package planning;

import modelling.*;
import java.util.*;


public class AStarPlanner extends AbstractPlanner {

    private Heuristic heuristic; // Voilà notre heuristique, elle nous permet d'estimer la distance qui nous sépare de notre but

    public AStarPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal, Set<Constraint> constraints, Heuristic heuristic) {
        super(initialState, actions, goal, constraints);
        this.heuristic = heuristic;
    }

    public AStarPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
        this(initialState, actions, goal, new HashSet<>(), heuristic);
    }


    @Override
    public List<Action> plan(){
        //Les variables ci-dessous vont nous permettre de faire le parcours de notre graphe d'état
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>(); //Ce dictionnaire nous permet de suivre les relations entre les noeuds explorés
        Map<Map<Variable, Object>, Action> plan = new HashMap<>(); 
        Map<Map<Variable, Object>, Double> distance = new HashMap<>(); //Ce dictionnaire associe à chaque noeud sa distance par rapport au noeud de départ
        Map<Map<Variable, Object>, Double> value = new HashMap<>(); //Ce dictionnaire permet d'associer à chaque noeud son heurisrique

        
        Queue<Map<Variable, Object>> open = new PriorityQueue<>(new StateComparator(distance)); //Ensemble des noeuds à explorer triés selon les distances
        open.offer(this.initialState); //On y met d'abord notre état initial
        father.put(this.initialState, null);
        distance.put(this.initialState,0.0); //La distance du noeud de départ par rapport à lui-même est évidemment 0
        
        double estimation = (double) this.heuristic.estimate(this.initialState); //On fait une estimation de la distance qui nous sépare du but
        value.put(this.initialState,estimation);
        this.exploredNodes = 0; //A chaque parcours, nous devons réinitialiser la variable exploredNodes
        while (!open.isEmpty()){
            Map<Variable, Object> instantiation = open.poll(); //On récupère l'état avec la plus petite heuristique c'est-à-dire l'état avec le plus proche du but
            incrementNodeCount();
            //Si notre but est atteint, on s'arre et on reconstruit le plan
            if(this.goal.isSatisfiedBy(instantiation)){
                return getBSFPlan(father, plan, instantiation);
            } else {
                //Sinon on explore les actions applicables à l'état actuel
                open.remove(instantiation);
                for(Action action : this.actions){
                    if(action.isApplicable(instantiation,constraints)){
                        Map<Variable, Object> next = action.successor(instantiation);
                        //Si l'état next(successeur n'est pas encore associée à une distance, on l'initialise à l'infini)
                        if(!distance.keySet().contains(next)){
                            distance.put(next, Double.POSITIVE_INFINITY);
                        }
                        //Si on trouve un meilleur chemin alors on met à jour nos valeurs
                        if (distance.get(next) > distance.get(instantiation) + action.getCost()){
                                distance.replace(next,distance.get(instantiation) + action.getCost());
                                value.put(next,distance.get(next) + this.heuristic.estimate(next));
                                father.put(next, instantiation);
                                plan.put(next, action);
                                open.offer(next);
                        }
                    }
                }
            }
            

        }
        return null;


    }


    public List<Action> getBSFPlan(Map<Map<Variable,Object>, Map<Variable,Object>> father,Map<Map<Variable,Object>, Action> plan, Map<Variable, Object> goal){
        // On reconstruit le plan une fois que le but est atteint
        List<Action> bfsPlan = new ArrayList<>();
        Map<Variable, Object> current = new HashMap<>(goal);
        //On remonte le dictionnaire father 
        while(father.get(current) != null && plan.get(current) != null){
            bfsPlan.add(plan.get(current));            
            current = father.get(current);
        }
        //Enfin on inverse  la liste avant de la renvoyer
        Collections.reverse(bfsPlan);
        return bfsPlan;
    }


    @Override
    public String toString () {
        return "AStarPlanner [\n\tInitial state : " + this.initialState + "\n\tActions : " + this.actions + "\n\tGoal : " + this.goal + "\n]";
    }

}