package planning;

import modelling.*;
import java.util.*;

public class DFSPlanner extends AbstractPlanner {

    public DFSPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal, Set<Constraint> constraints) {
        super(initialState, actions, goal, constraints);
    }

    public DFSPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this(initialState, actions, goal, new HashSet<>());
    }

    
    @Override
    public List<Action> plan(){
        List<Action> plan2 = new ArrayList<>(); //Notre liste qui va stocker le plan
        Set<Map<Variable,Object>> closed = new HashSet<>(); //Ensemble des noeuds déjà explorés
        Map<Variable,Object> initialState = this.initialState;
        this.exploredNodes = 0; //On réinitialise le compteur de noeuds
        //Notre méthode s'appuie sur une fonction récursive pour générer le plan
        List<Action> plan = plan2(initialState, plan2, closed);
        return plan;
        
    }


    public List<Action> plan2(Map<Variable,Object> initialState, List<Action> plan, Set<Map<Variable,Object>> closed){
        //On vérifie si le but est atteint pour l'état dans lequel on est
        if (this.goal.isSatisfiedBy(initialState)){
            return plan;
        } else {
            for (Action action : this.actions){
                //On parcourt les action et on fait des tests sur celle qui sont appliquables pour notre état
                if (action.isApplicable(initialState,constraints)){
                    Map<Variable,Object> next = action.successor(initialState); //On applique l'action sur notre état
                    if (!closed.contains(next)){ //On vérifie que notre état n'a pas encore été visité
                        plan.add(action); //Si notre successeur n'a pas encore été exploré on l'ajoute au plan
                        closed.add(next); //On ajoute le successeur aux noeuds visités
                        incrementNodeCount(); //On incrémente le nombre de noeuds visités
                        List<Action> subplan = plan2(next,plan,closed); //On génère récursivement un sous-plan à partir du successeur
                        if (subplan != null){
                            return subplan;  //Si le sous-plan n'est pas nul on le renvoie
                        } else {
                            plan.remove(plan.size() - 1); //Dans le cas contraire, on retire le dernier élément de plan, qui correspond au successeur
                        }
                    }
                }


            }
        }
        return null;
    }


    @Override
    public String toString () {
        return "DFSPlanner [\n\tInitial state : " + this.initialState + "\n\tActions : " + this.actions + "\n\tGoal : " + this.goal + "\n]";
    }

}