package cp;

import modelling.*;
import java.util.*;

public abstract class AbstractSolver implements Solver{

    protected Set<Variable> variables;  
    protected Set<Constraint> constraints;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints){
        this.variables = variables;
        this.constraints = constraints;
    }
    
    //Cette méthode vérifie si une affectation partielle satisfait toutes les contraintes qui porte sur ses variables
    public boolean isConsistent(Map<Variable, Object> partial){
        for(Constraint constraint: this.constraints){
            if (partial.keySet().containsAll(constraint.getScope())){
                //Si une notre affectation contient toutes les variables d'une contrainte alors on vérifie si la contrainte est satisfaite par notre instnciation
                if (!constraint.isSatisfiedBy(partial)){
                    //Si la contrainte est satisfaite, on continue sinon on retourne false
                    return false;
                }
            }
        }
        return true; //On retourne true après avoir vérifié toutes les contraintes
    }

    public Set<Variable> getVariables(){
        return this.variables;
    }


    public Set<Constraint> getConstraints(){
        return this.constraints;
    }


    @Override
    public String toString () {
        String representation = "SOLVER\n------------------------------\nVariables\n\t" + this.variables + "\n------------------------------\nConstraints\n";
        for (Constraint constraint : this.constraints) {
            representation += "\n" + constraint + "\n";
        }
        representation += "------------------------------";
        return representation;
    }

}