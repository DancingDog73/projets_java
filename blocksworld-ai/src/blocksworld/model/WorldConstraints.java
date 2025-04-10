package blocksworld.model;

import java.util.*;
import modelling.*;


public class WorldConstraints extends BlocksWorldStructure {


    public WorldConstraints (int blocksCount, int stacksCount) {
        super(blocksCount, stacksCount);
    }

    //Il faut une contrainte de différence entre le domaine de deux blocs différents. En effet, deux blocs ne peuvent pas être posés sur le même troisième bloc    
    public Constraint onBlockConstraint (int firstBlock, int secondBlock) {
        return new DifferenceConstraint(this.onBlockVariable(firstBlock), this.onBlockVariable(secondBlock));
    }

    
    public Constraint fixedBlockConstraint (int blockOnTop, int blockUnder) {
        return new Implication(
            this.onBlockVariable(blockOnTop),
            new HashSet<>(Arrays.asList(blockUnder)),
            this.blockFixedVariable(blockUnder),
            new HashSet<>(Arrays.asList(true))
        ); //Si un bloc b2 est sous un autre bloc b2 alors b2 est fixé
    }

    public Constraint freeStackConstraint (int block, int stack) {
        return new Implication(
            this.onBlockVariable(block),
            new HashSet<>(Arrays.asList(-stack)),
            this.stackFreeVariable(stack),
            new HashSet<>(Arrays.asList(false))
        ); //Si un bloc est posé sur une pile alors cette pile n'est pas libre.
    }

    
    //Génère toutes les contraintes de base 
    public Set<Constraint> getConstraints () {
        Set<Constraint> constraints = new HashSet<>();
        for (int firstBlock = 0; firstBlock < this.blocksCount; firstBlock += 1) {
            for (int secondBlock = 0; secondBlock < this.blocksCount; secondBlock += 1) {
                //Pour chaque paire de bloc on crée une contrainte de différence entre leurs support et des implications pour fixer un bloc au cas ou le deuxième est posé dessus
                if (firstBlock != secondBlock) {
                    constraints.add(this.onBlockConstraint(firstBlock, secondBlock));
                    constraints.add(this.fixedBlockConstraint(firstBlock, secondBlock));
                    constraints.add(this.fixedBlockConstraint(secondBlock, firstBlock));
                }
            }
            //Pour chaque pile et chaque bloc on crée une implication :  Si un bloc est posé sur une pile alors celle-ci n'est pas libre.
            for (int stack = 1; stack <= this.stacksCount; stack += 1) {
                constraints.add(this.freeStackConstraint(firstBlock, stack));
            }
        }
        return constraints;
    }


    @Override
    public String toString () {
        String representation = "BLOCKS WORLD CONSTRAINTS {blocksCount: " + this.blocksCount + ", stacksCount: " + this.stacksCount + "}\n";
        for (Constraint constraint : this.getConstraints()) {
            representation += "\t" + constraint + "\n";
        }
        return representation.trim();
    }

}