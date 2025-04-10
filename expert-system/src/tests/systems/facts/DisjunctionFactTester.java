package tests.systems.facts;
import systems.facts.*;
import java.util.*;


public class DisjunctionFactTester{

    public static final BooleanFact firstFact = new BooleanFact(new Variable<Boolean>("grand",true));
    public static final BooleanFact secondFact = new BooleanFact(new Variable<Boolean>("basketteur",true));
    protected DisjunctionFact fact = new DisjunctionFact(firstFact,secondFact);
    public static final BooleanFact fact1 = new BooleanFact(new Variable<Boolean>("grand",false));
    public static final BooleanFact fact3 = new BooleanFact(new Variable<Boolean>("basketteur",false));
    public static final BooleanFact fact2 = new BooleanFact(new Variable<Boolean>("handballeur",true));
    public static final DisjunctionFact mixed = new DisjunctionFact(firstFact,fact2);
    public static final DisjunctionFact full = new DisjunctionFact(fact1,fact2);
    public final static DisjunctionFact identicFact = new DisjunctionFact(firstFact,secondFact);

    public static final Set<Fact> nonCondratictory = Set.of(fact1);
    public static final Set<Fact> condratictory = Set.of(fact1,fact3);

    public DisjunctionFactTester(){
        this.fact = fact;
    }

    public boolean testImplements(){
        System.out.println("[Tests] [DisjunctionFactTester::testImplements] launched");
        if (!(this.fact instanceof Fact)){
            System.out.println("[Tests] [DisjunctionFactTester::testImplements] failed");
            return false;
        }
        
        System.out.println("[Tests] [DisjunctionFactTester::testImplements] passed");
        return true;
    }

    public boolean testAreEquivalent(){
        System.out.println("[Tests] [DisjunctionFactTester::testAreEquivalent] launched");
        if (!this.fact.areEquivalent(identicFact) || !this.fact.areEquivalent(mixed) || this.fact.areEquivalent(full)){
            System.out.println("[Tests] [DisjunctionFactTester::testAreEquivalent] failed");
            return false;
        }
        System.out.println("[Tests] [DisjunctionFactTester::testAreEquivalent] passed");
        return true;
    }

    public boolean testIsContradictory(){
        System.out.println("[Tests] [DisjunctionFactTester::testIsContradictory] launched");
        if (!this.fact.isContradictory(condratictory) || this.fact.isContradictory(nonCondratictory)){
            System.out.println("[Tests] [DisjunctionFactTester::testIsContradictory] failed");
            return false;
        }
        System.out.println("[Tests] [DisjunctionFactTester::testIsContradictory] passed");
        return true;
    }

    public boolean testEquals(){
        System.out.println("[Tests] [DisjunctionFactTester::testEquals] launched");
        if (!this.fact.equals(identicFact) || this.fact.equals(mixed) || this.fact.equals(full)){
            System.out.println("[Tests] [DisjunctionFactTester::testEquals] failed");
            return false;
        }
        System.out.println("[Tests] [DisjunctionFactTester::testEquals] passed");
        return true;
    }


}