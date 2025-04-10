package tests.systems.rules;

import systems.rules.*;
import systems.facts.*;
import systems.parsers.*;
import systems.base.*;


public class RuleTester {


    private final static String[][] FACTS = {
        {"temperature > 50", "chaud"},
        {"riche", "heureux"},
        {"pauvre et paresseux", "sort est scelle"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"},
        {"temperature > 50", "chaud"}
    };

    private final static Fact[] PREMISES = new Fact[FACTS.length];
    private final static Fact[] CONCLUSIONS = new Fact[FACTS.length];
    private final static Rule[] RULES = new Rule[FACTS.length];
    static {
        Parser parser = new Parser();
        ObjectsCreator creator = new ObjectsCreator();
        for (int i = 0; i < FACTS.length; i++) {
            Fact premise = creator.createFactFrom(parser.parseFact(FACTS[i][0]));
            Fact conclusion = creator.createFactFrom(parser.parseFact(FACTS[i][1]));
            PREMISES[i] = premise;
            CONCLUSIONS[i] = conclusion;
            RULES[i] = new Rule(premise, conclusion);
        }
    }


    public boolean testAll () {
        boolean results = true;
        results = results && this.testGetPremise();
        results = results && this.testGetConclusion();
        results = results && this.testEquals();
        return results;
    }


    public boolean testGetPremise () {
        System.out.println("[Tests] [RuleTester::testGetPremise] launched");
        for (int i = 0; i < PREMISES.length; i++) {
            if (!this.runGetPremiseTest(RULES[i], PREMISES[i])) { return false; }
        }
        System.out.println("[Tests] [RuleTester::testGetPremise] passed");
        return true;
    }

    public boolean runGetPremiseTest (Rule rule, Fact expected) {
        Fact computed = rule.getPremise();
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [RuleTester::testGetPremise] failed");
            System.out.println("Test failed : getPremise returned\n" + computed + "\nwhen called on\n" + rule + "\nshould have returned\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testGetConclusion () {
        System.out.println("[Tests] [RuleTester::testGetConclusion] launched");
        for (int i = 0; i < CONCLUSIONS.length; i++) {
            if (!this.runGetConclusionTest(RULES[i], CONCLUSIONS[i])) { return false; }
        }
        System.out.println("[Tests] [RuleTester::testGetConclusion] passed");
        return true;
    }

    public boolean runGetConclusionTest (Rule rule, Fact expected) {
        Fact computed = rule.getConclusion();
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [RuleTester::testGetConclusion] failed");
            System.out.println("Test failed : getConclusion returned\n" + computed + "\nwhen called on\n" + rule + "\nshould have returned\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testEquals () {
        System.out.println("[Tests] [RuleTester::testEquals] launched");
        for (int i = 0; i < RULES.length; i++) {
            if (!this.runEqualsTest(RULES[i], RULES[i], true)) { return false; }
        }
        System.out.println("[Tests] [RuleTester::testEquals] passed");
        return true;
    }

    public boolean runEqualsTest (Rule first, Rule second, boolean expected) {
        boolean computed = first.equals(second);
        if (computed != expected) {
            System.out.println("[Tests] [RuleTester::testEquals] failed");
            System.out.println("Test failed : equals returned " + computed + " when called on\n" + first + "\nand\n" + second + "\nshould have returned " + expected);
            return false;
        }
        return true;
    }

}