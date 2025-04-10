package planning;

import modelling.*;
import java.util.*;


public interface Heuristic {

     
    public float estimate(Map<Variable,Object> initialState);

}