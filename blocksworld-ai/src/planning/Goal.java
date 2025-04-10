package planning; 

import java.util.*;
import modelling.*;


public interface Goal {

    public boolean isSatisfiedBy(Map<Variable,Object> state);

}