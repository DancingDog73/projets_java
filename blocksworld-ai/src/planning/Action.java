package planning;

import modelling.*;
import java.util.*;

public interface Action{
    public boolean isApplicable(Map<Variable,Object> state);
    public boolean isApplicable(Map<Variable,Object> state, Set<Constraint> constraints);
    public Map<Variable,Object> successor(Map<Variable,Object> state);
    public Map<Variable,Object> getEffect();
    public Map<Variable,Object> getPrecondition();
    public int getCost();

}