package systems.base;


import java.util.*;

import systems.facts.*;
import systems.rules.*;
import systems.parsers.*;



/**
 * La classe KnowledgeBase représente une base de connaissances, constituée d'une base de règles, d'une base de faits et d'un ensemble de formules.
 */
public class KnowledgeBase {

    /**La base de faits */
    private Set<Fact> factsBase;
    /**La base de règles */
    private Set<Rule> rulesBase;
    /**L'ensemble de formules */
    private Set<Formula> formulas;
    
    /**L'ensemble de règles dont la premise est un fait possédant une variable pour laquelle {@link systems.facts.Variable#isSet()} retourne false */
    private Set<Rule> unsetRules;
    /**L'ensemble de faits possédant une variable pour laquelle {@link systems.facts.Variable#isSet()} retourne false */
    private Set<Fact> unsetFacts;

    /**Un parseur pour parser de nouveaux objets */
    private Parser parser = new Parser();
    /**Un créateur pour créer de nouveaux objets */
    private ObjectsCreator creator = new ObjectsCreator();


    /**
     * Construit une nouvelle base de connaissances à partir d'un fichier de configuration.
     * Le fichier de configuration doit se présenter de la sorte :<br><br>
     * 
     *      &nbsp;&nbsp;&nbsp;&nbsp;Facts<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fait1<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fait2<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fai...<br><br>
     *      
     *      &nbsp;&nbsp;&nbsp;&nbsp;Formulas<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Formule1<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Formule2<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Formu...<br><br>
     *      
     *      &nbsp;&nbsp;&nbsp;&nbsp;Known Rules<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Règle1<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Règle1<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Règl...<br>
     *          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>ligne vide</code><br>
     * 
     * @param baseFile le fichier de configuration
     */
    public KnowledgeBase (String baseFile) {

        Reader fileReader = new Reader(baseFile);

        this.factsBase = creator.createFactsFrom(parser.parseFacts(fileReader.getAllFacts()));
        this.rulesBase = creator.createRulesFrom(parser.parseRules(fileReader.getAllRules()));
        this.formulas = creator.createFormulasFrom(parser.parseFormulas(fileReader.getAllFormulas()));
        this.unsetRules = new HashSet<>();
        this.unsetFacts = new HashSet<>();
        this.findUnsets();
        this.tryFormulasEvaluation();

    }

    /**
     * Construit une nouvelle base de connaissance avec les bases données en paramètre.
     * @param factsBase la base faits
     * @param rulesBase la base de règles
     * @param formulas l'ensemble de formules
     */
    public KnowledgeBase(Set<Fact> factsBase, Set<Rule> rulesBase, Set<Formula> formulas){
        this.factsBase = factsBase;
        this.rulesBase = rulesBase;
        this.formulas = formulas;
        this.unsetRules = new HashSet<>();
        this.unsetFacts = new HashSet<>();
        this.findUnsets();
        this.tryFormulasEvaluation();
    }

    /**
     * Construit une nouvelle base de connaissances avec une base de règles, une base de faits et un ensemble de formules tous vides au départ.
     */
    public KnowledgeBase () { this(new HashSet<>(), new HashSet<>(), new HashSet<>()); }


    /**
     * Cette méthode permet de trouver les objets unset de la base.
     * Un fait est unset si sa variable retourne false pour la méthode {@link systems.facts.Variable#isSet()}, ou l'une des variables de ses faits dans le cas où il est composite.<br>
     * Une règle est unset si sa prémisse est un fait unset.
     */
    private void findUnsets () {
        for (Rule rule : this.rulesBase) {
            if (!rule.getPremise().isSet()) {
                this.unsetRules.add(rule);
            }
        }
        Set<Fact> tmpFacts = new HashSet<>(this.factsBase);
        for (Fact fact : tmpFacts) {
            if (!fact.isSet()) {
                this.unsetFacts.add(fact);
            }
            this.update(fact);
        }
    }


    /**
     * Récupère la base de faits de la base de connaissances.
     * @return la base de faits
     */
    public Set<Fact> getFactsBase () {
        return this.factsBase;
    }

    /**
     * Récupère la base de règles de la base de connaissances.
     * @return la base de règles
     */
    public Set<Rule> getRulesBase () {
        return this.rulesBase;
    }

    /**
     * Récupère l'ensemble de formules de la base de connaissances.
     * @return l'ensemble de formules
     */
    public Set<Formula> getFormulas () { return this.formulas; }

    /**
     * Ajoute un nouveau fait à la base.
     * @param toBeAdded le fait à ajouter
     */
    public void addFact(Fact toBeAdded){
        this.factsBase.add(toBeAdded);
        if (!toBeAdded.isSet()) { this.unsetFacts.add(toBeAdded); }
        this.update(toBeAdded);
    }

    /**
     * Ajoute une nouvelle règle à la base.
     * @param newRule la règle à ajouter
     */
    public void addRule (Rule newRule) {
        this.rulesBase.add(newRule);
        if (!newRule.getPremise().isSet()) { this.unsetRules.add(newRule); }
    }

    /**
     * Ajoute une nouvelle formule à la base.
     * @param formula la formule à ajouter
     */
    public void addFormula (Formula formula) {
        this.formulas.add(formula);
        this.tryFormulaEvaluation(formula);
    }

    /**
     *  Ajoute une nouvelle règle à partir de l'interface
     * @param toBeAdded la règle à ajouter
     * @return une chaîne de caractère décrivant l'issue de l'ajout. "Règle déjà existante" si la chaîne entrée est dans l'ensemble de règles. "Règle ajoutée avec succès" si la règle a pu être ajouté. "Désolé, veuillez revoir la structure de votre fait" dans le cas où si la structure de la règle est incorrecte.
     */

    public String addRuleFromUser(String toBeAdded){
        if (!(this.parser.parseRule(toBeAdded) == null)){
            for(Rule r : this.rulesBase)
                if (r.equals(this.creator.createRuleFrom(this.parser.parseRule(toBeAdded)))){
                    return "Règle déjà existante";
                } else { 
                    this.rulesBase.add(this.creator.createRuleFrom(this.parser.parseRule(toBeAdded)));
                    return "Règle ajoutée avec succès";
                }
        } 
        
        return "Désolé, veuillez revoir la structure de votre règle";
        
    }
    /**
     *  Ajoute un nouveau fait à partir de l'interface
     * @param toBeAdded le fait à ajouter
     * @return une chaîne de caractère décrivant l'issue de l'ajout. "Fait déjà existant" si la chaîne entrée est dans l'ensemble de faits. "Fait ajouté avec succès" si le fait a pu être ajouté. "Désolé, veuillez revoir la structure de votre fait" dans le cas où si la structure d'un fait est incorrecte.
     */

    public String addFactFromUser(String toBeAdded){
        if (!(this.parser.parseFact(toBeAdded) == null)){
            for(Fact f : this.factsBase){ 
                if (f.equals(this.creator.createFactFrom(this.parser.parseFact(toBeAdded)))){
                    return "Fait déjà existant";
                } else { 
                    this.factsBase.add(this.creator.createFactFrom(this.parser.parseFact(toBeAdded)));
                    return "Fait ajouté avec succès";
                }
            }
        }
        
        return "Désolé, veuillez revoir la structure de votre fait";
        
    }

    /** 
     * Supprime un fait à partir de l'interface
     * @param toBeDropped le fait à supprimer
     * @return une chaîne de caractère décrivant l'issue de l'ajout. "Fait supprimé avec succès" si la chaîne entrée est dans l'ensemble de faits. "Le fait n'est pas contenu dans la base" si le fait n'est pas dans la base.
     */


    public String dropFact(String toBeDropped){
        for(Fact currentFact : this.factsBase){
            if (currentFact.equals(this.creator.createFactFrom(this.parser.parseFact(toBeDropped)))){
                this.factsBase.remove(currentFact);
                return "Fait supprimé avec succès";
            }
        }

        return "Le fait n'est pas contenu dans la base";
        

    }

    /** 
     * Supprime une règle à partir de l'interface
     * @param toBeDropped la règle à supprimer
     * @return une chaîne de caractère décrivant l'issue de l'ajout. "Règle supprimée avec succès" si la chaîne entrée est dans l'ensemble de règles. "La règle n'est pas contenue dans la base" si le règle n'est pas dans la base.
     */
    public String dropRule(String toBeDropped){
        for (Rule currentRule : this.rulesBase){
            if (currentRule.equals(this.creator.createRuleFrom(this.parser.parseRule(toBeDropped)))){
                this.rulesBase.remove(currentRule);
                return "Règle supprimée avec succès";
                
            }
        } 
        return "La règle n'est pas contenue dans la base";
        

    }

    /**
     * Indique si un fait est connu de la base ou pas.
     * Un fait est connu s'il est contenu dans la base de faits ou si la base de faits contient un fait qui lui est équivalent.
     * @param searchedFact le fait 
     * @return true si le fait est connu, false sinon
     */
    public boolean isFactKnown (Fact searchedFact) {

        if (this.factsBase.contains(searchedFact)) { return true; }

        if (searchedFact instanceof NonCompositeFact) {
            for (Fact fact : this.factsBase) {
                if (searchedFact.areEquivalent(fact)) {
                    return true;
                }
            }
            NonCompositeFact s = (NonCompositeFact) searchedFact;
            if (s instanceof BooleanFact && s.getValue().equals(false)){
                if (!s.isContradictory(factsBase)){
                    return true;
                }
            }
            return false;
        }

        CompositeFact searchedFactAsComposite = (CompositeFact) searchedFact;
        boolean isOneFactKnown = this.isFactKnown(searchedFactAsComposite.getOneFact());
        boolean isAnotherFactKnown = this.isFactKnown(searchedFactAsComposite.getAnotherFact());
        
        if (searchedFact instanceof ConjunctionFact) {
            return isOneFactKnown && isAnotherFactKnown;
        } else if (searchedFact instanceof DisjunctionFact) {
            return isOneFactKnown || isAnotherFactKnown;
        } else {
            return (isOneFactKnown && (!isAnotherFactKnown)) || ((!isOneFactKnown) && isAnotherFactKnown);
        }

    }


    /**
     * Indique si une formule est applicable (évaluable) ou pas.
     * Une formule est applicable si son expression est calculable (voir {@link systems.base.KnowledgeBase#isExpressionCalculable} pour plus).
     * @param formula la formule
     * @return true si la formule est applicable, false sinon
     */
    public boolean isFormulaApplicable (Formula formula) {
        return this.isExpressionCalculable(formula.getExpression());
    }

    /**
     * Indique si une expression est calculable ou pas.
     * Une expression est calculable si pour toutes la variables qui la compose, la méthode {@link systems.facts.Variable#isSet()} retourne true.
     * @param expression l'expression
     * @return true si l'expression est calculable, false sinon
     */
    public boolean isExpressionCalculable (Expression expression) {
        if (expression.isVariable()) {
            return this.isNumericVariableKnown(expression.getVariable()) || expression.getVariable().isSet();
        }
        return this.isExpressionCalculable(expression.getLeft()) && this.isExpressionCalculable(expression.getRight());
    }

    /**
     * Indique si une variable paramétrée avec Float est connue ou pas.
     * Une variable est connue si elle est égale à l'une des variables des faits numériques contenus dans la base.
     * @param variable la variable
     * @return true si la variable est connue, false sinon
     */
    public boolean isNumericVariableKnown (Variable<Float> variable) {
        for (Fact fact : this.factsBase) {
            if (fact instanceof NumericFact) {
                NumericFact factAsNumeric = (NumericFact) fact;
                if (factAsNumeric.getName().equals(variable.getName()) && factAsNumeric.isSet() && factAsNumeric.getOperator().equals("=")) {
                    variable.setValue(factAsNumeric.getValue());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Met à jour la base avec un fait donné.
     * La mise à jour consiste à voir si le fait en paramètre permettra d'attribuer une valeur à un fait (ou à la premise d'une règle) qui n'a pas de valeur, s'ils ont le même nom.<br>
     * Par exemple, si on a le fait "revenus > depenses", la mise à jour avec le fait "depenses = 5000" permettra d'avoir le fait "revenus > 5000".<br>
     * Ou si on a la règle "Si temperatureDhier > temperatureDaujourdhui alors augmentationDeTemperature", la mise à jour avec le fait "temperatureDaujourdhui = 30" permettra d'avoir la règle "Si temperatureDhier > 30 alors augmentationDeTemperature".
     * @param fact le fait avec lequel on veut mettre à jour la base
     */
    public void update (Fact fact) {
        if (fact instanceof NumericFact) {
            this.updateAux((NumericFact)fact);
        }
        if (fact instanceof NanFact) {
            this.updateAux((NanFact)fact);
        }
        if (fact instanceof CompositeFact) {
            CompositeFact factAsComposite = (CompositeFact) fact;
            this.update(factAsComposite.getOneFact());
            this.update(factAsComposite.getAnotherFact());
        }
        this.tryFormulasEvaluation();
    }

    /**
     * Une méthode auxiliaire de update pour traiter juste les faits non composite.
     * @param fact le fait non composite
     */
    private <T> void updateAux (NonCompositeFact<T> fact) {
        for (Rule unsetRule : this.unsetRules) {
            if (this.setFact(unsetRule.getPremise(), fact)) { this.unsetRules.remove(unsetRule); }
        }
        for (Fact unsetFact : this.unsetFacts) {
            if (this.setFact(unsetFact, fact)) { this.unsetFacts.remove(unsetFact); }
        }
    }

    /**
     * Permet de set un fact avec un autre.
     * Par exemple, si on a le fait "revenus > depenses", le set fact avec le fait "depenses = 5000" permettra d'avoir le fait "revenus > 5000".<br>
     * Ou si on a le fait "revenus > depenses et primes = 3000", le set fact avec le fait "depenses = 5000 = 30" permettra d'avoir le fait "revenus > 5000 et primes = 3000".
     * @param factToSet le fait qu'on veut set
     * @param otherFact le fait avec lequel on veut le set
     * @return true si l'opération à réussi (les deux faits sont de même types et ont le même nom, ou si factToSet est composite, il contient un fait de même type et de même nom que otherFact), false sinon
     */
    public <T> boolean setFact (Fact factToSet, NonCompositeFact<T> otherFact) {
        if (factToSet instanceof NumericFact && otherFact instanceof NumericFact) {
            return this.setFactAux((NumericFact)factToSet, (NumericFact)otherFact);
        }
        if (factToSet instanceof NanFact && otherFact instanceof NanFact) {
            return this.setFactAux((NanFact)factToSet, (NanFact)otherFact);
        }
        if (factToSet instanceof CompositeFact) {
            CompositeFact factToSetAsComposite = (CompositeFact) factToSet;
            return this.setFact(factToSetAsComposite.getOneFact(), otherFact) ||
                this.setFact(factToSetAsComposite.getAnotherFact(), otherFact);
        }
        return false;
    }

    /**
     * Une méthode auxiliaire de setFact pour traiter juste les faits non composites.
     * @param factToSet le fait non composite qu'on veut set
     * @param otherFact le fait non composite avec lequel on veut le set
     * @return true si l'opération a réussi, false sinon
     */
    private <T> boolean setFactAux (NonCompositeFact<T> factToSet, NonCompositeFact<T> otherFact) {
        if ((!factToSet.isSet()) && factToSet.getVariable().getOther().getName().equals(otherFact.getName())) {
            factToSet.setValue(otherFact.getValue());
            return true;
        }
        return false;
    }


    /**
     * Essaie d'appliquer (évaluer toutes) les formules contenues dans la base.
     * @return true si au moins une formule a été évaluée, false sinon
     */
    public boolean tryFormulasEvaluation () {
        boolean res = false;
        for (Formula formula : this.formulas) {
            if (this.tryFormulaEvaluation(formula)) { res = true; }
        }
        return res;
    }

    /**
     * Essaie d'appliquer (évaluer) une formule.
     * @return true si la formule a été évaluée, false sinon
     */
    private boolean tryFormulaEvaluation (Formula formula) {
        if (this.isFormulaApplicable(formula)) {
            NumericFact newFact = new NumericFact(new Variable<Float>(formula.getName(), formula.evaluate()), "=");
            this.factsBase.add(newFact);
            this.updateAux(newFact);
            return true;
        }
        return false;
    }

}