package systems.parsers;

import java.util.*;
import java.util.List;

import java.util.regex.*;


/**
 * La classe Parser permet de parser les différents objets qu'on aura à traiter : les faits ({@link systems.facts.Fact}), les règles ({@link systems.rules.Rule}) et les formules ({@link systems.rules.Formula}).
 */
public class Parser {


    /**Le pattern des règles sous forme de String : {@value systems.parsers.Parser#RULE_PATTERN_STRING} */
    private static final String RULE_PATTERN_STRING = "si\\s+(.+)\\s+alors\\s+(.+)";
    /**Le pattern des règles */
    private static final Pattern RULE_PATTERN = Pattern.compile(RULE_PATTERN_STRING, Pattern.CASE_INSENSITIVE);

    /**Le pattern des faits numériques sous forme de String : {@value systems.parsers.Parser#NUMERIC_FACT_PATTERN_STRING} */
    private static final String NUMERIC_FACT_PATTERN_STRING = "^()([a-z0-9A-Z_]+)\\s*(=|<|>|<=|>=)\\s*([a-z0-9A-Z\\-_\\.]+)?\\s*$";
    /**Le pattern des faits numériques */
    private static final Pattern NUMERIC_FACT_PATTERN = Pattern.compile(NUMERIC_FACT_PATTERN_STRING, Pattern.CASE_INSENSITIVE);

    /**Le pattern des faits symboliques sous forme de String : {@value systems.parsers.Parser#NAN_FACT_PATTERN_STRING} */
    private static final String NAN_FACT_PATTERN_STRING = "^()([a-z0-9A-Z_]+)\\s+(est)\\s+([a-z0-9A-Z_]+)$";
    /**Le pattern des faits symboliques */
    private static final Pattern NAN_FACT_PATTERN = Pattern.compile(NAN_FACT_PATTERN_STRING, Pattern.CASE_INSENSITIVE);

    /**Le pattern des faits symboliques sous forme de String : {@value systems.parsers.Parser#BOOLEAN_FACT_PATTERN_STRING} */
    private static final String BOOLEAN_FACT_PATTERN_STRING = "^(non\\s+|pas\\s+)?([a-z0-9A-Z_]+)$";
    /**Le pattern des faits booléens */
    private static final Pattern BOOLEAN_FACT_PATTERN = Pattern.compile(BOOLEAN_FACT_PATTERN_STRING, Pattern.CASE_INSENSITIVE);

    /**Le pattern des faits composites sous forme de String : {@value systems.parsers.Parser#COMPOSITE_FACT_PATTERN_STRING} */
    private static final String COMPOSITE_FACT_PATTERN_STRING = "^(.+)\\s+(et|ou|oux)\\s+(.+)$";
    /**Le pattern des faits composites */
    private static final Pattern COMPOSITE_FACT_PATTERN = Pattern.compile(COMPOSITE_FACT_PATTERN_STRING, Pattern.CASE_INSENSITIVE);

    /**Le pattern des formules sous forme de String : {@value systems.parsers.Parser#FORMULA_PATTERN_STRING} et il faut que ce qui est apprès le '=' respecte {@link systems.parsers.Parser#EXPRESSION_PATTERN_STRING} */
    private static final String FORMULA_PATTERN_STRING = "([a-zA-Z0-9_]+)\\s*(=)\\s*(.+)";
    /**Le pattern des formules */
    private static final Pattern FORMULA_PATTERN = Pattern.compile(FORMULA_PATTERN_STRING, Pattern.CASE_INSENSITIVE);

    /**Le pattern des expressions sous forme de String : {@value systems.parsers.Parser#EXPRESSION_PATTERN_STRING}. Il faut que chaque membre respecte "^[a-z0-9A-Z\\-_\\.]+$" */
    private static final String EXPRESSION_PATTERN_STRING = "(.+)\\s*(\\+|-|\\*|/)\\s*(.+)";
    /**Le pattern des expressions */
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(EXPRESSION_PATTERN_STRING, Pattern.CASE_INSENSITIVE);



    /**
     * Parse les règles contenues dans la liste entrée en paramètre si elles sont parsables.
     * Une règle est parsable si elle respecte le pattern {@link systems.parsers.Parser#RULE_PATTERN_STRING} et que sa premise et sa conclusion respectent les patterns de facts, voir la doc de {@link systems.parsers.Parser#parseFact(String fact)} pour plus.
     * @param rules la liste contenant les règles à parser
     * @return un ensemble contenant les règles parsées sous forme de Map<String,ParsedFact>>
     */
    public Set<Map<String,ParsedFact>> parseRules (List<String> rules) {

        Set<Map<String,ParsedFact>> parsedRules = new HashSet<>();
        for (String rule : rules) {
            Map<String,ParsedFact> parsedRule = this.parseRule(rule);
            if (!parsedRule.isEmpty()) { parsedRules.add(parsedRule); }
        }
        return parsedRules;

    }


    /**
     * Parse les faits parsables contenues dans la liste.
     * Un fait est parsable si et seulement s'il respecte l'un des patterns suivants :<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NUMERIC_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NAN_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#BOOLEAN_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#COMPOSITE_FACT_PATTERN_STRING} (auquel cas chacun des faits qui le composent doit respecter un des patterns)
     * @param facts la liste contenant les faits à parser
     * @return un ensemble contenant les faits parsables et parsés
     */
    public Set<ParsedFact> parseFacts (List<String> facts) {

        Set<ParsedFact> parsedFacts = new HashSet<>();
        for (String fact : facts) {
            ParsedFact parsedFact = this.parseFact(fact);
            if (parsedFact != null) { parsedFacts.add(parsedFact); }
        }
        return parsedFacts;
        
    }


    /**
     * Parse la règle entrée en paramètre si elle est parsable.
     * Une règle est parsable si elle respecte le pattern {@link systems.parsers.Parser#RULE_PATTERN_STRING} et que sa premise et sa conclusion respectent les patterns de facts, voir la doc de {@link systems.parsers.Parser#parseFact(String fact)} pour plus
     * @param rule la règle à parser
     * @return la règle parsée si elle est parsable sous forme de Map<String,Parsedfact> où la clé "premise" est associée à la prémise de la règle et la clé "conclusion" à sa conclusion, null sinon
     */
    public Map<String,ParsedFact> parseRule (String rule) {

        Map<String,ParsedFact> parsedRule = new HashMap<>();
        Map<String,String> parts = this.getParts(rule);

        if (parts.containsKey("premise") && parts.containsKey("conclusion")) {
            ParsedFact premise = this.parseFact(parts.get("premise"));
            ParsedFact conclusion = this.parseFact(parts.get("conclusion"));
            if (premise != null && conclusion != null) {
                parsedRule.put("premise", premise);
                parsedRule.put("conclusion", conclusion);
                return parsedRule;
            }
        }

        System.out.println("Couldn't parse rule : " + rule);
        return parsedRule;

    }

    /**
     * Récupère la premise et la conclusion d'une règle.
     * @param rule la règle
     * @return un dictionnaire contenant la prémisse (clé "premise") et la conclusion (clé "conclusion") de la règle si elle est parsable, un dictionnaire vide sinon
     */
    private Map<String,String> getParts (String rule) {

        Map<String,String> parts = new HashMap<>();
        Matcher matcher = RULE_PATTERN.matcher(rule);

        if (matcher.find()) {
            String premise = matcher.group(1).trim();
            String conclusion = matcher.group(2).trim();
            parts.put("premise", premise);
            parts.put("conclusion", conclusion);
        }

        return parts;

    }


    /**
     * Parse le fait composite entré en paramètre.
     * @param facts le fait composite sous forme de liste
     */
    private ParsedFact parseFact (List<String> facts) {

        if (facts.size() >= 3) {
            String operator = facts.remove(1);
            ParsedFact first = this.detailFact(facts.remove(0));
            ParsedFact rest = this.parseFact(facts);
            if (first != null && rest != null) {
                return new ParsedFact(operator, first, rest);
            }
            return null;
        }

        return this.detailFact(facts.remove(0));
    }


    /**
     * Parse un fait s'il est parsable.
     * Un fait est parsable s'il respecte l'un des patterns suivants :<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NUMERIC_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NAN_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#BOOLEAN_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#COMPOSITE_FACT_PATTERN_STRING} (auquel cas chacun des faits qui le composent doit respecter un des patterns)
     * @param fact le fait à parser
     * @return le fait parsé si c'est possible, null sinon
     */
    public ParsedFact parseFact (String fact) {
        ParsedFact parsedFact = this.parseFact(this.getCompositors(fact, COMPOSITE_FACT_PATTERN));
        if (parsedFact != null) { return parsedFact; }
        System.out.println("Couldn't parse fact : " + fact);
        return null;
    }


    /**
     * Parse les formules parsables contenues dans la liste.
     * Une formule est parsable si elle respecte le pattern {@link systems.parsers.Parser#FORMULA_PATTERN_STRING}
     * @param formulas la liste contenant les formules à parser
     * @return la liste contenant les formules parsables et parsées
     */
    public Set<List<String>> parseFormulas (List<String> formulas) {
        Set<List<String>> parsedFormulas = new HashSet<>();
        for (String formula : formulas) {
            List<String> parsedFormula = this.parseFormula(formula);
            if (parsedFormula.size() >= 2) { parsedFormulas.add(parsedFormula); }
        }
        return parsedFormulas;
    }

    
    /**
     * Parse la formule entrée en paramètre si elle est parsable.
     * Une formule est parsable si elle respecte le pattern {@link systems.parsers.Parser#FORMULA_PATTERN_STRING}
     * @param formula la formule à parser
     * @return la formule parsée si elle est parsable, null sinon
     */
    public List<String> parseFormula (String formula) {
        List<String> parts = this.getCompositors(formula, FORMULA_PATTERN);
        if (parts.size() == 3 && !parts.get(0).equals("") && !parts.get(2).equals("")) {
            List<String> expression = this.parseExpression(parts.get(0));
            expression.add(0, parts.get(2));
            return expression;
        }
        return new ArrayList<>();
    }


    /**
     * Parse l'expression entrée en paramètre si elle est parsable.
     * Une expression est parsable si elle respecte le pattern {@link systems.parsers.Parser#EXPRESSION_PATTERN_STRING}
     * @param expression l'expression à parser
     * @return l'expression parsée si elle est parsable, null sinon
     */
    private List<String> parseExpression (String expression) {
        List<String> parsedExpression = this.getCompositors(expression, EXPRESSION_PATTERN);
        Collections.reverse(parsedExpression);
        return parsedExpression;
    }


    /**
     * Récupères les composants d'un objet composite.
     * @param composite l'objet composite
     * @param pattern le pattern selon lequel il est à découper
     * @return une liste contenant les différents composants, dans l'ordre inverse à celui de la chaine de caractère entrée en paramètre
     */
    private List<String> getCompositors (String composite, Pattern pattern) {

        List<String> compositors = new ArrayList<>();

        Matcher matcher = pattern.matcher(composite);

        if (matcher.find()) {
            compositors.add(matcher.group(3).trim());
            compositors.add(matcher.group(2).trim());
            compositors.addAll(this.getCompositors(matcher.group(1).trim(), pattern));
        } else {
            compositors.add(composite.trim());
        }

        return compositors;

    }


    /**
     * Parse un fait non composite.
     * @param fact le fait à parser
     * @return le fait parsé s'il est parsable, null sinon
     */
    private ParsedFact detailFact (String fact) {

        String type = this.getFactType(fact);
        String variable = this.getFactVariable(fact);
        String operator = this.getFactOperator(fact);
        String value = this.getFactValue(fact);

        if (this.isAttributeValid(type) && this.isAttributeValid(variable) && this.isAttributeValid(operator) && this.isAttributeValid(value)) {
            return new ParsedFact(type, variable, operator, value);
        }

        System.out.println("Couldn't parse fact : " + fact);
        return null;

    }


    /**
     * Indique si un attribut d'objet est valide.
     * Un attribut d'objet est valide s'il est différent de null et de la chaine vide ""
     * @param attribute l'attribut à tester
     * @return true si l'attribut est valide, false sinon
     */
    private boolean isAttributeValid (String attribute) {
        return attribute != null && (!attribute.equals(""));
    }


    /**
     * Récupère le type d'un fait non composite. Il doit respecter l'un des patterns suivants :<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NUMERIC_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NAN_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#BOOLEAN_FACT_PATTERN_STRING}<br>
     * @param fact le fait dont on cherche le type
     * @return le type du fait s'il respecte l'un des patterns ci-dessus, "" sinon
     */
    private String getFactType (String fact) {

        Matcher numericMatcher = NUMERIC_FACT_PATTERN.matcher(fact);
        Matcher nanMatcher = NAN_FACT_PATTERN.matcher(fact);
        Matcher booleanMatcher = BOOLEAN_FACT_PATTERN.matcher(fact);

        if (numericMatcher.find()) {
            return "numeric";
        }
        if (nanMatcher.find()) {
            return "NaN";
        }
        if (booleanMatcher.find()) {
            return "boolean";
        }

        return "";

    }


    /**
     * Récupère l'opérateur d'un fait non composite. Il doit respecter l'un des patterns suivants :<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NUMERIC_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NAN_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#BOOLEAN_FACT_PATTERN_STRING}<br>
     * @param fact le fait dont on cherche l'opérateur
     * @return l'opérateur du fait s'il respecte l'un des patterns ci-dessus, "" sinon
     */
    private String getFactOperator (String fact) {

        String factType = this.getFactType(fact);

        if (factType == "numeric") {
            Matcher matcher = NUMERIC_FACT_PATTERN.matcher(fact);
            matcher.find();
            return matcher.group(3).trim();
        }
        if (factType == "NaN") {
            return "est";
        }
        if (factType == "boolean") {
            return "=";
        }

        return "";

    }


    /**
     * Récupère la variable d'un fait non composite. Il doit respecter l'un des patterns suivants :<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NUMERIC_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NAN_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#BOOLEAN_FACT_PATTERN_STRING}<br>
     * @param fact le fait dont on cherche la variable
     * @return la variable du fait s'il respecte l'un des patterns ci-dessus, "" sinon
     */
    private String getFactVariable (String fact) {

        String factType = this.getFactType(fact);
        Matcher matcher;

        if (factType == "numeric") {
            matcher = NUMERIC_FACT_PATTERN.matcher(fact);
        } else if (factType == "NaN") {
            matcher = NAN_FACT_PATTERN.matcher(fact);
        } else if (factType == "boolean") {
            matcher = BOOLEAN_FACT_PATTERN.matcher(fact);
        } else {
            return "";
        }

        matcher.find();
        return matcher.group(2).trim();

    }


    /**
     * Récupère la valeur d'un fait non composite. Il doit respecter l'un des patterns suivants :<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NUMERIC_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#NAN_FACT_PATTERN_STRING}<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- {@link systems.parsers.Parser#BOOLEAN_FACT_PATTERN_STRING}<br>
     * @param fact le fait dont on cherche la valeur
     * @return la variable du fait s'il respecte l'un des patterns ci-dessus, "" sinon
     */
    private String getFactValue (String fact) {

        String factType = this.getFactType(fact);
        Matcher matcher;

        if (factType == "numeric") {
            matcher = NUMERIC_FACT_PATTERN.matcher(fact);
        } else if (factType == "NaN") {
            matcher = NAN_FACT_PATTERN.matcher(fact);
        } else if (factType == "boolean") {
            matcher = BOOLEAN_FACT_PATTERN.matcher(fact);
            matcher.find();
            return matcher.group(1) == null ? "true" : "false";
        } else {
            return "";
        }

        matcher.find();
        return matcher.group(4);

    }


}