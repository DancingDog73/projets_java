package planning;

import java.util.*;
import modelling.*;


public abstract class AbstractPlanner implements Planner {

    protected Map<Variable,Object> initialState;
    protected Set<Action> actions;
    protected Goal goal;
    protected Set<Constraint> constraints;

    protected boolean nodeCount;
    protected int exploredNodes;

    public AbstractPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal, Set<Constraint> constraints) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.constraints = constraints;
        this.nodeCount = false;
        this.exploredNodes = 0;
    }

    public AbstractPlanner (Map<Variable,Object> initialState, Set<Action> actions, Goal goal) {
        this(initialState, actions, goal, new HashSet<>());
    }

    @Override
    public Map<Variable, Object> getInitialState(){
        return this.initialState;
    }

    @Override
    public Set<Action> getActions(){
        return this.actions;
    }

    @Override
    public Goal getGoal(){
        return this.goal;
    }

    @Override
    public Set<Constraint> getConstraints () {
        return this.constraints;
    }


    public void activateNodeCount(boolean activate){
        this.nodeCount = activate; 
    }

    public int getExploredNodes(){
        return this.exploredNodes;
    }

    public void incrementNodeCount(){
        if(this.nodeCount){
            this.exploredNodes++;
        }        
    }
    
}