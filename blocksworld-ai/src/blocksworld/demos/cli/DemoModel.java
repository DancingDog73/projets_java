package blocksworld.demos.cli;

import java.util.*;
import blocksworld.model.*;
import blocksworld.demos.util.*;;


public class DemoModel {

    private static final String SEPARATOR = "----------------------------------------------------------------------------------------";

    public static void printHeader () {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("BLOCKS WORLD MODEL DEMO");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    public static void printManual () {
        printHeader();
        System.out.println("Indiquez en arguments le nombre de blocs et de piles de votre monde puis la\npartie du modèle que vous voulez voir selon les paramètres qui suivent :\n");
        System.out.println("1 -> Variables\n2 -> Contraintes de base\n3 -> Contraintes de régularité\n4 -> Contraintes de croissance\n5 -> Actions\n");
        System.out.println(SEPARATOR);
        System.out.println("\nPar exemple :\n\tjava -cp ../build blocksworld.demos.cli.DemoModel 4 2 5\nvous donne toutes les actions d'un monde de blocs de 4 blocs et 2 piles\n");
        System.out.println(SEPARATOR);
        System.out.println();
    }

    public static <E> void printModel (Set<E> objects, int blocksCount, int stacksCount, String description) {
        printHeader();
        System.out.println("Nombre de blocs : " + blocksCount);
        System.out.println("Nombre de piles : " + stacksCount + "\n");
        System.out.println(SEPARATOR);
        BlocksWorldUtil.printObjects(objects, description);
        System.out.println(SEPARATOR);
        System.out.println();
    }


    public static void main (String[] args) {

        try {

            int blocksCount = Integer.parseInt(args[0]);
            int stacksCount = Integer.parseInt(args[1]);
            int model = Integer.parseInt(args[2]);

            switch (model) {
                case 1:
                    WorldVariables wv = new WorldVariables(blocksCount,stacksCount);
                    printModel(wv.getVariables(), blocksCount, stacksCount, "VARIABLES");
                    break;
                case 2:
                    WorldConstraints wc = new WorldConstraints(blocksCount,stacksCount);
                    printModel(wc.getConstraints(), blocksCount, stacksCount, "CONTRAINTES DE BASE");
                    break;
                case 3:
                    WorldRegularityConstraints wrc = new WorldRegularityConstraints(blocksCount,stacksCount);
                    printModel(wrc.getRegularityConstraints(), blocksCount, stacksCount, "CONTRAINTES DE REGULARITE");
                    break;
                case 4:
                    WorldGrowthConstraints wgc = new WorldGrowthConstraints(blocksCount,stacksCount);
                    printModel(wgc.getGrowthConstraints(), blocksCount, stacksCount, "CONTRAINTES DE CROISSANCE");
                    break;
                case 5:
                    WorldActions wa = new WorldActions(blocksCount,stacksCount);
                    printModel(wa.getActions(), blocksCount, stacksCount, "ACTIONS");
                    break;
                default:
                    printManual();
            }

        } catch (Exception e) {
            printManual();
        }
    }

}