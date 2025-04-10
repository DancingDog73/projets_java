package cp;

import modelling.*;
import java.util.*;


public interface VariableHeuristic {
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains);
}