package modelling;

import java.util.*;


public class BooleanVariable extends Variable {

    public BooleanVariable (String name) {
        super(name, new HashSet<>(Arrays.asList(true, false)));
    }

}