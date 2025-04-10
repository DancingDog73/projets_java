package cp;

import modelling.*;
import java.util.*;

public interface ValueHeuristic {
    public List<Object> ordering(Variable variable, Set<Object> domain);
}