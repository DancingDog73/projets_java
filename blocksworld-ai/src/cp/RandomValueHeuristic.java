package cp;

import java.util.*;
import modelling.*;

public class RandomValueHeuristic implements ValueHeuristic{

    private Random randomGenerator;

    public RandomValueHeuristic(Random randomGenerator){
        this.randomGenerator = randomGenerator;
    }

    @Override
    public List<Object> ordering(Variable variable, Set<Object> domain){
        List<Object> randomDomain = new ArrayList<>(domain);
        Collections.shuffle(randomDomain, this.randomGenerator);
        return randomDomain;

    }


    @Override
    public String toString () {
        return "Random heuristic on values using the following generator: " + this.randomGenerator;
    }

}