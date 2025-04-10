package datamining;

import modelling.*;
import java.util.*;


public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner{
    
  

    public BruteForceAssociationRuleMiner(BooleanDatabase database){
        super(database);
    }

    // Méthode pour générer toutes les combinaisons possibles entre un item et un ensemble d'itemsets
    public static Set<Set<BooleanVariable>> allCombinations (Set<BooleanVariable> items, Set<Set<BooleanVariable>> sets) {
        Set<Set<BooleanVariable>> combinations = new HashSet<>();
        // Parcours chaque item de l'ensemble et chaque ensemble dans sets pour créer de nouvelles combinaisons
        for (BooleanVariable item : items) {
            for (Set<BooleanVariable> set : sets) {
                Set<BooleanVariable> combination = new HashSet<>(set);
                combination.add(item);
                combinations.add(combination);
            }
        }
        return combinations;
    }

    // Méthode pour générer tous les candidats pour les prémisses d'une règle d'association (l'ensemble de gauche de la règle)
    public static Set<Set<BooleanVariable>> allCandidatePremises (Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> premises = new HashSet<>();
        Set<Set<BooleanVariable>> nCandidates = new HashSet<>();
        // Initialisation de l'ensemble des candidats avec des sets vides
        for (BooleanVariable item : items) {
            nCandidates.add(new HashSet<>());
        }
        // Génération des candidats pour les prémisses en combinant les éléments de taille croissante
        for (int n = 1; n < items.size(); n += 1) {
            nCandidates = allCombinations(items, nCandidates);
            premises.addAll(nCandidates);
        }
        return premises;
    }

    

    @Override
    public Set<AssociationRule> extract(float minimalFrequency, float minimalConfidence){
        
        Set<AssociationRule> associationRules = new HashSet<>();

        Apriori itemsetMiner = new Apriori(this.database);
        Set<Itemset> itemsets = itemsetMiner.extract(minimalFrequency);
        
        for(Itemset itemset : itemsets){
            Set<Set<BooleanVariable>> allCandidates = allCandidatePremises(itemset.getItems());
            for(Set<BooleanVariable> candidate: allCandidates){
                Set<BooleanVariable> premise = new HashSet<>(itemset.getItems());
                premise.removeAll(candidate);
                float ruleConfidence = super.confidence(premise, candidate, itemsets);
                float ruleFrequency = super.frequency(itemset.getItems(), itemsets);
                if (ruleConfidence >= minimalConfidence){
                    AssociationRule rule = new AssociationRule(premise, candidate, ruleFrequency, ruleConfidence);
                    associationRules.add(rule);
                }
            }
        }
        
        return associationRules;
    }


    @Override
    public String toString () {
        return "Brute force association rule miner on following database:\n" + this.database;
    }
}