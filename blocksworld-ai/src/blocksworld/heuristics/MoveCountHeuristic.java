package blocksworld.heuristics;

import java.util.*;

import blocksworld.model.*;
import modelling.*;
import planning.*;


public class MoveCountHeuristic implements Heuristic{

    Map<Variable, Object> goal;
    WorldVariables variableBuilder;
    int blocksCount;
    int stacksCount;

    public MoveCountHeuristic(Map<Variable, Object> goal, int blocksCount, int stacksCount){
        this.goal = goal;
        this.blocksCount = blocksCount;
        this.stacksCount = stacksCount;
        this.variableBuilder = new WorldVariables(blocksCount, stacksCount);
    }

    @Override
    public float estimate(Map<Variable, Object> initialState){
        float distance = 0;
        for(int block = 0; block < this.blocksCount; block++){
            Variable onBlock = this.variableBuilder.onBlockVariable(block);
            Variable freeBlock = this.variableBuilder.blockFixedVariable(block);
            int destinationNumber = (int) this.goal.get(onBlock);
            Variable support = null;
            if(destinationNumber >= 0){
                support = this.variableBuilder.blockFixedVariable(destinationNumber);
            } else {
                support = this.variableBuilder.stackFreeVariable(Math.abs(destinationNumber));
            }
             //Pour chaque bloc si le bloc n'est pas sur le même support que dans l'état final, on ajoute 1 à l'estimation de la distance
            if(initialState.get(onBlock) != this.goal.get(onBlock)){
                distance++;
                if(!(boolean) initialState.get(freeBlock)){
                    //Si le bloc n'est pas déplaçable, on ajoute 1
                    distance++;
                }
                if(!(boolean) initialState.get(support)){
                    //Si la destination est occupée, on ajoute aussi 1.
                    distance++;
                }
            }

        }
        
        
        return distance;
    }


}