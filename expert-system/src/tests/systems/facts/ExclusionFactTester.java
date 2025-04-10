package tests.systems.facts;
import systems.facts.*;
import java.util.*;


public class ExclusionFactTester {

    public static final BooleanFact firstFact = new BooleanFact(new Variable<Boolean>("grand",true));
    public static final BooleanFact secondFact = new BooleanFact(new Variable<Boolean>("basketteur",true));
    protected ExclusionFact fact = new ExclusionFact(firstFact,secondFact);
    public static final BooleanFact fact1 = new BooleanFact(new Variable<Boolean>("grand",false));
    public static final BooleanFact fact3 = new BooleanFact(new Variable<Boolean>("basketteur",false));
    public static final BooleanFact fact2 = new BooleanFact(new Variable<Boolean>("handballeur",true));
    public static final ExclusionFact mixed = new ExclusionFact(firstFact,fact2);
    public static final ExclusionFact full = new ExclusionFact(fact1,fact2);
    public final static ExclusionFact identicFact = new ExclusionFact(firstFact,secondFact);

    public static final Set<Fact> nonCondratictory = Set.of(firstFact,fact3);
    public static final Set<Fact> condratictory = Set.of(firstFact,secondFact);


    public ExclusionFactTester(){
        this.fact = fact;
    }

    public boolean testImplements(){
        System.out.println("[Tests] [ExclusionFactTester::testImplements] launched");
        if (!(this.fact instanceof Fact)){
            System.out.println("[Tests] [ExclusionFactTester::testImplements] failed");
            return false;
        }
        
        System.out.println("[Tests] [ExclusionFactTester::testImplements] passed");
        return true;
    }

    public boolean testAreEquivalent(){
        System.out.println("[Tests] [ExclusionFactTester::testAreEquivalent] launched");
        if (!this.fact.areEquivalent(identicFact) || !this.fact.areEquivalent(mixed) || this.fact.areEquivalent(full)){
            System.out.println("[Tests] [ExclusionFactTester::testAreEquivalent] failed");
            return false;
        }
        System.out.println("[Tests] [ExclusionFactTester::testAreEquivalent] passed");
        return true;
    }

    public boolean testIsContradictory(){
        System.out.println("[Tests] [ExclusionFactTester::testIsContradictory] launched");
        if (!this.fact.isContradictory(condratictory) || this.fact.isContradictory(nonCondratictory)){
            System.out.println("[Tests] [ExclusionFactTester::testIsContradictory] failed");
            return false;
        }
        System.out.println("[Tests] [ExclusionFactTester::testIsContradictory] passed");
        return true;
    }

    public boolean testEquals(){
        System.out.println("[Tests] [ExclusionFactTester::testEquals] launched");
        if (!this.fact.equals(identicFact) || this.fact.equals(mixed) || this.fact.equals(full)){
            System.out.println("[Tests] [ExclusionFactTester::testEquals] failed");
            return false;
        }
        System.out.println("[Tests] [ExclusionFactTester::testEquals] passed");
        return true;
    }

}