package tests.systems.parsers;


import java.util.*;

import systems.facts.*;
import systems.rules.*;
import systems.base.*;
import systems.parsers.*;



public class ObjectsCreatorTester {

    private final static ObjectsCreator GOD = new ObjectsCreator();


    private final static ParsedFact[] PARSED_FACTS = {
        new ParsedFact("numeric", "temperature", ">", "50"),
        new ParsedFact("numeric", "temperature2", "=", "80"),
        new ParsedFact("numeric", "temperature_dhier", "<", "30"),
        new ParsedFact("boolean", "chaud", "=", "true"),
        new ParsedFact("boolean", "4enfants", "=", "true"),
        new ParsedFact("boolean", "chaud", "=", "false"),
        new ParsedFact("boolean", "allergique_au_poulet", "=", "false"),
        new ParsedFact("NaN", "profession", "est", "CEO")
    };

    private final static Fact[] FACTS = {
        new NumericFact(new Variable<Float>("temperature", 50f), ">"),
        new NumericFact(new Variable<Float>("temperature2", 80f), "="),
        new NumericFact(new Variable<Float>("temperature_dhier", 30f), "<"),
        new BooleanFact(new Variable<Boolean>("chaud", true)),
        new BooleanFact(new Variable<Boolean>("4enfants", true)),
        new BooleanFact(new Variable<Boolean>("chaud", false)),
        new BooleanFact(new Variable<Boolean>("allergique_au_poulet", false)),
        new NanFact(new Variable<String>("profession", "CEO"))
    };


    private final static ParsedFact[] PARSED_FACTS_C = {
        new ParsedFact("et", PARSED_FACTS[4], PARSED_FACTS[0]),
        new ParsedFact("ou", PARSED_FACTS[5], PARSED_FACTS[2]),
        new ParsedFact("oux", PARSED_FACTS[7], PARSED_FACTS[6]),
        new ParsedFact("et", PARSED_FACTS[5], new ParsedFact("et", PARSED_FACTS[3], PARSED_FACTS[1])),
        new ParsedFact("ou", PARSED_FACTS[5], new ParsedFact("ou", PARSED_FACTS[3], PARSED_FACTS[1])),
        new ParsedFact("oux", PARSED_FACTS[5], new ParsedFact("oux", PARSED_FACTS[3], PARSED_FACTS[1])),
        new ParsedFact("ou", PARSED_FACTS[3], new ParsedFact("oux", PARSED_FACTS[2], new ParsedFact("et", PARSED_FACTS[1], PARSED_FACTS[0]))),
        new ParsedFact("oux", PARSED_FACTS[7], new ParsedFact("et", PARSED_FACTS[6], new ParsedFact("ou", PARSED_FACTS[5], PARSED_FACTS[4])))
    };

    private final static Fact[] FACTS_C = {
        new ConjunctionFact(FACTS[4], FACTS[0]),
        new DisjunctionFact(FACTS[5], FACTS[2]),
        new ExclusionFact(FACTS[7], FACTS[6]),
        new ConjunctionFact(FACTS[5], new ConjunctionFact(FACTS[3], FACTS[1])),
        new DisjunctionFact(FACTS[5], new DisjunctionFact(FACTS[3], FACTS[1])),
        new ExclusionFact(FACTS[5], new ExclusionFact(FACTS[3], FACTS[1])),
        new DisjunctionFact(FACTS[3], new ExclusionFact(FACTS[2], new ConjunctionFact(FACTS[1], FACTS[0]))),
        new ExclusionFact(FACTS[7], new ConjunctionFact(FACTS[6], new DisjunctionFact(FACTS[5], FACTS[4])))
    };

    private final static String[] NON_PARSABLE_FACTS = {
        "temperature >", "temperature =", "temperature =", "= 60", "la temperature =",
        "chaud froid", "profession est", "profession est CEO de Apple", ""
    };


    private final static List<Map<String,ParsedFact>> PARSED_RULES = new ArrayList<>();
    private final static List<Rule> RULES = new ArrayList<>();
    static {
        for (ParsedFact fact1 : PARSED_FACTS) {
            for (ParsedFact fact2 : PARSED_FACTS_C) {
                PARSED_RULES.add(Map.of("premise", fact1, "conclusion", fact2));
                PARSED_RULES.add(Map.of("premise", fact2, "conclusion", fact1));
                PARSED_RULES.add(Map.of("premise", fact1, "conclusion", fact1));
                PARSED_RULES.add(Map.of("premise", fact2, "conclusion", fact2));
                RULES.add(new Rule(GOD.createFactFrom(fact1), GOD.createFactFrom(fact2)));
                RULES.add(new Rule(GOD.createFactFrom(fact2), GOD.createFactFrom(fact1)));
                RULES.add(new Rule(GOD.createFactFrom(fact1), GOD.createFactFrom(fact1)));
                RULES.add(new Rule(GOD.createFactFrom(fact2), GOD.createFactFrom(fact2)));
            }
        }
    }


    private final static List<List<String>> PARSED_FORMULAS = Arrays.asList(
        new ArrayList<>(Arrays.asList("revenus", "salaire", "*", "12", "+", "primes", "-", "impots")),
        new ArrayList<>(Arrays.asList("temperature", "37")),
        new ArrayList<>(Arrays.asList("revenus_parents", "revenus_enfant")),
        new ArrayList<>(Arrays.asList("pi", "22", "/", "7")),
        new ArrayList<>(Arrays.asList("bonheur", "argent", "+", "liberte")),
        new ArrayList<>(Arrays.asList("E", "M", "*", "C")),
        new ArrayList<>(Arrays.asList("pauvrete", "naissance", "-", "chance"))
    );

    private final static Formula[] FORMULAS = {
        new Formula("revenus", new Expression(new Expression(new Expression(new Expression(new Variable<Float>("salaire")), "*", new Expression(new Variable<Float>("", 12f))), "+", new Expression(new Variable<Float>("primes"))), "-", new Expression(new Variable<Float>("impots")))),
        new Formula("temperature", new Expression(new Variable<Float>("", 37f))),
        new Formula("revenus_parents", new Expression(new Variable<Float>("revenus_enfant"))),
        new Formula("pi", new Expression(new Expression(new Variable<Float>("", 22f)), "/", new Expression(new Variable<Float>("", 7f)))),
        new Formula("bonheur", new Expression(new Expression(new Variable<Float>("argent")), "+", new Expression(new Variable<Float>("liberte")))),
        new Formula("E", new Expression(new Expression(new Variable<Float>("M")), "*", new Expression(new Variable<Float>("C")))),
        new Formula("pauvrete", new Expression(new Expression(new Variable<Float>("naissance")), "-", new Expression(new Variable<Float>("chance"))))
    };


    public boolean testAll () {
        boolean results = true;
        results = results && this.testCreateFact();
        results = results && this.testCreateFacts();
        results = results && this.testCreateRule();
        results = results && this.testCreateRules();
        results = results && this.testCreateFormula();
        results = results && this.testCreateFormulas();
        return results;
    }


    public boolean testCreateFact () {
        System.out.println("[Tests] [GodTester::testCreateFact] launched");
        for (int i = 0; i < PARSED_FACTS.length; i++) {
            if (!this.runCreateFactTest(GOD, PARSED_FACTS[i], FACTS[i])) { return false; }
        }
        for (int i = 0; i < PARSED_FACTS_C.length; i++) {
            if (!this.runCreateFactTest(GOD, PARSED_FACTS_C[i], FACTS_C[i])) { return false; }
        }
        System.out.println("[Tests] [GodTester::testCreateFact] passed");
        return true;
    }

    public boolean runCreateFactTest (ObjectsCreator god, ParsedFact fact, Fact expected) {
        Fact computed = god.createFactFrom(fact);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [GodTester::testCreateFact] failed");
            System.out.println("Test failed : createFactFrom returned\n" + computed + "\nwhen creating from\n" + fact + "\nshould have returned\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testCreateFacts () {
        System.out.println("[Tests] [GodTester::testCreateFacts] launched");
        if (!this.runCreateFactsTest(GOD, new HashSet<>(), new HashSet<>())) { return false; }
        if (!this.runCreateFactsTest(GOD, new HashSet<>(Arrays.asList(PARSED_FACTS)), new HashSet<>(Arrays.asList(FACTS)))) { return false; }
        if (!this.runCreateFactsTest(GOD, new HashSet<>(Arrays.asList(PARSED_FACTS_C)), new HashSet<>(Arrays.asList(FACTS_C)))) { return false; }
        List<ParsedFact> parsedFacts = new ArrayList<>(Arrays.asList(PARSED_FACTS_C));
        parsedFacts.addAll(new ArrayList<>(Arrays.asList(PARSED_FACTS)));
        List<Fact> facts = new ArrayList<>(Arrays.asList(FACTS_C));
        facts.addAll(new ArrayList<>(Arrays.asList(FACTS)));
        if (!this.runCreateFactsTest(GOD, new HashSet<>(parsedFacts), new HashSet<>(facts))) { return false; }
        System.out.println("[Tests] [GodTester::testCreateFacts] passed");
        return true;
    }

    public boolean runCreateFactsTest (ObjectsCreator god, Set<ParsedFact> facts, Set<Fact> expected) {
        Set<Fact> computed = god.createFactsFrom(facts);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [GodTester::testCreateFacts] failed");
            System.out.println("Test failed : createFactsFrom returned\n" + computed + "\nwhen creating from\n" + facts + "\nshould have returned\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testCreateRule () {
        System.out.println("[Tests] [GodTester::testCreateRule] launched");
        for (int i = 0; i < PARSED_RULES.size(); i++) {
            if (!this.runCreateRuleTest(GOD, PARSED_RULES.get(i), RULES.get(i))) { return false; }
        }
        System.out.println("[Tests] [GodTester::testCreateRule] passed");
        return true;
    }

    public boolean runCreateRuleTest (ObjectsCreator god, Map<String,ParsedFact> rule, Rule expected) {
        Rule computed = god.createRuleFrom(rule);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [GodTester::testCreateRule] failed");
            System.out.println("Test failed : createRuleFrom returned\n" + computed + "\nwhen creating from\n" + rule + "\nshould have returned\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testCreateRules () {
        System.out.println("[Tests] [GodTester::testCreateRules] launched");
        if (!this.runCreateRulesTest(GOD, new HashSet<>(), new HashSet<>())) { return false; }
        if (!this.runCreateRulesTest(GOD, new HashSet<>(PARSED_RULES), new HashSet<>(RULES))) { return false; }
        System.out.println("[Tests] [GodTester::testCreateRules] passed");
        return true;
    }

    public boolean runCreateRulesTest (ObjectsCreator god, Set<Map<String,ParsedFact>> rules, Set<Rule> expected) {
        Set<Rule> computed = god.createRulesFrom(rules);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [GodTester::testCreateRules] failed");
            System.out.println("Test failed : createRulesFrom returned\n" + computed + "\nwhen creating from\n" + rules + "\nshould have returned\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testCreateFormula () {
        System.out.println("[Tests] [GodTester::testCreateFormula] launched");
        for (int i = 0; i < PARSED_FORMULAS.size(); i++) {
            if (!this.runCreateFormulaTest(GOD, new ArrayList<>(new ArrayList<>(PARSED_FORMULAS).get(i)), FORMULAS[i])) { return false; }
        }
        System.out.println("[Tests] [GodTester::testCreateFormula] passed");
        return true;
    }

    public boolean runCreateFormulaTest (ObjectsCreator god, List<String> formula, Formula expected) {
        List<String> formulaC = new ArrayList<>(formula);
        Formula computed = god.createFormulaFrom(formula);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [GodTester::testCreateFormula] failed");
            System.out.println("Test failed : createFormulaFrom returned\n" + computed + "\nwhen creating from\n" + formulaC + "\nshould have returned\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testCreateFormulas () {
        System.out.println("[Tests] [GodTester::testCreateFormulas] launched");
        if (!this.runCreateFormulasTest(GOD, new HashSet<>(), new HashSet<>())) { return false; }
        if (!this.runCreateFormulasTest(GOD, new HashSet<>(PARSED_FORMULAS), new HashSet<>(Arrays.asList(FORMULAS)))) { return false; }
        System.out.println("[Tests] [GodTester::testCreateFormulas] passed");
        return true;
    }

    public boolean runCreateFormulasTest (ObjectsCreator god, Set<List<String>> formulas, Set<Formula> expected) {
        Set<Formula> computed = god.createFormulasFrom(formulas);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [GodTester::testCreateFormulas] failed");
            System.out.println("Test failed : createFormulasFrom returned\n" + computed + "\nwhen creating from\n" + formulas + "\nshould have returned\n" + expected);
            return false;
        }
        return true;
    }


}