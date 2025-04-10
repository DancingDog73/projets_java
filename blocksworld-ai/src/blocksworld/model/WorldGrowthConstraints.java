package blocksworld.model;

import java.util.*;
import modelling.*;


public class WorldGrowthConstraints extends BlocksWorldStructure {

    public WorldGrowthConstraints (int blocksCount, int stacksCount) {
        super(blocksCount, stacksCount);
    }

    //génère une contrainte de croissance sur un bloc donné 
    public Constraint growthConstraint (int block, boolean ascending) {
        Set<Object> allowedBases = new HashSet<>(); //bases autorisées
        // Ajouter toutes les piles comme bases possibles (les piles sont représentées par des indices négatifs)
        for (int stack = 1; stack <= this.stacksCount; stack += 1) {
            allowedBases.add(-stack);
        }
        int smallerBlockAllowedAsBase = ascending ? 0 : block+1; // Bloc minimum autorisé
        int biggerBlockAllowedAsBase = ascending ? block-1 : this.blocksCount-1; // Bloc maximum autorisé
        // Ajouter tous les blocs dans la plage définie comme bases possibles
        for (int allowedBase = smallerBlockAllowedAsBase; allowedBase <= biggerBlockAllowedAsBase; allowedBase += 1) {
            allowedBases.add(allowedBase);
        }

        // Retourne une contrainte unitaire définissant les bases autorisées
        return new UnaryConstraint(this.onBlockVariable(block), allowedBases);
    }

    //Génère un ensemble de contraintes de croissance pour tous les blocs.
    //Selon a valeur de ascending, les contraintes sont de croissance(true) ou décroissance(false)
    public Set<Constraint> getGrowthConstraints (boolean ascending) {
        Set<Constraint> constraints = new HashSet<>();
        for (int block = 0; block < this.blocksCount; block += 1) {
            constraints.add(this.growthConstraint(block, ascending));
        }
        return constraints;
    }

    // Génère un ensemble de contraintes de croissance croissante par défaut.
    public Set<Constraint> getGrowthConstraints () {
        return this.getGrowthConstraints(true);
    }


    @Override
    public String toString () {
        String representation = "BLOCKS WORLD GROWTH CONSTRAINTS {blocksCount: " + this.blocksCount + ", stacksCount: " + this.stacksCount + "}\n";
        for (Constraint constraint : this.getGrowthConstraints()) {
            representation += "\t" + constraint + "\n";
        }
        return representation.trim();
    }

}