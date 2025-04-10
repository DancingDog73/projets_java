package planning;


import modelling.*;
import java.util.*;

public class MockHeuristic implements Heuristic{

    public MockHeuristic() {

    }

    @Override
    public float estimate(Map<Variable,Object> initialState){
        return 0;
    }

}