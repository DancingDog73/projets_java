package modelling;

import java.util.*;


public class Implication implements Constraint {

    private Variable firstVariable;
    private Variable secondVariable;
    private Set<Object> firstSet;
    private Set<Object> secondSet;

    public Implication (Variable firstVariable, Set<Object> firstSet, Variable secondVariable, Set<Object> secondSet) {
        this.firstVariable = firstVariable;
        this.secondVariable = secondVariable;
        this.firstSet = firstSet;
        this.secondSet = secondSet;
    }


    @Override
    public Set<Variable> getScope () {
        return new HashSet<>(Arrays.asList(this.firstVariable, this.secondVariable));
    }

    @Override
    public boolean isSatisfiedBy (Map<Variable, Object> instantiation) throws IllegalArgumentException {

        for (Variable variable : this.getScope()) {
            if (!instantiation.containsKey(variable)) {
                throw new IllegalArgumentException("Variable " + variable.getName() + " in constraint's scope but not in instantiation");
            }
        }
        
        Object value1 = instantiation.get(this.firstVariable);
        Object value2 = instantiation.get(this.secondVariable);
        if (this.firstSet.contains(value1)) {
            return this.secondSet.contains(value2);
        }
        return true;
    }


    @Override
    public String toString () {
        return "if " + this.firstVariable.getName() + "'s value in " + this.firstSet + ", " + this.secondVariable.getName() + "'s value must be in " + this.secondSet;
    }

}