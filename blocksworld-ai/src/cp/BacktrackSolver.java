package cp;

import modelling.*;
import java.util.*;

public class BacktrackSolver extends AbstractSolver{

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints){
        super(variables, constraints);
    }


    @Override 
    public Map<Variable, Object> solve(){
        //On appelle une méthode auxiliaire qui va appliquer le backtracking
        return solveAux(new HashMap<Variable, Object>(), new LinkedList<Variable>(super.getVariables()));
    }

    public Map<Variable,Object> solveAux (Map<Variable,Object> partialSolution, List<Variable> uninstantiated) {
        // Si toutes les variables sont instanciées, retourne la solution partielle actuelle
        if (uninstantiated.isEmpty()){
            return partialSolution;
        }
        Variable variable = uninstantiated.remove(0);
        for (Object value : variable.getDomain()) {
            //On crée une nouvelle instanciation partielle et on y ajoute la valeur courante
            Map<Variable,Object> newPartialSolution = new HashMap<>(partialSolution);
            newPartialSolution.put(variable, value);
            if (this.isConsistent(newPartialSolution)) {
                // Appelle récursivement solveAux pour essayer d'attribuer les autres variables
                Map<Variable,Object> solution = this.solveAux(newPartialSolution, uninstantiated);
                //Si on trouve une solution non nulle, on la retourne
                if (solution != null){
                    return solution;
                }
            }
        }
        // Si aucune valeur ne fonctionne, remet la variable dans la liste des non instanciées pour d'autres essais
        uninstantiated.add(0, variable);
        // Retourne null si aucune solution n'est trouvée avec l'attribution actuelle
        return null;
    }


    @Override
    public String toString () {
        return "------------------------------\nBACKTRACK " + super.toString();
    }


}