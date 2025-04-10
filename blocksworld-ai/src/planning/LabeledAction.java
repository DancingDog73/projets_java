package planning;

import java.util.*;
import modelling.*;


public class LabeledAction extends BasicAction {

    private String description;

    public LabeledAction (Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost, String description) {
        super(precondition, effect, cost);
        this.description = description;
    }


    @Override
    public String toString () {
        return this.description;
    }

}