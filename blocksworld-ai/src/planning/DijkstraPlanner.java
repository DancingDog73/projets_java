package planning;

import modelling.*;
import java.util.*;


public class DijkstraPlanner extends AbstractPlanner {

    public DijkstraPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal, Set<Constraint> constraints) {
        super(initialState, actions, goal, constraints);
    }

    public DijkstraPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this(initialState, actions, goal, new HashSet<Constraint>());
    }


    @Override
    public List<Action> plan(){
        
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>(); //Ce dictionnaire nous permet de suivre les relations entre les noeuds explorés
        Map<Map<Variable, Object>, Action> plan = new HashMap<>(); // Le plan qu'on retournera(il associe chaque état à son action)
        Map<Map<Variable, Object>, Double> distance = new HashMap<>(); //Ce dictionnaire associe à chaque noeud sa distance par rapport au noeud de départ
        StateComparator stateComparator = new StateComparator(distance);
        Queue<Map<Variable, Object>> open = new PriorityQueue<>(stateComparator); //Les noeuds à explorer
        Queue<Map<Variable, Object>> goals = new PriorityQueue<>(stateComparator); //Les buts atteints
        
        distance.put(this.initialState,0.0); //La distance de l'état initial à lui-même est 0
        father.put(this.initialState, new HashMap<>()); //L'état initial n'a pas de père
        open.offer(this.initialState);
        this.exploredNodes = 0; //on réinitialise les noeuds explorés
        
        while(!open.isEmpty()){
            Map<Variable, Object> instantiation = open.poll(); //On prend le noeud à explorer le plus proche
            open.remove(instantiation); //On retire ce noeuds minimal des noeuds à explorer
            incrementNodeCount(); //incrémentation de nodescount
            if (this.goal.isSatisfiedBy(instantiation)){
                goals.offer(instantiation); //Si notre noeud actuel satisfait notre objectif on l'ajoute aux buts atteints
            }
            for (Action action : this.actions){
                if(action.isApplicable(instantiation,constraints)){
                    //On parcourt les actions applicables
                    Map<Variable, Object> next = action.successor(instantiation);
                    if (!distance.keySet().contains(next)){
                        distance.put(next, Double.POSITIVE_INFINITY); //Si le noeud n'appartient pas à distance(on l'ajoute et on initialise sa distance à l'infini)
                    }
                    if (distance.get(next) > distance.get(instantiation) + action.getCost()){ //On vérifie que la distance du noeud est initialisé à la bonne valeur en comparant sa valeur dans dist avec le calcul de sa vraie valeur
                        distance.replace(next, distance.get(instantiation) + action.getCost()); //On initialise sa distance
                        father.put(next, instantiation); //On l'ajoute à father avec son parent
                        plan.put(next, action); //On ajoute l'action au plan
                        open.offer(next); //On ajoute ce noeud aux noeuds à explorer                      
                    }
                }
            }
        }
        

        if(goals.isEmpty()){
            return null; //Si l'ensemble des buts est vide on retourne null
        } else {
            return getDijkstraPlan(father, plan, goals, distance); //Sinon on reconstruit le plan et on le renvoie
        }

    }


    public List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,Map<Map<Variable, Object>, Action> plan, Queue<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Double> distance){
        //Liste des actions du plan
        List<Action> dijPlan = new ArrayList<>();
        
        Map<Variable, Object> goal = goals.poll(); //On trouve le but avec la distance minimale

        //On remonte au but initial en partant du but 
        while(father.get(goal) != null && plan.get(goal) != null){
            dijPlan.add(plan.get(goal)); //On ajoute l'action qui correspond
            goal = father.get(goal); //On passe à l'état parent        
        }
        //On inverse la liste pour avoir le plan dans le bon ordre
        Collections.reverse(dijPlan);
        return dijPlan;

    }


    @Override
    public String toString () {
        return "DijkstraPlanner [\n\tInitial state : " + this.initialState + "\n\tActions : " + this.actions + "\n\tGoal : " + this.goal + "\n]";
    }

}