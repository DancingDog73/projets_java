package tests.systems.facts;

import java.util.*;

import systems.facts.*;


public class VariableTester {


    private final static List<Variable<?>> SET_VARIABLES = Arrays.asList(
        new Variable<Float>("", 0f),
        new Variable<Float>("v", 0f),
        new Variable<String>("v", "0f"),
        new Variable<String>("", "0f"),
        new Variable<Boolean>("v", true),
        new Variable<Boolean>("", false)
    );

    private final static List<Variable<?>> UNSET_VARIABLES = Arrays.asList(
        new Variable<Float>("unset"),
        new Variable<Float>("v", new Variable<Float>("f")),
        new Variable<String>("v"),
        new Variable<String>("", new Variable<String>("")),
        new Variable<Boolean>("v"),
        new Variable<Boolean>("", new Variable<Boolean>("ok"))
    );

    private final static List<Variable<?>> VARIABLES_E = Arrays.asList(
        new Variable<Float>("v", 10f),
        new Variable<String>("v", "10f"),
        new Variable<Boolean>("v", true)
    );

    private final static List<Variable<?>> VARIABLES_F = Arrays.asList(
        new Variable<Float>("v", new Variable<Float>("", 10f)),
        new Variable<String>("v", new Variable<String>("", "10f")),
        new Variable<Boolean>("v", new Variable<Boolean>("", true))
    );


    public boolean testAll () {
        boolean results = true;
        results = results && this.testIsSet();
        results = results && this.testEquals();
        return results;
    }


    public boolean testIsSet () {
        System.out.println("[Tests] [VariableTester::testIsSet] launched");
        for (Variable<?> variable : SET_VARIABLES) {
            if (!this.runIsSetTest(variable, true)) { return false; }
        }
        for (Variable<?> variable : UNSET_VARIABLES) {
            if (!this.runIsSetTest(variable, false)) { return false; }
        }
        System.out.println("[Tests] [VariableTester::testIsSet] passed");
        return true;
    }

    public boolean runIsSetTest (Variable variable, boolean expected) {
        boolean computed = variable.isSet();
        if (computed != expected) {
            System.out.println("[Tests] [VariableTester::testIsSet] failed");
            System.out.println("Test failed : isSet returned " + computed + " when called on this varible\n" + variable + "\nshould have returned " + expected);
            return false;
        }
        return true;
    }


    public boolean testEquals () {
        System.out.println("[Tests] [VariableTester::testEquals] launched");
        for (Variable<?> variable : SET_VARIABLES) {
            if (!this.runEqualsTest(variable, variable, true)) { return false; }
        }
        for (Variable<?> variable : UNSET_VARIABLES) {
            if (!this.runEqualsTest(variable, variable, true)) { return false; }
        }
        for (int i = 0; i < VARIABLES_E.size(); i++) {
            if (!this.runEqualsTest(VARIABLES_E.get(i), VARIABLES_F.get(i), true)) { return false; }
        }
        System.out.println("[Tests] [VariableTester::testEquals] passed");
        return true;
    }

    public boolean runEqualsTest (Variable first, Variable second, boolean expected) {
        boolean computed = first.equals(second);
        if (computed != expected) {
            System.out.println("[Tests] [VariableTester::testEquals] failed");
            System.out.println("Test failed : equals returned " + computed + " when comparing these two variables\n" + first + "\n" + second + "\nshould have returned " + expected);
            return false;
        }
        return true;
    }

}