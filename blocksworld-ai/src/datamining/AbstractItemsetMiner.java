package datamining;

import modelling.*;
import java.util.*;


public abstract class AbstractItemsetMiner implements ItemsetMiner {

    protected BooleanDatabase database;
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());

    public AbstractItemsetMiner(BooleanDatabase database){
        this.database = database;
    }

    @Override 
    public BooleanDatabase getDatabase(){
        return this.database;
    }

    // Méthode pour calculer la fréquence d'un itemset dans la base de données
    public float frequency(Set<BooleanVariable> itemset){
        List<Set<BooleanVariable>> transactions = this.database.getTransactions();

        int occurences = 0;
        for(Set<BooleanVariable> itemset2 : transactions){
            if(itemset2.containsAll(itemset)){
                occurences ++;
            }
        }

        // Calcule la fréquence de l'itemset dans la base de données (nombre d'occurrences divisé par le nombre total de transactions)
        float result = (float) occurences/transactions.size();
        return result;
    }




}