package datamining;


import modelling.*;
import java.util.*;

public class Demo {
    
    public static void main(String[] args) {

        System.out.println();

        // Création de variables booléennes
        BooleanVariable itemA = new BooleanVariable("A");
        BooleanVariable itemB = new BooleanVariable("B");
        BooleanVariable itemC = new BooleanVariable("C");
        BooleanVariable itemD = new BooleanVariable("D");

        // Création de la base de données booléenne
        BooleanDatabase database = new BooleanDatabase(new HashSet<>(Arrays.asList(itemA, itemB, itemC, itemD)));


        // Création de transactions pour la base de données
        database.add(new HashSet<>(Arrays.asList(itemA, itemB)));
        database.add(new HashSet<>(Arrays.asList(itemA, itemC)));
        database.add(new HashSet<>(Arrays.asList(itemA, itemB, itemC)));
        database.add(new HashSet<>(Arrays.asList(itemB, itemC)));
        database.add(new HashSet<>(Arrays.asList(itemA, itemB, itemD)));
        database.add(new HashSet<>(Arrays.asList(itemB, itemD)));
        database.add(new HashSet<>(Arrays.asList(itemA, itemC, itemD)));
        database.add(new HashSet<>(Arrays.asList(itemA, itemB, itemC, itemD)));

        System.out.println(database + "\n");

        
        // Exécution de l'algorithme Apriori pour trouver les itemsets fréquents
        float minimalFrequency = 0.3f; // seuil de fréquence minimale
        Apriori apriori = new Apriori(database);
        Set<Itemset> frequentItemsets = apriori.extract(minimalFrequency);

        System.out.println("Itemsets fréquents (fréquence >= " + minimalFrequency + "):");
        for (Itemset itemset : frequentItemsets) {
            System.out.println("Itemset: " + itemset.getItems() + " - Fréquence: " + itemset.getFrequency());
        }

        // Exécution du BruteForceAssociationRuleMiner pour extraire les règles d'association
        float minimalConfidence = 0.6f; // seuil de confiance minimale
        BruteForceAssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(database);
        Set<AssociationRule> associationRules = ruleMiner.extract(minimalFrequency, minimalConfidence);

        System.out.println("\nRègles d'association (fréquence >= " + minimalFrequency + ", confiance >= " + minimalConfidence + "):");
        for (AssociationRule rule : associationRules) {
            System.out.println("Règle: " + rule.getPremise() + " => " + rule.getConclusion() + 
                               " - Fréquence: " + rule.getFrequency() + 
                               " - Confiance: " + rule.getConfidence());
        }

        System.out.println();

    }

}
