package tests.systems.base;

import java.util.*;

import systems.base.*;
import systems.facts.*;
import systems.rules.*;


public class KnowledgeBaseTester {


    private final static KnowledgeBase FIRST_BASE = new KnowledgeBase("../../../res/testsbases/TestBase1.txt");
    private final static KnowledgeBase SECOND_BASE = new KnowledgeBase("../../../res/testsbases/TestBase2.txt");
    private final static KnowledgeBase THIRD_BASE = new KnowledgeBase("../../../res/testsbases/TestBase3.txt");
    private final static KnowledgeBase FOURTH_BASE = new KnowledgeBase("../../../res/testsbases/TestBase4.txt");
    private final static KnowledgeBase FITH_BASE = new KnowledgeBase("../../../res/testsbases/TestBase5.txt");
    private final static KnowledgeBase SIXTH_BASE = new KnowledgeBase("../../../res/testsbases/TestBase6.txt");
    private final static KnowledgeBase SEVENTH_BASE = new KnowledgeBase("../../../res/testsbases/TestBase7.txt");


    public boolean testAll () {
        boolean results = true;
        results = results && this.testIsFactKnown();
        results = results && this.testIsFormulaApplicable();
        results = results && this.testUpdate();
        return results;
    }


    public boolean testIsFactKnown () {
        System.out.println("[Tests] [KnowledgeBaseTester::testIsFactKnown] launched");
        for (Fact fact : FIRST_BASE.getFactsBase()) {
            if (!this.runIsFactKnownTest(FIRST_BASE, fact, true)) { return false; }
            if (!this.runIsFactKnownTest(SECOND_BASE, fact, false)) { return false; }
        }
        for (Fact fact : SECOND_BASE.getFactsBase()) {
            if (!this.runIsFactKnownTest(FIRST_BASE, fact, false)) { return false; }
            if (!this.runIsFactKnownTest(SECOND_BASE, fact, true)) { return false; }
        }
        for (Fact fact : THIRD_BASE.getFactsBase()) {
            if (!this.runIsFactKnownTest(FIRST_BASE, fact, true)) { return false; }
        }
        for (Fact fact : FOURTH_BASE.getFactsBase()) {
            if (!this.runIsFactKnownTest(FIRST_BASE, fact, false)) { return false; }
        }
        System.out.println("[Tests] [KnowledgeBaseTester::testIsFactKnown] passed");
        return true;
    }

    public boolean runIsFactKnownTest (KnowledgeBase base, Fact fact, boolean expected) {
        boolean computed = base.isFactKnown(fact);
        if (computed != expected) {
            System.out.println("[Tests] [KnowledgeBaseTester::testIsFactKnown] failed");
            System.out.println("Test failed : isFactKnown returned " + computed + " for \nFact : " + fact + "\nFactsBase " + base.getFactsBase());
            return false;
        }
        return true;
    }


    public boolean testIsFormulaApplicable () {
        System.out.println("[Tests] [KnowledgeBaseTester::testIsFormulaApplicable] launched");
        for (Formula formula : FIRST_BASE.getFormulas()) {
            if (!this.runIsFormulaApplicableTest(FIRST_BASE, formula, true)) { return false; }
        }
        for (Formula formula : SECOND_BASE.getFormulas()) {
            if (!this.runIsFormulaApplicableTest(SECOND_BASE, formula, false)) { return false; }
        }
        System.out.println("[Tests] [KnowledgeBaseTester::testIsFormulaApplicable] passed");
        return true;
    }

    public boolean runIsFormulaApplicableTest (KnowledgeBase base, Formula formula, boolean expected) {
        boolean computed = base.isFormulaApplicable(formula);
        if (computed != expected) {
            System.out.println("[Tests] [KnowledgeBaseTester::testIsFormulaApplicable] failed");
            System.out.println("Test failed : isFormulaApplicable returned " + computed + " when called on the following formula\n" + formula + "\nshould have returned " + expected);
            return false;
        }
        return true;
    }


    public boolean testUpdate () {
        System.out.println("[Tests] [KnowledgeBaseTester::testUpdate] launched");
        for (Fact fact : SIXTH_BASE.getFactsBase()) {
            FITH_BASE.update(fact);
        }
        for (Fact fact : SEVENTH_BASE.getFactsBase()) {
            if (!this.runUpdateTest(FITH_BASE, SIXTH_BASE, fact, true)) { return false; }
        }
        System.out.println("[Tests] [KnowledgeBaseTester::testUpdate] passed");
        return true;
    }

    public boolean runUpdateTest (KnowledgeBase base, KnowledgeBase updater, Fact fact, boolean expected) {
        boolean computed = base.isFactKnown(fact);
        if (computed != expected) {
            System.out.println("[Tests] [KnowledgeBaseTester::testUpdate] failed");
            System.out.println("Test failed : the following base :\nFacts " + base.getFactsBase() + "\nFormulas " + base.getFormulas() + "\nhas been updated with these facts :\n" + updater.getFactsBase() + "\nThe updated base should have contained this fact : " + fact + ", it does not.");
            return false;
        }
        return true;
    }

}