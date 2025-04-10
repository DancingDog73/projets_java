package tests.systems.facts;
import systems.facts.*;
import java.util.*;


public class NumericFactTester {

    protected NumericFact fact = new NumericFact(new Variable<Float>("temperature",12f),">");
    public static final NumericFact identicFact = new NumericFact(new Variable<Float>("temperature",12f),">");
    public static final NumericFact otherFact = new NumericFact(new Variable<Float>("temperature",12f),"<");
    public static final NumericFact compatibleFact = new NumericFact(new Variable<Float>("temperature",13f),"=");
    

    public static final Set<Fact> nonCondratictory = Set.of(compatibleFact);
    public static final Set<Fact> condratictory = Set.of(identicFact, otherFact);

    public NumericFactTester(){
        this.fact = fact;
    }

    public boolean testImplements(){
        System.out.println("[Tests] [NumericFactTester::testImplements] launched");
        if (!(this.fact instanceof Fact)){
            System.out.println("[Tests] [NumericFactTester::testImplements] failed");
            return false;
        }
        
        System.out.println("[Tests] [NumericFactTester::testImplements] passed");
        return true;
    }

    public boolean testGetOperator(){
        System.out.println("[Tests] [NumericFactTester::testGetOperator] launched");
        if (!(this.fact.getOperator().equals(">"))){
            System.out.println("[Tests] [NumericFactTester::testGetOperator] failed");
            return false;
        }
        
        System.out.println("[Tests] [NumericFactTester::testGetOperator] passed");
        return true;
    }


    public boolean testGetVariable(){
        System.out.println("[Tests] [NumericFactTester::testGetVariable] launched");
        if (!(this.fact.getName().equals("temperature"))){
            System.out.println("[Tests] [NumericFactTester::testGetVariable] failed");
            return false;
        }
        
        System.out.println("[Tests] [NumericFactTester::testGetVariable] passed");
        return true;
    }


    public boolean testGetValue(){
        System.out.println("[Tests] [NumericFactTester::testGetValue] launched");
        if (!(this.fact.getValue() == 12)){
            System.out.println("[Tests] [NumericFactTester::testGetValue] failed");
            return false;
        }
        
        System.out.println("[Tests] [NumericFactTester::testGetValue] passed");
        return true;
    }

    public boolean testArequivalent(){
        System.out.println("[Tests] [NumericFactTester::testArequivalent] launched");
        if (!(this.fact.areEquivalent(compatibleFact)) || (this.fact.areEquivalent(otherFact))){
            System.out.println("[Tests] [NumericFactTester::testArequivalent] failed");
            return false;
        }
        
        System.out.println("[Tests] [NumericFactTester::testArequivalent] passed");
        return true;
    }

    public boolean testEquals(){
        System.out.println("[Tests] [NumericFactTester::testEquals] launched");
        
        if (!(this.fact.equals(identicFact)) || (this.fact.equals(otherFact))){
            System.out.println("[Tests] [NumericFactTester::testEquals] failed");
            return false;
        }
        
        System.out.println("[Tests] [NumericFactTester::testEquals] passed");
        return true;
    }


    public boolean testHashCode(){
        System.out.println("[Tests] [NumericFactTester::testHashCode] launched");
        if (!(this.fact.hashCode() == identicFact.hashCode()) || (this.fact.hashCode() == otherFact.hashCode())){
            System.out.println("[Tests] [NumericFactTester::testHashCode] failed");
            return false;
        }
        
        System.out.println("[Tests] [NumericFactTester::testHashCode] passed");
        return true;
    }

    public boolean testIsContradictory(){
        System.out.println("[Tests] [NumericFactTester::testIsContradictory] launched");
        if ((this.fact.isContradictory(nonCondratictory)) || !(this.fact.isContradictory(condratictory))){
            System.out.println("[Tests] [NumericFactTester::testIsContradictory] failed");
            return false;
        }
        
        System.out.println("[Tests] [NumericFactTester::testIsContradictory] passed");
        return true;
    }

}