package blocksworld.heuristics;

import java.util.*;

import blocksworld.model.*;
import modelling.*;
import planning.*;

public class MisplacedBlocksCountHeuristic implements Heuristic{

    Map<Variable, Object> goal;
    WorldVariables variableBuilder;
    int blocksCount;
    int stacksCount;

    public MisplacedBlocksCountHeuristic(Map<Variable, Object> goal, int blocksCount, int stacksCount){
        this.goal = goal;
        this.blocksCount = blocksCount;
        this.stacksCount = stacksCount;
        this.variableBuilder = new WorldVariables(blocksCount, stacksCount);
    }


    @Override
    public float estimate(Map<Variable, Object> initialState){
        float res = 0;
        //Pour chaque bloc si le bloc n'est pas sur le même support que dans l'état final, on ajoute 1 à l'estimation de la distance
        for(int block = 0; block < this.blocksCount; block++){
            Variable onBlock = this.variableBuilder.onBlockVariable(block);
            if(initialState.get(onBlock) != this.goal.get(onBlock)){
                res += 1;
            }

        }
        return res;
    }


    
    
}