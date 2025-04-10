package cp;

import java.util.*;
import modelling.*;

public class DomainSizeVariableHeuristic implements VariableHeuristic{

    private boolean preference;

    public DomainSizeVariableHeuristic(boolean preference){
        this.preference = preference;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains){
        Variable bestVariable = null;
        int bestDomainSize = this.preference ? -1 : Integer.MAX_VALUE;

        for(Variable variable: variables){
            int domainSize = domains.get(variable).size();
            /*
             * On parcourt toutes les variables pour trouver la meilleure selon la taille de leur domaine
             * Si la préférence est true, on cherche la variable avec le plus grand domaine
             * Sinon, on cherche la variable avec le plus petit domaine
             */
    

            if(this.preference){
                if(domainSize > bestDomainSize){
                    bestDomainSize = domainSize;
                    bestVariable = variable;
                }
            } else {
                if(domainSize < bestDomainSize){
                    bestDomainSize = domainSize;
                    bestVariable = variable;
                }
            }

        }
        return bestVariable;
    }


    @Override
    public String toString () {
        return "Heuristic based on variables domains size, prioritizes the ones with " + (this.preference ? "bigger" : "smaller") + " domains";
    }

}