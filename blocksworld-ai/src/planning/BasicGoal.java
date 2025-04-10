package planning; 

import java.util.*;
import modelling.*;


public class BasicGoal implements Goal {
    private Map<Variable, Object> goal; // Notre but est composé d'une variable et de sa valeur lorsque le but est atteint
    
    public BasicGoal(Map<Variable, Object> goal){
        this.goal = goal;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> state){
        for(Variable key : this.goal.keySet()){ //On parcourt les variable de l'objectif
            if (!state.keySet().contains(key)){
                return false; //Si une variable du but n'est pas contenue dans l'état en paramètre alors il ne satisfait pas l'objectif
            } else if (state.get(key) != this.goal.get(key)){
                return false; //On renvoie aussi faux si une variable est initialisée à la mauvaise valeur
            }
        }
        return true;
    }

}