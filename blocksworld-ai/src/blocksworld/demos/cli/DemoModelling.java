package blocksworld.demos.cli;

import modelling.*;
import blocksworld.model.*;
import blocksworld.demos.util.*;
import java.util.*;


public class DemoModelling {

    private static final String SEPARATOR = "----------------------------------------------------------------------------------------------------------";

    public static void printHeader () {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("BLOCKS WORLD - CONSTRAINTES");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    public static void printManual () {
        printHeader();
        System.out.println("Indiquez en arguments le nombre de blocs et de piles de votre monde pour\ntester la satisfaction des contraintes sur instanciation générée aléatoirement à partir de ces paramètres\n");
        System.out.println(SEPARATOR);
        System.out.println("\nPar exemple :\n\tjava -cp ../build blocksworld.demos.cli.DemoModelling 4 3\nteste la satisfaction de contraintes sur une instanciation choisie au hasard sur 4 blocs et 3 piles\n");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    public static void runSatisfactionTests (int blocksCount, int stacksCount) {
        Map<Variable,Object> instantiation = BlocksWorldUtil.generateRandomConfiguration(blocksCount, stacksCount);
        printHeader();
        BlocksWorldUtil.printState(instantiation, "INSTANCIATION");
        System.out.println(SEPARATOR);
        runSatisfactionTest(instantiation, BlocksWorldUtil.getBasicConstraints(blocksCount, stacksCount), "Contraintes de base");
        runSatisfactionTest(instantiation, BlocksWorldUtil.getGrowthIConstraints(blocksCount, stacksCount), "Contraintes de croissance");
        runSatisfactionTest(instantiation, BlocksWorldUtil.getGrowthDConstraints(blocksCount, stacksCount), "Contraintes de décroissance");
        runSatisfactionTest(instantiation, BlocksWorldUtil.getRegularityConstraints(blocksCount, stacksCount), "Contraintes de régularité");
        runSatisfactionTest(instantiation, BlocksWorldUtil.getAllConstraints(blocksCount, stacksCount), "Toutes contraintes");
        System.out.println();
    }

    public static void runSatisfactionTest (Map<Variable,Object> instantiation, Set<Constraint> constraints, String description) {
        System.out.println(description);
        System.out.println("\tSatisfaites ? " + (BlocksWorldUtil.allConstraintsAreSatisfied(instantiation, constraints) ? "oui" : "non"));
        System.out.println(SEPARATOR);
    }

    public static void main (String[] args) {

        try {

            int blocksCount = Integer.parseInt(args[0]);
            int stacksCount = Integer.parseInt(args[1]);

            runSatisfactionTests(blocksCount, stacksCount);

        } catch (Exception e) {
            printManual();
        }

    }

}