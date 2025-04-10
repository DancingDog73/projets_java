package cp;

import modelling.*;
import java.util.*;


public class HeuristicMACSolver extends AbstractSolver {

    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;
    private ArcConsistency consistencyTester;

    public HeuristicMACSolver (Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
        this.consistencyTester = new ArcConsistency(constraints);
    }

    // Méthode principale pour résoudre le problème en utilisant les heuristiques et la consistance d'arc
    @Override
    public Map<Variable,Object> solve () {
        // Initialisation des domaines pour chaque variable
        Map<Variable,Set<Object>> domains = new HashMap<>();
        for (Variable variable : this.variables) {
            domains.put(variable, variable.getDomain());
        }
        return this.solveAux(new HashMap<>(), new HashSet<>(this.variables), domains);
    }

    public Map<Variable,Object> solveAux (Map<Variable,Object> partialInstanciation, Set<Variable> uninstanciated, Map<Variable,Set<Object>> domains) {
        // Si toutes les variables sont instanciées, retourne la solution partielle
        if (uninstanciated.isEmpty()) {
            return partialInstanciation;
        }

        // Applique la consistance d'arc pour réduire les domaines des variables
        if (!this.consistencyTester.ac1(domains)) {
            return null;
        }

        // Sélectionne la meilleure variable non instanciée en utilisant l'heuristique
        Variable variable = this.variableHeuristic.best(uninstanciated, domains);
        uninstanciated.remove(variable);
        List<Object> orderedDomain = this.valueHeuristic.ordering(variable, domains.get(variable));
        //On ordonne les valeurs de notre domaine grâce à l'heuristique puis on teste chaque valeur
        for (Object value : orderedDomain) {
            Map<Variable,Object> newInstanciation = new HashMap<>(partialInstanciation);
            newInstanciation.put(variable, value);
            //Si'linstantiation partielle qu'on vient de créer est cohérente, on appelle récursivement la méthode sinon on renvoie null
            if (this.isConsistent(newInstanciation)) {
                Map<Variable,Object> solution = this.solveAux(newInstanciation, uninstanciated, domains);
                if (solution != null) {
                    return solution;
                }
            }
        }
        // Si aucune solution n'a été trouvée avec la valeur actuelle, remet la variable dans les variables non instanciées
        uninstanciated.add(variable);
        return null;
    }


    @Override
    public String toString () {
        return "------------------------------\nHEURISTIC MAC " + super.toString() + "\nHeuristics\n\t" + this.variableHeuristic + "\n\t" + this.valueHeuristic + "\n------------------------------";
    }

}