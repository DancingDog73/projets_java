package cp;

import java.util.*;
import modelling.*;


public class MACSolver extends AbstractSolver {

    private ArcConsistency consistencyTester;

    public MACSolver (Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
        this.consistencyTester = new ArcConsistency(constraints);
    }

    // Méthode principale pour résoudre le problème en utilisant la consistance d'arc
    @Override
    public Map<Variable,Object> solve () {
        // Initialisation des domaines pour chaque variable
        Map<Variable,Set<Object>> domains = new HashMap<>();
        for (Variable variable : this.variables) {
            domains.put(variable, variable.getDomain());
        }
        return this.solveAux(new HashMap<>(), new LinkedList<>(this.variables), domains);
    }

    // Méthode auxiliaire récursive qui résout le problème en appliquant la consistance d'arc
    public Map<Variable,Object> solveAux (Map<Variable,Object> partialInstanciation, List<Variable> uninstanciated, Map<Variable,Set<Object>> domains) {
        // Si toutes les variables sont instanciées, retourne la solution partielle
        if (uninstanciated.isEmpty()) {
            return partialInstanciation;
        }
        if (!this.consistencyTester.ac1(domains)) {
            return null;
        }
        Variable variable = uninstanciated.remove(0);
         //On retire la première variable non instanciée et on teste chaque valeur 
        for (Object value : domains.get(variable)) {
            Map<Variable,Object> newInstanciation = new HashMap<>(partialInstanciation);
            newInstanciation.put(variable, value);
            if (this.isConsistent(newInstanciation)) {
                //Si l'intanciation partielle qu'on vient de créer est cohérente, on rappelle la métode récursivement pour continuer avec les variables suivantes
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
        return "------------------------------\nMAC " + super.toString();
    }

}