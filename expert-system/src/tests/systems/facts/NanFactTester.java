package tests.systems.facts;
import java.util.*;
import systems.facts.*;


public class NanFactTester {

    protected NanFact fact = new NanFact(new Variable<String>("profession","plongeur"));
    public static final NanFact identicFact = new NanFact(new Variable<String>("profession","plongeur"));
    public static final NanFact otherFact = new NanFact(new Variable<String>("profession","handballeur"));

    public static final Set<Fact> nonCondratictory = Set.of(identicFact);
    public static final Set<Fact> condratictory = Set.of(identicFact, otherFact);

    public NanFactTester(){
        this.fact = fact;
    }

    public boolean testImplements(){
        System.out.println("[Tests] [NanFactTester::testImplements] launched");
        if (!(this.fact instanceof Fact)){
            System.out.println("[Tests] [NanFactTester::testImplements] failed");
            return false;
        }
        
        System.out.println("[Tests] [NanFactTester::testImplements] passed");
        return true;
    }

    public boolean testGetVariable(){
        System.out.println("[Tests] [NanFactTester::testGetVariable] launched");
        if (!(this.fact.getName().equals("profession"))){
            System.out.println("[Tests] [NanFactTester::testGetVariable] failed");
            return false;
        }
        
        System.out.println("[Tests] [NanFactTester::testGetVariable] passed");
        return true;
    }


    public boolean testGetValue(){
        System.out.println("[Tests] [NanFactTester::testGetValue] launched");
        if (!(this.fact.getValue().equals("plongeur"))){
            System.out.println("[Tests] [NanFactTester::testGetValue] failed");
            return false;
        }
        
        System.out.println("[Tests] [NanFactTester::testGetValue] passed");
        return true;
    }

    public boolean testArequivalent(){
        System.out.println("[Tests] [NanFactTester::testArequivalent] launched");
        if (!(this.fact.areEquivalent(identicFact)) || (this.fact.areEquivalent(otherFact))){
            System.out.println("[Tests] [NanFactTester::testArequivalent] failed");
            return false;
        }
        
        System.out.println("[Tests] [NanFactTester::testArequivalent] passed");
        return true;
    }

    public boolean testEquals(){
        System.out.println("[Tests] [NanFactTester::testEquals] launched");
        if (!(this.fact.equals(identicFact)) || (this.fact.equals(otherFact))){
            System.out.println("[Tests] [NanFactTester::testEquals] failed");
            return false;
        }
        
        System.out.println("[Tests] [NanFactTester::testEquals] passed");
        return true;
    }


    public boolean testHashCode(){
        System.out.println("[Tests] [NanFactTester::testHashCode] launched");
        if (!(this.fact.hashCode() == identicFact.hashCode()) || (this.fact.hashCode() == otherFact.hashCode())){
            System.out.println("[Tests] [NanFactTester::testHashCode] failed");
            return false;
        }
        
        System.out.println("[Tests] [NanFactTester::testHashCode] passed");
        return true;
    }

    public boolean testIsContradictory(){
        System.out.println("[Tests] [NanFactTester::testIsContradictory] launched");
        if ((this.fact.isContradictory(nonCondratictory)) || !(this.fact.isContradictory(condratictory))){
            System.out.println("[Tests] [NanFactTester::testIsContradictory] failed");
            return false;
        }
        
        System.out.println("[Tests] [NanFactTester::testIsContradictory] passed");
        return true;
    }

}