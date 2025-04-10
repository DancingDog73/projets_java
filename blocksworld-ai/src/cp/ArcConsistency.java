package cp;

import modelling.*;
import java.util.*;

public class ArcConsistency {

    Set<Constraint> constraints;
    
    public ArcConsistency(Set<Constraint> constraints){
        for (Constraint c: constraints){
            if (c.getScope().size() > 2){
                throw new IllegalArgumentException("All constraints should be unary or binary : " + c);
                
            }
        }
        this.constraints = constraints;
    }

    //Permet de supprimer les valuers non valides dans les domaines. On retourne false si un domaine est vidé, true sinon.
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains){
        boolean res = true;
        for(Constraint constraint: this.constraints){
            if (constraint.getScope().size() == 1){
                Variable variable = constraint.getScope().iterator().next();
                Set<Object> domain = domains.get(variable);
                Set<Object> toBeRemoved = new HashSet<>(); //Ensemble des valeurs non valides(elles seront donc retirées du domaine)
                for (Object value: domain){
                    //On crée une affectation temporaire pour tester chaque valeur du domaine
                    Map<Variable, Object> tmp = Map.of(variable,value);
                    if (!constraint.isSatisfiedBy(tmp)){
                        toBeRemoved.add(value); // Ajoute les valeurs non satisfaites à supprimer
                    }
                }
                domain.removeAll(toBeRemoved);
                if (domain.size() == 0){ //Si le domaine est vide à la fin alors la consistence n'est pas vérifiée
                    res = false;
                }
            }

        }
        return res;
    }

    // Applique la révision d'arc entre deux variables, v1 et v2, pour vérifier la consistance entre les deux
    public boolean revise(Variable v1, Set<Object> d1, Variable v2, Set<Object> d2){
        boolean res = false;
        Set<Object> toBeRemoved = new HashSet<>();

        for(Object value1 : d1){
            boolean found = false;
            for(Object value2 : d2){
                boolean allSatisfied = true;
                for (Constraint constraint: this.constraints){
                    Set<Variable> scope = constraint.getScope();
                    if (scope.contains(v1) && scope.contains(v2)){
                        //Si une contrainte contient nos deux variables, on teste leurs valeurs
                        Map<Variable,Object> toBeTested = Map.of(v1,value1,v2,value2);
                        if (!constraint.isSatisfiedBy(toBeTested)){ // Si une contrainte échoue, on sort de la boucle
                            allSatisfied = false;
                            break;
                        }
                    }
                }
                if (allSatisfied){ //Si on trouve un binome qui satisfait toutes les contraintes, on continue d'itérer sur le domaine de d1
                    found = true;
                    break;
                }
            }
            if (!found){ //Si aucun binôme n'est valide, on ajoute la valeur à l'ensembles des valeurs à supprimer
                toBeRemoved.add(value1);
            }
        }

        if(!toBeRemoved.isEmpty()){
            d1.removeAll(toBeRemoved); // Supprime les valeurs de d1 qui n'ont pas de correspondance valide dans d2
            res = true; //On retourne true vu qu'on a supprimé au moins une valeur
        }

        return res;
    }

    //applique l'arc-consistance à l'ensemble des domaines
    public boolean ac1(Map<Variable, Set<Object>> domains){
        if(!enforceNodeConsistency(domains)){
            return false;
        }

        boolean change = false;
        do {
            change = false;
            for(Variable variable1 : domains.keySet()){
                for(Variable variable2 : domains.keySet()){
                    // Vérifie et applique la révision entre chaque paire de variables distinctes
                    if(!variable1.equals(variable2) && revise(variable1, domains.get(variable1), variable2, domains.get(variable2))){
                        change = true;
                    }
                }
            }
        } while (change);
        
        //Si un domaine est vide à la fin alors on renvoie false
        for(Variable variable : domains.keySet()){
            if (domains.get(variable).isEmpty()){
                return false;
            }
        }
        return true;
        
    }

    
    @Override
    public String toString () {
        return "Arc consistency on following constraints :\n" + this.constraints;
    }

}