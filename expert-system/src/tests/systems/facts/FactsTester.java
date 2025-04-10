package tests.systems.facts;
import systems.facts.*;

public class FactsTester {

    public  boolean testAll (){

        boolean ok = true;

        System.out.println("NanFactTester");
        NanFactTester nanFactTester = new NanFactTester();
        ok = ok && nanFactTester.testImplements();
        ok = ok && nanFactTester.testGetValue();
        ok = ok && nanFactTester.testGetVariable();
        ok = ok && nanFactTester.testArequivalent();
        ok = ok && nanFactTester.testEquals();
        ok = ok && nanFactTester.testHashCode();
        ok = ok && nanFactTester.testIsContradictory();
        
        System.out.println("NumericFactTester");
        NumericFactTester numericFactTester = new NumericFactTester();
        ok = ok && numericFactTester.testImplements();
        ok = ok && numericFactTester.testGetValue();
        ok = ok && numericFactTester.testGetOperator();
        ok = ok && numericFactTester.testGetVariable();
        ok = ok && numericFactTester.testArequivalent();
        ok = ok && numericFactTester.testEquals();
        ok = ok && numericFactTester.testHashCode();
        ok = ok && numericFactTester.testIsContradictory();

        System.out.println("BooleanFactTester");
        BooleanFactTester booleanFactTester = new BooleanFactTester();
        ok = ok && booleanFactTester.testImplements();
        ok = ok && booleanFactTester.testGetValue();
        ok = ok && booleanFactTester.testGetVariable();
        ok = ok && booleanFactTester.testArequivalent();
        ok = ok && booleanFactTester.testEquals();
        ok = ok && booleanFactTester.testHashCode();
        ok = ok && booleanFactTester.testIsContradictory();

        System.out.println("ConjunctionFactTester");
        ConjunctionFactTester conjunctionFactTester = new ConjunctionFactTester();
        ok = ok && conjunctionFactTester.testImplements();
        ok = ok && conjunctionFactTester.testAreEquivalent();
        ok = ok && conjunctionFactTester.testIsContradictory();
        ok = ok && conjunctionFactTester.testEquals();

        System.out.println("DisjunctionFactTester");
        DisjunctionFactTester disjunctionFactTester = new DisjunctionFactTester();
        ok = ok && disjunctionFactTester.testImplements();
        ok = ok && disjunctionFactTester.testAreEquivalent();
        ok = ok && disjunctionFactTester.testIsContradictory();
        ok = ok && disjunctionFactTester.testEquals();

        System.out.println("ExclusionFactTester");
        ExclusionFactTester exclusionFactTester = new ExclusionFactTester();
        ok = ok && exclusionFactTester.testImplements();
        ok = ok && exclusionFactTester.testAreEquivalent();
        ok = ok && exclusionFactTester.testIsContradictory();
        ok = ok && exclusionFactTester.testEquals();

        return ok;

    }



}