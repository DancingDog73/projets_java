package systems.facts;

import java.util.*;

/**
 * La classe ConjunctionFact sert à représenter une conjonction entre deux faits.
 * Une conjonction est un et logique entre deux faits
 */
public class ConjunctionFact extends CompositeFact {

    /**
     * Construit un ConjunctionFact nouvellement alloué qui représente une conjonction entre deux faits.
     * @param oneFact est une instance de Fact
     * @param anotherFact est une instance de Fact
     */
    public ConjunctionFact (Fact oneFact, Fact anotherFact) {
        super(oneFact, anotherFact);
    }

    /**
     * Compare ce ConjunctionFact au fait spécifié. Le résultat est vrai si ce fait est équivalent au fait spécifié.
     * @param other est l'autre fait auquel nous comparons ce fait
     * @return true si le fait donné est équivalent à celui-ci, false sinon
     */
    @Override
    public boolean areEquivalent (Fact other) {
        
        if (other instanceof ConjunctionFact){
            ConjunctionFact otherFact = (ConjunctionFact) other;
            return (this.oneFact.areEquivalent(otherFact.getOneFact()) && this.anotherFact.areEquivalent(otherFact.getAnotherFact())) || (this.oneFact.areEquivalent(otherFact.getAnotherFact()) && this.anotherFact.areEquivalent(otherFact.getOneFact()));

        }
        return false;
    }

    /**
     * Renvoie un objet chaîne représentant l'objet spécifié.
     * @return une représentation sous forme de chaîne de ce ConjunctionFact
     */
    @Override
    public String toString () {
        return this.oneFact + " et " + this.anotherFact;
    }

    /**
     * Vérifie si un ConjunctionFact est contradictoire avec une base de faits.
     * @param factBase est la base que nous utilisons pour les comparaisons
     * @return true si ce ConjunctionFact est contradictoire à la base donnée, false sinon
     */
    @Override 
    public boolean isContradictory(Set<Fact> factsBase){
        boolean result1 = this.oneFact.isContradictory(factsBase);
        boolean result2 = this.anotherFact.isContradictory(factsBase);
        return (result1 || result2);
    }

    /**
     * Renvoie un code de hachage pour ce fait.
     * @return un code de hachage pour cet objet
     */
    @Override
    public int hashCode(){
        int result = this.oneFact.hashCode() + this.anotherFact.hashCode();
        return result * 14;
    }

    
    /**
     * Compare ce ConjunctionFact à celui spécifié. Le résultat est vrai si et seulement si l'argument n'est pas nul et est un ConjunctionFact qui représente les deux mêmes faits que cet objet.
     * @param other est l'objet avec lequel comparer ce fait
     * @return true si l'objet donné est le même que celui-ci ; false sinon
     */
    @Override 
    public boolean equals(Object other){
        if(other instanceof ConjunctionFact){
            ConjunctionFact otherFact = (ConjunctionFact) other;
            return (this.oneFact.equals(otherFact.getOneFact()) && this.anotherFact.equals(otherFact.getAnotherFact())) || (this.oneFact.equals(otherFact.getAnotherFact()) && this.anotherFact.equals(otherFact.getOneFact()));
        }
        return false;
    }

}