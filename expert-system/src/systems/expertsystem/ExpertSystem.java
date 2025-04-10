package systems.expertsystem;


import systems.base.*;
import systems.facts.*;
import systems.rules.*;
import systems.inferenceengines.*;

public class ExpertSystem{
    private KnowledgeBase base = new KnowledgeBase("../../../res/bases/climat.txt");
    private Engine engine = new Engine(base);

    public ExpertSystem(){
        
        this.base = base;
        this.engine = engine;
    }

    public KnowledgeBase getKnowledgeBase(){
        return this.base;
    }

    public Engine getEngine(){
        return this.engine;
    }

}