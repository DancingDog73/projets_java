package modelling;

import java.util.*;


/*

Nous allons dans notre démo modéliser un petit monde des blocs (2 blocks et 2 piles) et tester la satisfaction de contraintes avec plusieurs instanciations.
Les contraintes manipulées sont celles nécessaires à une configuration de monde des blocks valide, auxquelles nous avons ajouté celles d'une configuration
 décroissante (un bloc ne peut être positionné que sur un bloc de numéro plus grand)
Pour mieux lire les contraintes et instanciations, se reporter à l'affichage de l'exécution. Aussi certaines contraintes ont été commentées pour faciliter la
 lecture du résultat affichées (elles n'étaint pas forcément pertinentes vu nos configurations)

Nous avons donc défini un ensembles de variables (un tableau en réalité pour faciliter la réutilisation de ces variables), de contraintes et d'instanciations
 (des dictionnaires en réalité pour qu'elles aient des noms plus simples plutôt que des les afficher en entier tout le temps). Puis nous avons procédé au test
 de la satisfaction des contraintes pour chaque instanciation. Il suffira par ailleurs de modifier ces ensembles pour ajouter des tests ou en faire d'autres.

*/

public class Demo {

    
    public final static Variable[] VARIABLES = {
        new Variable("onB1", new HashSet<>(Arrays.asList(-2,-1,1))),
        new Variable("onB2", new HashSet<>(Arrays.asList(-2,-1,0))),
        new BooleanVariable("fixedB1"), new BooleanVariable("fixedB2"),
        new BooleanVariable("freeS1"), new BooleanVariable("freeS2")
    };

    public final static Map<String,Constraint> CONSTRAINTS = Map.of(
        "Constraint_1", new DifferenceConstraint(VARIABLES[0], VARIABLES[1]),
        "Constraint_2", new Implication(VARIABLES[0], new HashSet<>(Arrays.asList(1)), VARIABLES[3], new HashSet<>(Arrays.asList(true))),
        //"Constraint_3", new Implication(VARIABLES[1], new HashSet<>(Arrays.asList(0)), VARIABLES[2], new HashSet<>(Arrays.asList(true))),
        "Constraint_4", new Implication(VARIABLES[0], new HashSet<>(Arrays.asList(-1)), VARIABLES[4], new HashSet<>(Arrays.asList(false))),
        //"Constraint_5", new Implication(VARIABLES[0], new HashSet<>(Arrays.asList(-2)), VARIABLES[5], new HashSet<>(Arrays.asList(false))),
        "Constraint_6", new Implication(VARIABLES[1], new HashSet<>(Arrays.asList(-1)), VARIABLES[4], new HashSet<>(Arrays.asList(false))),
        //"Constraint_7", new Implication(VARIABLES[1], new HashSet<>(Arrays.asList(-2)), VARIABLES[5], new HashSet<>(Arrays.asList(false))),
        "Constraint_8", new UnaryConstraint(VARIABLES[0], new HashSet<>(Arrays.asList(1))),
        "Constraint_9", new UnaryConstraint(VARIABLES[1], new HashSet<>(Arrays.asList(-1,-2)))
    );

    public final static Map<String,Map<Variable,Object>> INSTANTIATIONS = Map.of(
        "Instantiation_1", Map.of(VARIABLES[0], -1, VARIABLES[1], -1, VARIABLES[2], false, VARIABLES[3], false, VARIABLES[4], true, VARIABLES[5], false),
        "Instantiation_2", Map.of(VARIABLES[0], -2, VARIABLES[1], 0, VARIABLES[2], false, VARIABLES[3], false, VARIABLES[4], false, VARIABLES[5], true),
        "Instantiation_3", Map.of(VARIABLES[0], 1, VARIABLES[1], -1, VARIABLES[2], false, VARIABLES[3], true, VARIABLES[4], false, VARIABLES[5], true)
        // Instantiation_3 satisfait toutes les contraintes
    );


    // affiche les différentes variables (variables, contraintes, instantiations) manipulées
    public static void describeWorld () {
        System.out.println("\n============ VARIABLES ===========\n");
        for (Variable variable : VARIABLES) {
            System.out.println(variable);
        }
        System.out.println("\n============ CONSTRAINTS ===========\n");
        for (String constraint : CONSTRAINTS.keySet()) {
            System.out.println(constraint + " : " + CONSTRAINTS.get(constraint));
        }
        System.out.println("\n============ INSTANTIATIONS =========\n");
        for (String instantiation : INSTANTIATIONS.keySet()) {
            System.out.println(instantiation + " : " + describeInstantiation(INSTANTIATIONS.get(instantiation)));
        }
        System.out.println();
    }

    // donne un affichage moins lourd d'une instantiation en affichant juste le nom des variables sans leur domaine
    public static String describeInstantiation (Map<Variable,Object> instantiation) {
        String representation = "{ ";
        for (Variable variable : instantiation.keySet()) {
            representation += variable.getName() + "=" + instantiation.get(variable) + " ";
        }
        representation += "}";
        return representation;
    }


    // Indique pour chaque instantiation si elle satisfait ou pas les contraintes
    public static void testConstraints () {
        System.out.println("\n=========== TEST CONSTRAINTS ===========");
        for (String instantiation : INSTANTIATIONS.keySet()) {
            System.out.println("\nDoes " +  instantiation);
            for (String constraint : CONSTRAINTS.keySet()) {
                System.out.println("\tsatisfies " + constraint + " ? " + CONSTRAINTS.get(constraint).isSatisfiedBy(INSTANTIATIONS.get(instantiation)));
            }
        }
        System.out.println();
    }

    
    public static void main (String[] args) {

        describeWorld();

        testConstraints();

    }

}