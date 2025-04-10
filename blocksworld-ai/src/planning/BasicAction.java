package planning;

import java.util.*;
import modelling.*;

public class BasicAction implements Action{

    protected Map<Variable,Object> precondition; //Les valeurs des variables avant l'application de notre action
    protected Map<Variable,Object> effect; //Les valeurs des variables après l'application de notre action
    protected int cost; //Le coût de notre action

    public BasicAction(Map<Variable,Object> precondition, Map<Variable,Object> effect, int cost){
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }

    @Override
    public Map<Variable,Object> getEffect () {
        return this.effect;
    }

    @Override
    public Map<Variable,Object> getPrecondition () {
        return this.effect;
    }

    @Override
    public boolean isApplicable(Map<Variable,Object> state){
        //On vérifie si une action est applicable sur un état
        for (Variable variable : this.precondition.keySet()){
            if(!state.keySet().contains(variable)){
                return false;  //Si une variable n'est pas dans notre état alors l'action n'est pas applicable
            }
            if(!this.precondition.get(variable).equals(state.get(variable))){
                return false; //Idem si la variable existe mais ne possède pas la bonne valeur
            }
        }
        return true;
    }

    @Override
    public boolean isApplicable(Map<Variable,Object> state, Set<Constraint> constraints) {
        return this.isApplicable(state) && this.isValid(state, constraints);
    }

    public boolean isValid (Map<Variable,Object> state, Set<Constraint> constraints) {
        Map<Variable,Object> next = new HashMap<>(state);
        next.putAll(this.effect);
        for (Constraint constraint : constraints) {
            if (!constraint.isSatisfiedBy(next)) {
                return false;
            }
        }
        return true;
    }

    @Override
    //Renvoie le successeur d'un état après l'application de l'état
    public Map<Variable,Object> successor(Map<Variable,Object> state){
        Map<Variable, Object> res = new HashMap<>(this.effect); //On renvoie les modification des variables
        if ( isApplicable(state)){  //On vérifie d'abord que l'action est applicable
            for (Variable variable : state.keySet()){
                if (!res.keySet().contains(variable)){
                    res.put(variable,state.get(variable)); //On rajoute les valeurs de l'état qui n'étaient pas dans effect
                } 
            }
        }
        return res;
        
    }

    @Override
    public int getCost(){
        return this.cost;
    }

    @Override
    public boolean equals (Object other) {
        if (other instanceof BasicAction) {
            BasicAction otherAsAction = (BasicAction) other;
            return this.precondition.equals(otherAsAction.getPrecondition()) && this.effect.equals(otherAsAction.getEffect()) && this.cost == otherAsAction.getCost();
        }
        return false;
    }

    @Override
    public int hashCode () {
        return Objects.hash(this.precondition, this.effect, this.cost);
    }

    @Override
    public String toString () {
        return " Action {\n\tPreconditions : " + this.precondition + "\n\tEffect : " + this.effect + "\n\tCost : " + this.cost + "\n}";
    }

    

    

}