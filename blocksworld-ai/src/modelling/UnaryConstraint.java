package modelling;

import java.util.*;


public class UnaryConstraint implements Constraint {

    private Variable variable;
    private Set<Object> set;

    public UnaryConstraint (Variable variable, Set<Object> set) {
        this.variable = variable;
        this.set = set;
    }


    @Override
    public Set<Variable> getScope () {
        return new HashSet<>(Arrays.asList(this.variable));
    }

    @Override
    public boolean isSatisfiedBy (Map<Variable, Object> instanciation) throws IllegalArgumentException {

        if (!instanciation.containsKey(this.variable)) {
            throw new IllegalArgumentException("Variable " + this.variable.getName() + " in constraint's scope but not in instantiation");
        }
        
        return this.set.contains(instanciation.get(variable));
    }


    @Override
    public String toString () {
        return this.variable.getName() + "'s value must be in " + this.set;
    }

}