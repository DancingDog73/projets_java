package tests.systems.rules;

import systems.rules.*;
import systems.base.*;
import systems.parsers.*;


public class FormulaTester {


    private final static String[] FORMULAS_AS_STRING = {
        "pi = 22/7", "a = 1+1.2", "b=0-4", "d=0*9", "e = 4+2*1.1", "f = 4-2.2*2+3"
    };
    private final static Formula[] FORMULAS = new Formula[FORMULAS_AS_STRING.length];
    static {
        Parser parser = new Parser();
        ObjectsCreator creator = new ObjectsCreator();
        for (int i = 0; i < FORMULAS_AS_STRING.length; i++) {
            FORMULAS[i] = creator.createFormulaFrom(parser.parseFormula(FORMULAS_AS_STRING[i]));
        }
    }
    private final static String[] NAMES = {"pi", "a", "b", "d", "e", "f"};
    private final static float[] EVALUATIONS = {22/7f, 1+1.2f, 0-4, 0*9, (4+2)*1.1f, (4-2.2f)*2f+3};


    public boolean testAll () {
        boolean results = true;
        results = results && this.testGetName();
        results = results && this.testEvaluate();
        results = results && this.testEquals();
        return results;
    }


    public boolean testGetName () {
        System.out.println("[Tests] [FormulaTester::testGetName] launched");
        for (int i = 0; i < FORMULAS.length; i++) {
            if (!this.runGetNameTest(FORMULAS[i], NAMES[i])) { return false; }
        }
        System.out.println("[Tests] [FormulaTester::testGetName] passed");
        return true;
    }

    public boolean runGetNameTest (Formula formula, String expected) {
        String computed = formula.getName();
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [FormulaTester::testGetName] failed");
            System.out.println("Test failed : getName returned " + computed + " when called on " + formula + ", should have returned " + expected);
            return false;
        }
        return true;
    }


    public boolean testEvaluate () {
        System.out.println("[Tests] [FormulaTester::testEvaluate] launched");
        for (int i = 0; i < FORMULAS.length; i++) {
            if (!this.runEvaluateTest(FORMULAS[i], EVALUATIONS[i])) { return false; }
        }
        System.out.println("[Tests] [FormulaTester::testEvaluate] passed");
        return true;
    }

    public boolean runEvaluateTest (Formula formula, float expected) {
        float computed = formula.evaluate();
        if (computed != expected) {
            System.out.println("[Tests] [FormulaTester::testEvaluate] failed");
            System.out.println("Test failed : evaluate returned " + computed + " when called on " + formula + ", should have returned " + expected);
            return false;
        }
        return true;
    }


    public boolean testEquals () {
        System.out.println("[Tests] [FormulaTester::testEquals] launched");
        for (int i = 0; i < FORMULAS.length; i++) {
            if (!this.runEqualsTest(FORMULAS[i], FORMULAS[i], true)) { return false; }
        }
        System.out.println("[Tests] [FormulaTester::testEquals] passed");
        return true;
    }

    public boolean runEqualsTest (Formula first, Formula second, boolean expected) {
        boolean computed = first.equals(second);
        if (computed != expected) {
            System.out.println("[Tests] [FormulaTester::testEquals] failed");
            System.out.println("Test failed : equals returned " + computed + " when called on " + first + " and " + second + ", should have returned " + expected);
            return false;
        }
        return true;
    }
    

}