package blocksworld.demos.gui;

import java.util.*;
import modelling.*;
import planning.*;
import cp.*;
import bwmodel.*;
import bwui.*;
import javax.swing.*;
import blocksworld.demos.util.*;
import blocksworld.model.*;


public class GUIRepresentation {

    private static final String SEPARATOR = "----------------------------------------------------------------------------------------------------------------";


    public static void printManual () {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("BLOCKS WORLD - MODELLING GUI");
        System.out.println(SEPARATOR);
        System.out.println();
        System.out.println("Indiquez en arguments le nombre de blocs et de piles de votre monde pour\nvoir une instanciation générée aléatoirement à partir de ces paramètres\n");
        System.out.println(SEPARATOR);
        System.out.println("\nPar exemple :\n\tjava -cp ../build:../lib/blocksworld.jar blocksworld.demos.gui.GUIRepresentation 6 4\naffiche la représentation d'une instanciation choisie au hasard sur 4 blocs et 3 piles\n! Des nombres trop grands peuvent être source d'erreur ! Réessayer plusieurs fois si nécessaire\n");
        System.out.println(SEPARATOR);
        System.out.println();
    }


    public static void main (String[] args) {


        try {

            int blocksCount = Integer.parseInt(args[0]);
            int stacksCount = Integer.parseInt(args[1]);

            Map<Variable,Object> configuration = BlocksWorldUtil.generateRandomConfiguration(blocksCount, stacksCount);
            WorldVariables variableBuilder = new WorldVariables(blocksCount, stacksCount);

            // Building state
            BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(blocksCount);
            for (int b = 0; b < blocksCount; b++) {
                Variable onB = variableBuilder.onBlockVariable(b); // get instance of Variable for "on_b"
                int under = (int) configuration.get(onB);
                if (under >= 0) { // if the value is a block (as opposed to a stack)
                    builder.setOn(b, under);
                }
            }

            BWState<Integer> state = builder.getState();
            
            // Displaying
            BWIntegerGUI gui = new BWIntegerGUI(blocksCount);
            JFrame frame = new JFrame("CONFIGURATION ALEATOIRE (" + blocksCount + " blocs et " + stacksCount + " piles)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(gui.getComponent(state));
            frame.pack();
            frame.setSize(1200, 800);
            frame.setVisible(true);

        } catch (Exception e) {
            printManual();
        }

    }

}