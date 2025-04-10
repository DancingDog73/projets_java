package planning;

import modelling.*;
import java.util.*;



public interface Planner {

    public List<Action> plan();
    public Map<Variable, Object> getInitialState();
    public Set<Action> getActions();
    public Set<Constraint> getConstraints();
    public Goal getGoal();

    public void activateNodeCount(boolean activate);
    public int getExploredNodes();

}