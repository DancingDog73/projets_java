package tests.systems.parsers;

import java.util.*;
import java.io.*;

import systems.parsers.*;

/**
 * 
 */
public class ParserTester {


    private final static Parser PARSER = new Parser();

    private final static String[] PARSABLE_FACTS = {
        "temperature > 50",
        "temperature2 = 80",
        "temperature_dhier < 30",
        "chaud",
        "4enfants",
        "non chaud",
        "non allergique_au_poulet",
        "profession est CEO"
    };

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
    
    private final static String[] PARSABLE_FACTS_C = {
        PARSABLE_FACTS[0] + " et " + PARSABLE_FACTS[4],
        PARSABLE_FACTS[2] + " ou " + PARSABLE_FACTS[5],
        PARSABLE_FACTS[6] + " oux " + PARSABLE_FACTS[7],
        PARSABLE_FACTS[1] + " et " + PARSABLE_FACTS[3] + " et " + PARSABLE_FACTS[5],
        PARSABLE_FACTS[1] + " ou " + PARSABLE_FACTS[3] + " ou " + PARSABLE_FACTS[5],
        PARSABLE_FACTS[1] + " oux " + PARSABLE_FACTS[3] + " oux " + PARSABLE_FACTS[5],
        PARSABLE_FACTS[0] + " et " + PARSABLE_FACTS[1] + " oux " + PARSABLE_FACTS[2] + " ou " + PARSABLE_FACTS[3],
        PARSABLE_FACTS[4] + " ou " + PARSABLE_FACTS[5] + " et " + PARSABLE_FACTS[6] + " oux " + PARSABLE_FACTS[7]
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

    private final static String[] NON_PARSABLE_FACTS = {
        "temperature >", "temperature =", "temperature =", "= 60", "la temperature =",
        "chaud froid", "profession est", "profession est CEO de Apple", ""
    };

    private final static String[] PARSABLE_RULES = new String[PARSABLE_FACTS.length*PARSABLE_FACTS_C.length*3*2];
    private final static List<Map<String,ParsedFact>> PARSED_RULES = new ArrayList<>();
    static {
        String[][] linkers = {{"Si "," alors "}, {"si "," alors "}, {"SI "," ALORS "}};
        int i = 0;
        for (String fact1 : PARSABLE_FACTS) {
            for (String fact2 : PARSABLE_FACTS_C) {
                for (String[] linker : linkers) {
                    PARSABLE_RULES[i] = linker[0] + fact1 + linker[1] + fact2;
                    PARSABLE_RULES[i+1] = linker[0] + fact2 + linker[1] + fact1;
                    i += 2;
                }
            }
        }
        for (ParsedFact fact1 : PARSED_FACTS) {
            for (ParsedFact fact2 : PARSED_FACTS_C) {
                for (String[] linker : linkers) {
                    PARSED_RULES.add(Map.of("premise", fact1, "conclusion", fact2));
                    PARSED_RULES.add(Map.of("premise", fact2, "conclusion", fact1));
                }
            }
        }
    }


    private final static String[] PARSABLE_FORMULAS = {
        "revenus = salaire * 12 + primes - impots",
        "temperature = 37",
        "revenus_parents = revenus_enfant",
        "pi = 22/7",
        "bonheur = argent+liberte",
        "E= M * C",
        "pauvrete=naissance-chance"
    };

    private final static List<List<String>> PARSED_FORMULAS = Arrays.asList(
        Arrays.asList("revenus", "salaire", "*", "12", "+", "primes", "-", "impots"),
        Arrays.asList("temperature", "37"),
        Arrays.asList("revenus_parents", "revenus_enfant"),
        Arrays.asList("pi", "22", "/", "7"),
        Arrays.asList("bonheur", "argent", "+", "liberte"),
        Arrays.asList("E", "M", "*", "C"),
        Arrays.asList("pauvrete", "naissance", "-", "chance")
    );

    private final static String[] NON_PARSABLE_FORMULAS = {
        "-h =", "temprature", "t = ", "te-", "tem+per", "= h"
    };



    public boolean testAll () {
        boolean results = true;
        results = results && this.testParseFact();
        results = results && this.testParseFacts();
        results = results && this.testParseRule();
        results = results && this.testParseFormula();
        return results;
    }


    public boolean testParseFact () {
        System.out.println("[Tests] [ParserTester::testParseFact] launched");
        for (int i = 0; i < PARSABLE_FACTS.length; i++) {
            if (!this.runParseFactTest(PARSER, PARSABLE_FACTS[i], PARSED_FACTS[i])) { return false; }
        }
        for (int i = 0; i < PARSABLE_FACTS_C.length; i++) {
            if (!this.runParseFactTest(PARSER, PARSABLE_FACTS_C[i], PARSED_FACTS_C[i])) { return false; }
        }
        System.out.println("[Tests] [ParserTester::testParseFact] passed");
        return true;
    }

    public boolean runParseFactTest (Parser parser, String fact, ParsedFact expected) {
        ParsedFact computed = parser.parseFact(fact);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [ParserTester::testParseFact] failed");
            System.out.println("Test failed : parseFact returned :\n" + computed + "\nwhen parsing " + fact + ", should have returned :\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testParseFacts () {
        System.out.println("[Tests] [ParserTester::testParseFacts] launched");
        if (!this.runParseFactsTest(PARSER, Arrays.asList(PARSABLE_FACTS), new HashSet<>(Arrays.asList(PARSED_FACTS)))) { return false; }
        if (!this.runParseFactsTest(PARSER, Arrays.asList(PARSABLE_FACTS_C), new HashSet<>(Arrays.asList(PARSED_FACTS_C)))) { return false; }
        if (!this.runParseFactsTest(PARSER, Arrays.asList(NON_PARSABLE_FACTS), new HashSet<>())) { return false; }
        String[] bothFacts = new String[PARSABLE_FACTS.length+PARSABLE_FACTS_C.length];
        System.arraycopy(PARSABLE_FACTS, 0, bothFacts, 0, PARSABLE_FACTS.length);
        System.arraycopy(PARSABLE_FACTS_C, 0, bothFacts, PARSABLE_FACTS.length, PARSABLE_FACTS_C.length);
        ParsedFact[] bothFactsParsed = new ParsedFact[PARSED_FACTS.length+PARSED_FACTS_C.length];
        System.arraycopy(PARSED_FACTS, 0, bothFactsParsed, 0, PARSED_FACTS.length);
        System.arraycopy(PARSED_FACTS_C, 0, bothFactsParsed, PARSED_FACTS.length, PARSED_FACTS_C.length);
        String[] singles = new String[PARSABLE_FACTS.length+NON_PARSABLE_FACTS.length];
        System.arraycopy(PARSABLE_FACTS, 0, singles, 0, PARSABLE_FACTS.length);
        System.arraycopy(NON_PARSABLE_FACTS, 0, singles, PARSABLE_FACTS.length, NON_PARSABLE_FACTS.length);
        String[] composites = new String[PARSABLE_FACTS_C.length+NON_PARSABLE_FACTS.length];
        System.arraycopy(PARSABLE_FACTS_C, 0, composites, 0, PARSABLE_FACTS_C.length);
        System.arraycopy(NON_PARSABLE_FACTS, 0, composites, PARSABLE_FACTS_C.length, NON_PARSABLE_FACTS.length);
        if (!this.runParseFactsTest(PARSER, Arrays.asList(bothFacts), new HashSet<>(Arrays.asList(bothFactsParsed)))) { return false; }
        if (!this.runParseFactsTest(PARSER, Arrays.asList(singles), new HashSet<>(Arrays.asList(PARSED_FACTS)))) { return false; }
        if (!this.runParseFactsTest(PARSER, Arrays.asList(composites), new HashSet<>(Arrays.asList(PARSED_FACTS_C)))) { return false; }
        System.out.println("[Tests] [ParserTester::testParseFacts] passed");
        return true;
    }

    public boolean runParseFactsTest (Parser parser, List<String> facts, Set<ParsedFact> expected) {
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        Set<ParsedFact> computed = parser.parseFacts(facts);
        System.setOut(originalOut);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [ParserTester::testParseFacts] failed");
            System.out.println("Test failed : parseFacts returned :\n" + computed + "\nwhen parsing :\n" + facts + "\nshould have returned :\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testParseRule () {
        System.out.println("[Tests] [ParserTester::testParseRule] launched");
        for (int i = 0; i < PARSABLE_RULES.length; i++) {
            if (!this.runParseRuleTest(PARSER, PARSABLE_RULES[i], PARSED_RULES.get(i))) { return false; }
        }
        System.out.println("[Tests] [ParserTester::testParseRule] passed");
        return true;
    }

    public boolean runParseRuleTest (Parser parser, String rule, Map<String,ParsedFact> expected) {
        Map<String,ParsedFact> computed = parser.parseRule(rule);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [ParserTester::testParseRule] failed");
            System.out.println("Test failed : parseRule returned :\n" + computed + "\nwhen parsing " + rule + ", should have returned :\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testParseRules () {
        System.out.println("[Tests] [ParserTester::testParseRules] launched");
        System.out.println("[Tests] [ParserTester::testParseRules] passed");
        return true;
    }

    public boolean runParseRulesTest (Parser parser, List<String> rules, Set<Map<String,ParsedFact>> expected) {
        Set<Map<String,ParsedFact>> computed = parser.parseRules(rules);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [ParserTester::testParseRules] failed");
            System.out.println("Test failed : parseRules returned :\n" + computed + "\nwhen parsing\n" + rules + "\nshould have returned :\n" + expected);
            return false;
        }
        return true;
    }


    public boolean testParseFormula () {
        System.out.println("[Tests] [ParserTester::testParseFormula] launched");
        for (int i = 0; i < PARSABLE_FORMULAS.length; i++) {
            if (!this.runParseFormulaTest(PARSER, PARSABLE_FORMULAS[i], PARSED_FORMULAS.get(i))) { return false; }
        }
        for (String formula : NON_PARSABLE_FORMULAS) {
            if (!this.runParseFormulaTest(PARSER, formula, new ArrayList<>())) { return false; }
        }
        System.out.println("[Tests] [ParserTester::testParseFormula] passed");
        return true;
    }

    public boolean runParseFormulaTest (Parser parser, String formula, List<String> expected) {
        List<String> computed = parser.parseFormula(formula);
        if (!computed.equals(expected)) {
            System.out.println("[Tests] [ParserTester::testParseFormula] failed");
            System.out.println("Test failed : parseFormula returned\n" + computed + "\nwhen parsing " + formula + ", should have returned\n" + expected);
            return false;
        }
        return true;
    }

}