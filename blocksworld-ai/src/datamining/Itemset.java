package datamining;

import modelling.*;
import java.util.*;

public class Itemset {

    private Set<BooleanVariable> items;
    private float frequency;

    public Itemset( Set<BooleanVariable> items, float frequency){
        if(frequency < 0.0 || frequency > 1.0){
            throw new IllegalArgumentException("La fréquence dot être comprise entre 0 et 1");
        }
        this.items = items;
        this.frequency = frequency;
    }


    public Set<BooleanVariable> getItems(){
        return this.items;
    }

    public float getFrequency(){
        return this.frequency;
    }

    @Override
    public String toString () {
        return "Itemset {set: " + this.items + ", frequency: " + this.frequency + "}";
    }



}