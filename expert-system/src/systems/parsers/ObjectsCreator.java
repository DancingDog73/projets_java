package systems.parsers;


import java.util.*;

import systems.facts.*;
import systems.rules.*;


/**
 * La classe ObjectsCreator permet de créer les différents objets manipulés - les faits ({@link systems.facts.Fact}), les règles ({@link systems.rules.Rule}) et les formules ({@link systems.rules.Formula})
 */
public class ObjectsCreator {


    /**
     * Créé un fait à partir d'un fait parsé.
     * @param parsedFact le fait parsé à partir duquel on veut créer le fait
     * @return le fait créé
     */
    public Fact createFactFrom (ParsedFact parsedFact) {

        if (parsedFact.isComposite()) {
            return this.createCompositeFact(parsedFact);
        }

        String factType = parsedFact.getType();

        if (factType == "numeric") {
            try {
                return new NumericFact(new Variable<Float>(parsedFact.getVariable(), Float.parseFloat(parsedFact.getValue())), parsedFact.getOperator());
            } catch (Exception e) {
                return new NumericFact(new Variable<Float>(parsedFact.getVariable(), new Variable<Float>(parsedFact.getValue())), parsedFact.getOperator());
            }
        } else if (factType == "NaN") {
            if (parsedFact.getValue().charAt(0) == '_') {
                return new NanFact(new Variable<String>(parsedFact.getVariable(), new Variable<String>(parsedFact.getValue().substring(1))));
            }
            return new NanFact(new Variable<String>(
                parsedFact.getVariable(),
                parsedFact.getValue()
            ));
        } else {
            return new BooleanFact(new Variable<Boolean>(
                parsedFact.getVariable(),
                parsedFact.getValue() == "true" ? true : false
            ));
        }

    }


    /**
     * Créé un fait à partir d'un fait parsé composite.
     * @param parsedFact le fait parsé à partir duquel on veut créer le fait
     * @return le fait créé
     */
    public Fact createCompositeFact (ParsedFact parsedFact) {
        String compositor = parsedFact.getCompositor();
        if (compositor.equals("et")) {
            return new ConjunctionFact(
                this.createFactFrom(parsedFact.getOneParsedFact()),
                this.createFactFrom(parsedFact.getAnotherParsedFact())
            );
        } else if (compositor.equals("ou")) {
            return new DisjunctionFact(
                this.createFactFrom(parsedFact.getOneParsedFact()),
                this.createFactFrom(parsedFact.getAnotherParsedFact())
            );
        } else {
            return new ExclusionFact(
                this.createFactFrom(parsedFact.getOneParsedFact()),
                this.createFactFrom(parsedFact.getAnotherParsedFact())
            );
        }
    }


    /**
     * Créé un ensemble de faits à partir d'un ensemble de faits parsés.
     * @param parsedFacts l'ensemble de faits parsés à partir duquel on veut céer un ensemble de faits
     * @return les faits créés
     */
    public Set<Fact> createFactsFrom (Set<ParsedFact> parsedFacts) {

        Set<Fact> facts = new HashSet<>();

        for (ParsedFact parsedFact : parsedFacts) {
            facts.add(this.createFactFrom(parsedFact));
        }

        return facts;

    }


    /**
     * Créé une règle à partir d'une règle parsée. Elle doit contenir les clés "premise" et "conclusion".
     * @param ruleAsDico la règle parsée à partir de laquelle on veut créer la règle les clés "premise" et "conclusion"
     * @throws java.lang.NullPointerException si la règle ne contient pas 
     * @return la règle créée
     */
    public Rule createRuleFrom (Map<String,ParsedFact> ruleAsDico) {

        return new Rule(
            this.createFactFrom(ruleAsDico.get("premise")),
            this.createFactFrom(ruleAsDico.get("conclusion"))
        );

    }


    /**
     * Créé un ensemble de règles à partir d'un ensemble de règles parsées. Chacune des règles parsées doit ontenir les clés "premise" et "conclusion".
     * @param rulesAsDico l'ensemble de règles parsées à partir duquel on veut céer un ensemble de règles
     * @return les règles créées
     */
    public Set<Rule> createRulesFrom (Set<Map<String,ParsedFact>> rulesAsDico) {

        Set<Rule> rules = new HashSet<>();

        for (Map<String,ParsedFact> ruleAsDico : rulesAsDico) {
            rules.add(this.createRuleFrom(ruleAsDico));
        }

        return rules;

    }


    /**
     * Créé une variable paramétrée avec Float.
     * @param name le nom de la variable
     * @param value la valeur de la variable
     * @return la variable crée
     */
    private Variable<Float> createVariable (String name, String value) {
        try {
            return new Variable<Float>(name, Float.parseFloat(value));
        } catch (Exception e) {
            return new Variable<Float>(value);
        }
    }

    /**
     * Créé une variable paramétrée avec Float.
     * @param value la valeur de la variable
     * @return la variable crée
     */
    private Variable<Float> createVariable(String value) { return this.createVariable("", value); }

    /**
     * Crée une expression à partir d'une expression parsée.
     * @param expressionAsList l'expression parsée à partir de laquelle on veut créer une expression
     * @throws java.lang.ArrayIndexOutOfBoundsException si la liste entrée en paramètre ne contient pas au moins un élément
     * @return l'expression créée
     */
    public Expression createExpressionFrom (List<String> expressionAsList) {
        if (expressionAsList.size() >= 3) {
            String lastMember = expressionAsList.remove(expressionAsList.size()-1);
            String lastOperator = expressionAsList.remove(expressionAsList.size()-1);
            return new Expression(this.createExpressionFrom(expressionAsList), lastOperator, new Expression(this.createVariable(lastMember)));
        }
        return new Expression(this.createVariable(expressionAsList.remove(0)));
    }


    /**
     * Crée une formule à partir d'une formule parsée.
     * @param formulaAsList la formule parsée à partir de laquelle on veut créer une formule
     * @throws java.lang.ArrayIndexOutOfBoundsException si la liste entrée en paramètre ne contient pas au moins deux éléments
     * @return la formule créée
     */
    public Formula createFormulaFrom (List<String> formulaAsList) {
        return new Formula(formulaAsList.remove(0), this.createExpressionFrom(formulaAsList));
    }

    /**
     * Crée un ensemble de formules à partir d'un ensemble de formules parsées.
     * @param formulasAsList l'ensemble de formules parsées à partir duquel on veut créer un ensemble de formules
     * @throws java.lang.ArrayIndexOutOfBoundsException si une des listes de l'ensemble entré en paramètre contient moins de deux éléments
     * @return un ensemble contenant les formules créées
     */
    public Set<Formula> createFormulasFrom (Set<List<String>> formulasAsList) {
        Set<Formula> formulas = new HashSet<>();
        for (List<String> formulaAsList : formulasAsList) {
            formulas.add(this.createFormulaFrom(formulaAsList));
        }
        return formulas;
    }


}