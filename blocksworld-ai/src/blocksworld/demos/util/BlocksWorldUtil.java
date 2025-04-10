package blocksworld.demos.util;

import java.util.*;
import modelling.*;
import blocksworld.model.*;


public class BlocksWorldUtil {

    // Constante pour créer des variables pour un monde de 3 blocs et 3 piles
    public static final WorldVariables variableBuilder = new WorldVariables(3, 3);


    //Génère l'état initial pour le problème des tours de Hanoï 
    public static Map<Variable,Object> getHanoiInitialState (int blocksCount, int stacksCount) {
        WorldVariables variableBuilder = new WorldVariables(blocksCount, stacksCount);
        Map<Variable,Object> initialState = new HashMap<>();
        for (int block = 0; block < blocksCount-1; block += 1) {
            //Pour tous les blocs sauf le dernier, on le place sur le bloc supérieur et on marque le bloc supérieur comme fixe
            initialState.put(variableBuilder.onBlockVariable(block), block+1);
            initialState.put(variableBuilder.blockFixedVariable(block+1), true);
        }
        // Le dernier bloc de la pile 1 n'est pas fixe
        initialState.put(variableBuilder.blockFixedVariable(0), false);
        // Placer le dernier bloc directement sur la pile 1 (pile représentée par -1)
        initialState.put(variableBuilder.onBlockVariable(blocksCount-1), -1);
        // Marquer la pile 1 comme occupée
        initialState.put(variableBuilder.stackFreeVariable(1), false);
        
        // Les autres piles sont libres
        for (int stack = 2; stack <= stacksCount; stack += 1) {
            initialState.put(variableBuilder.stackFreeVariable(stack), true);
        }
        return initialState;
    }


     //Génère l'état final pour le problème des tours de Hanoï
     //On suit le même principe que la méthode précédente sauf que les blocs sont placés sur la dernière pile
    public static Map<Variable,Object> getHanoiFinalState (int blocksCount, int stacksCount) {
        WorldVariables variableBuilder = new WorldVariables(blocksCount, stacksCount);
        Map<Variable,Object> finalState = new HashMap<>();
        for (int block = 0; block < blocksCount-1; block += 1) {
            finalState.put(variableBuilder.onBlockVariable(block), block+1);
            finalState.put(variableBuilder.blockFixedVariable(block+1), true);
        }
        finalState.put(variableBuilder.blockFixedVariable(0), false);
        finalState.put(variableBuilder.onBlockVariable(blocksCount-1), -stacksCount);
        finalState.put(variableBuilder.stackFreeVariable(stacksCount), false);
        for (int stack = 1; stack <= stacksCount-1; stack += 1) {
            finalState.put(variableBuilder.stackFreeVariable(stack), true);
        }
        return finalState;
    }

    //Affiche l'ensemble des objets avec une description
    public static <E> void printObjects (Set<E> objects, String description) {
        System.out.println(description);
        for (Object object : objects) {
            System.out.println("\t" + object);
        }
        if (objects.isEmpty()) {
            System.out.println("\tAucun(e)");
        }
    }


    //Affiche un état du monde des blocs
    public static void printState (Map<Variable,Object> state, String description) {
        System.out.println(description);
        for (Variable variable : state.keySet()) {
            System.out.println("\t" + variable + " = " + state.get(variable));
        }
        if (state.keySet().isEmpty()) {
            System.out.println("\tAucun(e)");
        }
    }

    //Vérifie si une chaîne est un entier valide.
    public static boolean isInt (String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Retourne les contraintes de base pour le monde des blocs
    public static Set<Constraint> getBasicConstraints (int blocksCount, int stacksCount) {
        return new WorldConstraints(blocksCount, stacksCount).getConstraints();
    }

    //Retourne les contraintes de croissance croissante pour le monde des blocs.
    public static Set<Constraint> getGrowthIConstraints (int blocksCount, int stacksCount) {
        Set<Constraint> constraints = new HashSet<>(new WorldConstraints(blocksCount, stacksCount).getConstraints());
        constraints.addAll(new WorldGrowthConstraints(blocksCount, stacksCount).getGrowthConstraints());
        return constraints;
    }

    //Retourne les contraintes de croissance décroissante pour le monde des blocs.
    public static Set<Constraint> getGrowthDConstraints (int blocksCount, int stacksCount) {
        Set<Constraint> constraints = new HashSet<>(new WorldConstraints(blocksCount, stacksCount).getConstraints());
        constraints.addAll(new WorldGrowthConstraints(blocksCount, stacksCount).getGrowthConstraints(false));
        return constraints;
    }

    //Retourne les contraintes de régularité pour le monde des blocs.
    public static Set<Constraint> getRegularityConstraints (int blocksCount, int stacksCount) {
        Set<Constraint> constraints = new HashSet<>(new WorldConstraints(blocksCount, stacksCount).getConstraints());
        constraints.addAll(new WorldRegularityConstraints(blocksCount, stacksCount).getRegularityConstraints());
        return constraints;
    }

    //Retourne toutes les contraintes pour le monde des blocs.
    public static Set<Constraint> getAllConstraints (int blocksCount, int stacksCount) {
        Set<Constraint> constraints = new HashSet<>(getGrowthIConstraints(blocksCount, stacksCount));
        constraints.addAll(getRegularityConstraints(blocksCount, stacksCount));
        return constraints;
    }

    //Vérifie si toutes les contraintes sont satisfaites dans un état donné.
    public static boolean allConstraintsAreSatisfied (Map<Variable,Object> instantiation, Set<Constraint> constraints) {
        for (Constraint constraint : constraints) {
            if (instantiation.keySet().containsAll(constraint.getScope()) && !constraint.isSatisfiedBy(instantiation)) {
                return false;
            }
        }
        return true;
    }

    //Génère une configuration aléatoire valide du monde des blocs.
    public static Map<Variable,Object> generateRandomConfiguration (int blocksCount, int stacksCount) {
        Map<Variable,Object> instantiation = new HashMap<>();
        Random generator = new Random();
        WorldVariables variableBuilder = new WorldVariables(blocksCount, stacksCount);
        List<Integer> bases = new ArrayList<>();
        // Préparer toutes les bases possibles (-piles pour piles et blocs)
        for (int base = -stacksCount; base < blocksCount; base += 1) {
            bases.add(base);
        }
        Set<Integer> setBases = new HashSet<>();
        for (int block = 0; block < blocksCount; block += 1) {
            // Sélection aléatoire d'une base valide pour chaque bloc
            int base = bases.get(generator.nextInt(bases.size()));
            while (base == block || setBases.contains(base)) {
                base = bases.get(generator.nextInt(bases.size()));
            }
            instantiation.put(variableBuilder.onBlockVariable(block), base);
            bases.remove((Integer)base);
            setBases.add(base);
            if (base < 0) {
                instantiation.put(variableBuilder.stackFreeVariable(-base), false);
            } else {
                instantiation.put(variableBuilder.blockFixedVariable(base), true);
            }
            if (!bases.contains(block)) {
                bases.add(block);
            }
        }
        for (int base : bases) {
            if (!setBases.contains(base)) {
                if (base < 0) {
                    instantiation.put(variableBuilder.stackFreeVariable(-base), true);
                } else {
                    instantiation.put(variableBuilder.blockFixedVariable(base), false);
                }
            }
        }
        return instantiation;
    }

}