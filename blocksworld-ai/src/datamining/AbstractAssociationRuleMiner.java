package datamining;

import java.util.*;
import modelling.*;


public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner{
    protected BooleanDatabase database;

    public AbstractAssociationRuleMiner(BooleanDatabase database){
        this.database = database;
    }

    @Override
    public BooleanDatabase getDatabase(){
        return this.database;
    }

    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets){
        for(Itemset itemset: itemsets){
            if(items.equals(itemset.getItems())){
                // On parcourt les itemsets pour trouver celui qui correspond à l'ensemble d'items et on renvoie sa fréquence
                return itemset.getFrequency();
            }
        }
        return 0.0f;
    }

    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets){
        // On crée un ensemble contenant à la fois la prémisse et la conclusion puis on renvoie la division entre la fréquence de cet ensemble et la fréquence de la prémisse
        Set<BooleanVariable> all = new HashSet<>(premise);
        all.addAll(conclusion);
        return frequency(all, itemsets)/frequency(premise, itemsets);

    }
}