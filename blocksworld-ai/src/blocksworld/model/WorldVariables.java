package blocksworld.model;

import java.util.*;
import modelling.*;


public class WorldVariables extends BlocksWorldStructure {


    public WorldVariables (int blocksCount, int stacksCount) {
        super(blocksCount, stacksCount);
    }

    //Génère TOUTES les variables pour une configuration du monde des blocs
    public Set<Variable> getVariables () {
        Set<Variable> variables = new HashSet<>();
        //Pour chaque bloc on ajoute deux variable, une qui indique son support et l'autre si elle est fixée ou pas
        for (int block = 0; block < this.blocksCount; block += 1) {
            variables.add(this.onBlockVariable(block));
            variables.add(this.blockFixedVariable(block));
        }
        //Pour chaque pile on crée la variable qui indique si elle est libre ou pas 
        for (int stack = 1; stack <= this.stacksCount; stack += 1) {
            variables.add(this.stackFreeVariable(stack));
        }
        return variables;
    }


    @Override
    public String toString () {
        String representation = "BLOCKS WORLD VARIABLES {blocksCount: " + this.blocksCount + ", stacksCount: " + this.stacksCount + "}\n";
        for (Variable variable : this.getVariables()) {
            representation += "\t" + variable + "\n";
        }
        return representation.trim();
    }

}