package datamining;


import modelling.*;
import java.util.*;


public class Apriori extends AbstractItemsetMiner{


    public Apriori(BooleanDatabase database){
        super(database);
    }

    // Méthode pour obtenir les itemsets fréquents de taille 1 (singleton)
    public Set<Itemset> frequentSingletons(float minimalFrequency){
        Set<Itemset> frequents = new HashSet<>();

        for(BooleanVariable item : this.database.getItems()){
            Set<BooleanVariable> itemset = new HashSet<>();
            itemset.add(item);
            float frequency = super.frequency(itemset);
            //On parcourt la base encalculant la fréquence de chaque singleton et si cette fréquence est suffisante, on l'ajoute à la base
            if (frequency >= minimalFrequency){
                Itemset singleton = new Itemset(itemset , frequency);
                frequents.add(singleton);
            }
        }
        return frequents;
    }


    //Méthode pour combiner deux itemsets de taille 1 (ou plus) en un itemset de taille supérieure
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> itemset1, SortedSet<BooleanVariable> itemset2){
        // Si les itemsets n'ont pas la même taille ou sont vides, on retourne null
        if ((itemset1.size() != itemset2.size()) || (itemset1.size() == 0)){
            return null;
        }
        

        SortedSet<BooleanVariable> combinedSet = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        var iterateur1 = itemset1.iterator();
        var iterateur2 = itemset2.iterator();

        for (int i=0; i<itemset1.size()-1; i++){
            BooleanVariable item1 = iterateur1.next();
            BooleanVariable item2 = iterateur2.next();
            //On itère sur les éléments pour vérifier qu'ils peuvent être combinés
            if (!item1.equals(item2)){
                return null; //Si les éléments sont inégaux, on renvoie null car la combinaison a échoué
            }
            combinedSet.add(item1);
        }

        // On ajoute le dernier élément des deux itemsets si ils sont différents
        BooleanVariable lastItem1 = iterateur1.next();
        BooleanVariable lastItem2 = iterateur2.next();
        if (lastItem1.equals(lastItem2)){
            return null;
        }
        combinedSet.add(lastItem1);
        combinedSet.add(lastItem2);
        
        return combinedSet;
    }

    // Vérifie si tous les sous-ensembles d'un itemset donné sont fréquents
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> itemsetCollection){
        for(BooleanVariable item: items){
            SortedSet<BooleanVariable> subcopy = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            subcopy.addAll(items);
            subcopy.remove(item); //On supprime un élément à la fois pour vérifier tous les sous-ensembles
            if(!itemsetCollection.contains(subcopy)){
                return false;
            }
        }
        return true;
    }

    // Méthode principale pour extraire les itemsets fréquents en utilisant l'algorithme Apriori
    @Override
    public Set<Itemset> extract(float minimalFrequency){
        Set<Itemset> frequentItemsets = frequentSingletons(minimalFrequency);
        List<SortedSet<BooleanVariable>> itemsetBySteps = new ArrayList<>();
        //On crée une liste d'itemsets de taille 1 pour démarrer l'algorithme Apriori
        for (Itemset item: frequentItemsets){
            var iterateur = item.getItems().iterator();
            SortedSet<BooleanVariable> sortedSet = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            sortedSet.add(iterateur.next());
            itemsetBySteps.add(sortedSet);
        }
        
        // L'algorithme continue tant qu'il existe des itemsets fréquents de taille plus grande
        while(!itemsetBySteps.isEmpty()){
            List<SortedSet<BooleanVariable>> nextItemset = new ArrayList<>();
            for(int i = 0; i < itemsetBySteps.size(); i++){
                for(int j= i+1; j < itemsetBySteps.size(); j++){
                    // Combine deux itemsets et vérifie si la combinaison est valide et fréquente
                    SortedSet<BooleanVariable> combination = combine(itemsetBySteps.get(i), itemsetBySteps.get(j));
                    if(combination != null && allSubsetsFrequent(combination, itemsetBySteps)){
                        float frequency = super.frequency(combination);
                        if(frequency >= minimalFrequency){
                            nextItemset.add(combination);
                            frequentItemsets.add(new Itemset(combination, frequency));
                        }
                        
                    }

                }
            }
            itemsetBySteps = nextItemset;
        }
        return frequentItemsets;
    
    }


    @Override
    public String toString () {
        return "Apriori itemset miner on following database:\n" + this.database;
    }
}