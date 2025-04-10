package blocksworld.model;

import java.util.*;
import modelling.*;


public class WorldRegularityConstraints extends BlocksWorldStructure {


    public WorldRegularityConstraints (int blocksCount, int stacksCount) {
        super(blocksCount, stacksCount);
    }

    //Génère un ensemble de contraintes de régularité pour le monde des blocs.
    public Set<Constraint> getRegularityConstraints () {
        Set<Object> stacks = new HashSet<>();
        // Ajouter les piles comme bases possibles (indices négatifs pour les piles)
        for (int stack = 1; stack <= this.stacksCount; stack += 1) {
            stacks.add(-stack);
        }

        
        Set<Constraint> constraints = new HashSet<>();
        // Générer des contraintes entre tous les paires de blocs (premier et second)
        for (int firstBlock = 0; firstBlock < this.blocksCount; firstBlock += 1) {
            for (int secondBlock = 0; secondBlock < this.blocksCount; secondBlock += 1) {
                if (firstBlock != secondBlock) {
                    Set<Object> allowedBases = new HashSet<>(stacks);
                    int targetedBase = secondBlock+secondBlock-firstBlock;
                    if (0 <= targetedBase && targetedBase < this.blocksCount) {
                        allowedBases.add(targetedBase);
                    }
                    constraints.add(new Implication(
                        this.onBlockVariable(firstBlock), new HashSet<>(Arrays.asList(secondBlock)),
                        this.onBlockVariable(secondBlock), allowedBases
                    ));
                }
            }
        }
        return constraints;
    }


    @Override
    public String toString () {
        String representation = "BLOCKS WORLD REGULARITY CONSTRAINTS {blocksCount: " + this.blocksCount + ", stacksCount: " + this.stacksCount + "}\n";
        for (Constraint constraint : this.getRegularityConstraints()) {
            representation += "\t" + constraint + "\n";
        }
        return representation.trim();
    }


}