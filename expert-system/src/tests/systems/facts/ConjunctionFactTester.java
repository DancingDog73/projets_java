package tests.systems.facts;
import systems.facts.*;
import java.util.*;


public class ConjunctionFactTester{

    public static final BooleanFact firstFact = new BooleanFact(new Variable<Boolean>("grand",true));
    public static final BooleanFact secondFact = new BooleanFact(new Variable<Boolean>("basketteur",true));
    protected ConjunctionFact fact = new ConjunctionFact(firstFact,secondFact);
    public static final BooleanFact fact1 = new BooleanFact(new Variable<Boolean>("grand",false));
    public static final BooleanFact fact2 = new BooleanFact(new Variable<Boolean>("handballeur",true));
    public static final ConjunctionFact mixed = new ConjunctionFact(firstFact,fact2);
    public static final ConjunctionFact full = new ConjunctionFact(fact1,fact2);
    public final static ConjunctionFact identicFact = new ConjunctionFact(secondFact,firstFact);

    public static final Set<Fact> nonCondratictory = Set.of(fact2);
    public static final Set<Fact> condratictory = Set.of(fact1);

    
    public ConjunctionFactTester(){
        this.fact = fact;
    }

    public boolean testImplements(){
        System.out.println("[Tests] [ConjunctionFactTester::testImplements] launched");
        if (!(this.fact instanceof Fact)){
            System.out.println("[Tests] [ConjunctionFactTester::testImplements] failed");
            return false;
        }
        
        System.out.println("[Tests] [ConjunctionFactTester::testImplements] passed");
        return true;
    }

    public boolean testAreEquivalent(){
        System.out.println("[Tests] [ConjunctionFactTester::testAreEquivalent] launched");
        if (!this.fact.areEquivalent(identicFact) || this.fact.areEquivalent(mixed)){
            System.out.println("[Tests] [ConjunctionFactTester::testAreEquivalent] failed");
            return false;
        }
        System.out.println("[Tests] [ConjunctionFactTester::testAreEquivalent] passed");
        return true;
    }

    public boolean testIsContradictory(){
        System.out.println("[Tests] [ConjunctionFactTester::testIsContradictory] launched");
        if (!this.fact.isContradictory(condratictory) || this.fact.isContradictory(nonCondratictory)){
            System.out.println("[Tests] [ConjunctionFactTester::testIsContradictory] failed");
            return false;
        }
        System.out.println("[Tests] [ConjunctionFactTester::testIsContradictory] passed");
        return true;
    }

    public boolean testEquals(){
        System.out.println("[Tests] [ConjunctionFactTester::testEquals] launched");
        if (!this.fact.equals(identicFact) || this.fact.equals(full)){
            System.out.println("[Tests] [ConjunctionFactTester::testEquals] failed");
            return false;
        }
        System.out.println("[Tests] [ConjunctionFactTester::testEquals] passed");
        return true;
    }





}