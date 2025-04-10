package planning;

import java.util.*;
import modelling.*;


/*
  La classe StateComparator nous permettra de comparer deux deux états en nous indiquant lequel est le plus proche (le plus petit).
  Pour ce faire, elle stockera le tableau des distances.
  Son utilité sera de nous permettre d'utiliser des priority queues dans nos algos de planning, la récupération de l'état le plus
   proche pouvant désormais se faire en temps constant plutôt qu'en temps linéaire.
*/

public class StateComparator implements Comparator<Map<Variable,Object>> {

    private Map<Map<Variable, Object>, Double> distances;

    public StateComparator(Map<Map<Variable, Object>, Double> distances) {
        this.distances = distances;
    }

    
    @Override
    public int compare (Map<Variable,Object> firstState, Map<Variable,Object> secondState) throws IllegalArgumentException {

        // Il est nécessaire que les deux états comparés soient présents dans notre tableau des distances.
        if (!(distances.containsKey(firstState) && distances.containsKey(secondState))) {
            throw new IllegalArgumentException("Trying to compare a state which is not in the comparator distances.");
        }

        double firstDistance = distances.get(firstState);
        double secondDistance = distances.get(secondState);

        if (firstDistance < secondDistance) {
            return -1;
        }
        if (firstDistance > secondDistance) {
            return 1;
        }
        return 0;
    }

}