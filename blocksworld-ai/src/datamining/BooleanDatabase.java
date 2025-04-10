package datamining;

import modelling.*;
import java.util.*;



public class BooleanDatabase {

    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;

    public BooleanDatabase(Set<BooleanVariable> items){
        this.items = new HashSet<>(items);
        this.transactions = new ArrayList<>();

    }

    public Set<BooleanVariable> getItems(){
        return this.items;
    }


    public List<Set<BooleanVariable>> getTransactions(){
        return this.transactions;
    }

    public void add(Set<BooleanVariable> toBeAdded){
        this.transactions.add(toBeAdded);
    }

    
    @Override
    public String toString () {
        String representation = "-------------------------------\nBOOLEAN DATABASE\n-------------------------------\n";
        representation += "Items\n\t" + this.items;
        representation += "\n-------------------------------\nTransactions\n";
        for (Set<BooleanVariable> transaction : this.transactions) {
            representation += "\t" + transaction + "\n";
        }
        representation += "-------------------------------";
        return representation;
    }

}