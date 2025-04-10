package blocksworld.demos.cli;

import java.util.*;
import modelling.*;
import bwgenerator.BWGenerator;
import bwgeneratordemo.Demo;
import datamining.Apriori;
import datamining.AssociationRule;
import datamining.BooleanDatabase;
import datamining.BruteForceAssociationRuleMiner;
import datamining.Itemset;
import blocksworld.model.*;



public class DemoDatamining {

    public static void main(String[] argv){

        int blocksCount = 7;
        int stacksCount = 5;
        WorldDatabase d = new WorldDatabase(blocksCount, stacksCount);
        int n = 10000;
        BooleanDatabase db = new BooleanDatabase(d.getBooleanVariables());
        Random random = new Random();
        BWGenerator demo = new BWGenerator(blocksCount, stacksCount);
        for (int i = 0; i < n; i++) {
            // Drawing a state at random
            List<List<Integer>> state = demo.generate(random);
            
            // Converting state to instance
            Set<BooleanVariable> instance = d.getTransactions(state);
            
            // Adding state to database
            db.add(instance);
        }
        
        BruteForceAssociationRuleMiner bruteForceAssociationRuleMiner = new BruteForceAssociationRuleMiner(db);
        Apriori apriori = new Apriori(db);

        float minimalFrequency = 2/3f;
        float minimalConfidence = 0.95f;
        Set<Itemset> frequentItemsets = apriori.extract(minimalFrequency);
        Set<AssociationRule> associationRules = bruteForceAssociationRuleMiner.extract(minimalFrequency, minimalConfidence);
        
        System.out.println("Itemsets fréquents (fréquence >= " + minimalFrequency + "):");
        for (Itemset itemset : frequentItemsets) {
            System.out.println("Itemset: " + itemset.getItems() + " - Fréquence: " + itemset.getFrequency());
        }

        System.out.println("\nRègles d'association (fréquence >= " + minimalFrequency + ", confiance >= " + minimalConfidence + "):");
        for (AssociationRule rule : associationRules) {
            System.out.println("Règle: " + rule.getPremise() + " => " + rule.getConclusion() + 
                               " - Fréquence: " + rule.getFrequency() + 
                               " - Confiance: " + rule.getConfidence());
        }
    }

}