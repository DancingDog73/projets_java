package cp;

import java.util.*;
import modelling.*;

public class Demo {

    public static void main(String[] args) {

        System.out.println();
        
        //Nos variables et de leurs domaines 
        Variable A = new Variable("A", Set.of("Red", "Green", "Blue"));
        Variable B = new Variable("B", Set.of("Red", "Green", "Blue"));
        Variable C = new Variable("C", Set.of("Red", "Green", "Blue"));
        Variable D = new Variable("D", Set.of("Red", "Green", "Blue"));

        Set<Variable> variables = Set.of(A, B, C, D);

        //On définit les contraintes pour que chaque variable soit différente
        Constraint constraintAB = new DifferenceConstraint(A, B);
        
        Constraint constraintBC = new DifferenceConstraint(B, C);
        
        Constraint constraintCD = new DifferenceConstraint(C, D);
        
        Constraint constraintAD = new DifferenceConstraint(A, D);

        Set<Constraint> constraints = Set.of(constraintAB, constraintBC, constraintCD, constraintAD);

        VariableHeuristic varHeuristic = new DomainSizeVariableHeuristic(false);  
        ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());  
        
        // Résolution du problème avec HeuristicMACSolver
        HeuristicMACSolver heuristicSolver = new HeuristicMACSolver(variables, constraints, varHeuristic, valHeuristic);
        System.out.println(heuristicSolver + "\n");
        System.out.println("Résolution avec HeuristicMACSolver:");
        Map<Variable, Object> solution = heuristicSolver.solve();

        if (solution != null) {
            System.out.println("Solution trouvée: " + solution);
        } else {
            System.out.println("Aucune solution trouvée.");
        }

        //Résolution du problème avec MACSolver (sans heuristique)
        MACSolver macSolver = new MACSolver(variables, constraints);
        System.out.println("\n\n" + macSolver);
        System.out.println("\nRésolution avec MACSolver (sans heuristique):");
        solution = macSolver.solve();

        if (solution != null) {
            System.out.println("Solution trouvée: " + solution);
        } else {
            System.out.println("Aucune solution trouvée.");
        }

        System.out.println();


        // Juste pour compiler ces classes grâce à la seule compilation de la démo, permettant ainsi la réalisation des Test sans autre manoeuvre de compilation
        NbConstraintsVariableHeuristic h1 = new NbConstraintsVariableHeuristic(constraints, false);
        BacktrackSolver s1 = new BacktrackSolver(variables, constraints);

    }
}
