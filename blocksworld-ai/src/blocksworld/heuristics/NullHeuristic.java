package blocksworld.heuristics;

import java.util.*;
import modelling.*;
import planning.*;


public class NullHeuristic implements Heuristic {

    @Override
    public float estimate (Map<Variable,Object> state) {
        return 0;
    }

}