package planning;

import modelling.*;
import java.util.*;


public class BFSPlanner extends AbstractPlanner {

    public BFSPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal, Set<Constraint> constraints) {
        super(initialState, actions, goal, constraints);
    }

    public BFSPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this(initialState, actions, goal, new HashSet<>());
    }


    @Override
    public List<Action> plan(){ 
        Set<Map<Variable,Object>> closed = new HashSet<>();  //Ensemble des noeuds déjà explorés
        Queue<Map<Variable,Object>> open = new LinkedList<>();  //File d'attente des noeuds à explorer
        Map<Map<Variable,Object>, Map<Variable,Object>> father = new HashMap<>(); //Ce dictionnaire nous permet de suivre les relations entre les noeuds explorés
        Map<Map<Variable,Object>, Action> plan = new HashMap<>(); //Le plan qui associe à chaque état une action
        father.put(this.initialState, null); //Le noeud de départ n'a pas de parents
        closed.add(this.initialState); //Le noeud de départ n'a pas de parents
        open.add(this.initialState);  //On l'ajoute aux noeuds à explorer
        this.exploredNodes = 0; //On réinitialise le nombre de noeuds explorés
        if (this.goal.isSatisfiedBy(this.initialState)){
            return new ArrayList<>();
        } 
        while (!open.isEmpty()){
            Map<Variable, Object> instantiation = open.poll();
            closed.add(instantiation);
            incrementNodeCount(); //On incrémente nodeCount
            for (Action action : this.actions){
                if (action.isApplicable(instantiation,constraints)){
                    Map<Variable, Object> next = action.successor(instantiation); //Successeur de notre état
                    //Si l'état n'a pas encore été exploré, on l'ajoute aux noeuds à explorer 
                    if (!closed.contains(next) && !open.contains(next)){ 
                        father.put(next,instantiation);  //On enregistre le successeur dans father
                        plan.put(next,action); //Et on l'ajoute au plan
                        if (this.goal.isSatisfiedBy(next)){
                            return plan_aux(father, plan, next); //Si on a atteint l'objectif alors on génère le plan d'action 
                        } else {
                            open.add(next); //On ajoute le successeur aux noeuds à explorer
                        }
                    }
                }
            }

        }
        return null;

    }

    //Mèthode pour reconstruire le plan à partir du but
    public List<Action> plan_aux(Map<Map<Variable,Object>, Map<Variable,Object>> father,Map<Map<Variable,Object>, Action> plan, Map<Variable, Object> goal){
        //Plan à retourner
        List<Action> bfsPlan = new ArrayList<>();
        //On remonte à l'état initial en suivant les relations parent-enfant
        Map<Variable, Object> current = new HashMap<>(goal);

        while(current != null && plan.get(current) != null){
            bfsPlan.add(plan.get(current));            
            current = father.get(current);
        }
        //On inverse la liste pour la remettre dans le bon ordre
        Collections.reverse(bfsPlan);
        return bfsPlan;
    }


    @Override
    public String toString () {
        return "BFSPlanner [\n\tInitial state : " + this.initialState + "\n\tActions : " + this.actions + "\n\tGoal : " + this.goal + "\n]";
    }

}