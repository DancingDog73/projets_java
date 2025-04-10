package tests.systems.facts;
import systems.facts.*;
import java.util.*;


public class BooleanFactTester {

    protected BooleanFact fact = new BooleanFact(new Variable<Boolean>("gourmand",true));
    public static final BooleanFact identicFact = new BooleanFact(new Variable<Boolean>("gourmand",true));
    public static final BooleanFact otherFact = new BooleanFact(new Variable<Boolean>("gourmand",false));

    public static final Set<Fact> nonCondratictory = Set.of(identicFact);
    public static final Set<Fact> condratictory = Set.of(identicFact, otherFact);

    public BooleanFactTester(){
        this.fact = fact;
    }

    public boolean testImplements(){
        System.out.println("[Tests] [BooleanFactTester::testImplements] launched");
        if (!(this.fact instanceof Fact)){
            System.out.println("[Tests] [BooleanFactTester::testImplements] failed");
            return false;
        }
        
        System.out.println("[Tests] [BooleanFactTester::testImplements] passed");
        return true;
    }

    public boolean testGetVariable(){
        System.out.println("[Tests] [BooleanFactTester::testGetVariable] launched");
        if (!(this.fact.getName().equals("gourmand"))){
            System.out.println("[Tests] [BooleanFactTester::testGetVariable] failed");
            return false;
        }
        
        System.out.println("[Tests] [BooleanFactTester::testGetVariable] passed");
        return true;
    }


    public boolean testGetValue(){
        System.out.println("[Tests] [BooleanFactTester::testGetValue] launched");
        if (!(this.fact.getValue())){
            System.out.println("[Tests] [BooleanFactTester::testGetValue] failed");
            return false;
        }
        
        System.out.println("[Tests] [BooleanFactTester::testGetValue] passed");
        return true;
    }

    public boolean testArequivalent(){
        System.out.println("[Tests] [BooleanFactTester::testArequivalent] launched");
        if (!(this.fact.areEquivalent(identicFact)) || (this.fact.areEquivalent(otherFact))){
            System.out.println("[Tests] [BooleanFactTester::testArequivalent] failed");
            return false;
        }
        
        System.out.println("[Tests] [BooleanFactTester::testArequivalent] passed");
        return true;
    }

    public boolean testEquals(){
        System.out.println("[Tests] [BooleanFactTester::testEquals] launched");
        if (!(this.fact.equals(identicFact)) || (this.fact.equals(otherFact))){
            System.out.println("[Tests] [BooleanFactTester::testEquals] failed");
            return false;
        }
        
        System.out.println("[Tests] [BooleanFactTester::testEquals] passed");
        return true;
    }


    public boolean testHashCode(){
        System.out.println("[Tests] [BooleanFactTester::testHashCode] launched");
        if (!(this.fact.hashCode() == identicFact.hashCode()) || (this.fact.hashCode() == otherFact.hashCode())){
            System.out.println("[Tests] [BooleanFactTester::testHashCode] failed");
            return false;
        }
        
        System.out.println("[Tests] [BooleanFactTester::testHashCode] passed");
        return true;
    }

    public boolean testIsContradictory(){
        System.out.println("[Tests] [BooleanFactTester::testIsContradictory] launched");
        if ((this.fact.isContradictory(nonCondratictory)) || !(this.fact.isContradictory(condratictory))){
            System.out.println("[Tests] [BooleanFactTester::testIsContradictory] failed");
            return false;
        }
        
        System.out.println("[Tests] [BooleanFactTester::testIsContradictory] passed");
        return true;
    }

}