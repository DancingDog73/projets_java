package cp;

import java.util.*;
import modelling.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic{

    private Set<Constraint> constraints;
    private boolean preference;

    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean preference){
        this.constraints = constraints;
        this.preference = preference;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains){
        Variable bestVariable = null;
        int bestOccurences = this.preference ? -1 : Integer.MAX_VALUE;

        for(Variable variable: variables){
            int count = 0;
            // On compte le nombre de contraintes dans lesquelles cette variable apparaît
            for(Constraint constraint: this.constraints){
                if(constraint.getScope().contains(variable)){
                    count++;
                }
            }

            /*
             * On parcourt toutes les variables pour trouver la meilleure selon la taille de leur domaine
             * Si la préférence est true, on cherche la variable impliquée dans le plus de contraintes
             * Sinon, on cherche la impliquée dans le moins de contraintes
             */

            if(this.preference){
                if(count > bestOccurences){
                    bestOccurences = count;
                    bestVariable = variable;
                }
            } else {
                if(count < bestOccurences){
                    bestOccurences = count;
                    bestVariable = variable;
                }
            }

        }
        return bestVariable;
    }


    @Override
    public String toString () {
        return "Heuristic based on constraints on variables, prioritizes the ones with the " + (this.preference ? "most" : "least") + " constraints";
    }


}