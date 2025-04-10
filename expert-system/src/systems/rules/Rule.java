package systems.rules;

import java.util.*;

import systems.facts.Fact;


/**
 * La classe Rule permet de représenter les règles, constituées d'une premise et d'une conclusion, toutes deux étant des {@link systems.facts.Fact}.
 */
public class Rule {

    /**La premise de la règle */
    private Fact premise;
    /**La conclusion de la règle */
    private Fact conclusion;

    /**
     * Construit une nouvelle règle à partir de sa prémisse et de sa conclusion.
     * @param premise la prémisse de la règle
     * @param conclusion la conclusion de la règle
     */
    public Rule (Fact premise, Fact conclusion) {
        this.premise = premise;
        this.conclusion = conclusion;
    }

    /**
     * Récupère la prémisse de la règle.
     * @return la prémisse de la règle
     */
    public Fact getPremise () {
        return this.premise;
    }

    /**
     * Récupère la conclusion de la règle.
     * @return la conclusion de la règle
     */
    public Fact getConclusion () {
        return this.conclusion;
    }


    /**
     * Deux règles sont égales si et seulement si leurs deux prémisses sont égales et leurs deux conclusion sont égales.
     */
    @Override
    public boolean equals (Object other) {
        if (other instanceof Rule) {
            Rule otherAsRule = (Rule) other;
            return this.premise.equals(otherAsRule.getPremise()) && this.conclusion.equals(otherAsRule.getConclusion());
        }
        return false;
    }

    @Override
    public int hashCode () {
        return Objects.hash(this.premise, this.conclusion);
    }

    @Override
    public String toString () {
        return "Si " + this.premise + " alors " + this.conclusion;
    }

}