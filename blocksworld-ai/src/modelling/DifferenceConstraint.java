package modelling;

import java.util.*;


public class DifferenceConstraint implements Constraint {

    private Variable first;
    private Variable second;

    public DifferenceConstraint (Variable first, Variable second) {
        this.first = first;
        this.second = second;
    }


    @Override
    public Set<Variable> getScope () {
        return new HashSet<>(Arrays.asList(this.first, this.second));
    }

    @Override
    public boolean isSatisfiedBy (Map<Variable, Object> instantiation) throws IllegalArgumentException {

        for (Variable variable : this.getScope()) {
            if (!instantiation.containsKey(variable)) {
                throw new IllegalArgumentException("Variable " + variable.getName() + " in constraint's scope but not in instantiation");
            }
        }
        
        return !instantiation.get(this.first).equals(instantiation.get(this.second));
    }


    @Override
    public String toString () {
        return this.first.getName() + " and " + this.second.getName() + " must not have the same value";
    }

}