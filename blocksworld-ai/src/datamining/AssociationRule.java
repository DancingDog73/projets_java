package datamining;

import java.util.*;
import modelling.*;


public  class AssociationRule{
    
    private Set<BooleanVariable> premise;
    private Set<BooleanVariable> conclusion;
    private float frequency;
    private float confidence;
   

    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency, float confidence){
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    public Set<BooleanVariable> getPremise(){
        return this.premise;
    }

    public Set<BooleanVariable> getConclusion(){
        return this.conclusion;
    }

    public float getConfidence(){
        return this.confidence;
    }

    public float getFrequency(){
        return this.frequency;
    }

    public String toString () {
        return this.premise + " -> " + this.conclusion + ", fr=" + this.frequency + " conf=" + this.confidence;
    }
}