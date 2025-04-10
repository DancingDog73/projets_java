package systems.facts;

import java.util.Set;
import java.util.HashSet;

/**
 * La classe ExclusionFact sert à représenter une exclusion entre deux faits.
 * Une exclusion est un "non et" entre deux faits.
 */
public class ExclusionFact extends CompositeFact {

    /**
     * Construit un ExclusionFact nouvellement alloué qui représente une exclusion entre deux faits.
     * @param oneFact est une instance de Fact
     * @param anotherFact est une instance de Fact
     */
    public ExclusionFact (Fact oneFact, Fact anotherFact) {
        super(oneFact, anotherFact);
    }

    /**
     * Compare cet ExclusionFact au fait spécifié. Le résultat est vrai si ce fait est équivalent au fait spécifié.
     * @param other est l'autre fait auquel nous comparons ce fait
     * @return true si le fait donné est équivalent à celui-ci, false sinon
     */
    @Override
    public boolean areEquivalent (Fact other) {
        if (other instanceof NonCompositeFact){
            boolean firstOption = this.oneFact.areEquivalent(other) && (!this.anotherFact.areEquivalent(other));
            boolean secondOption = (!this.oneFact.areEquivalent(other)) && this.anotherFact.areEquivalent(other);
            return firstOption || secondOption;
        }
        CompositeFact otherFact = (CompositeFact) other;
        if (this.equals(otherFact)){
            return true;
        }
        int trueCounts = 0;
        trueCounts += this.oneFact.areEquivalent(otherFact.getOneFact()) && (!this.anotherFact.areEquivalent(otherFact.getOneFact()))? 1 : 0;
        trueCounts += (!this.oneFact.areEquivalent(otherFact.getOneFact())) && (this.anotherFact.areEquivalent(otherFact.getOneFact()))? 1 : 0;
        trueCounts += this.oneFact.areEquivalent(otherFact.getAnotherFact()) && (!this.anotherFact.areEquivalent(otherFact.getAnotherFact())) ? 1 : 0;
        trueCounts += (!this.oneFact.areEquivalent(otherFact.getAnotherFact())) && (this.anotherFact.areEquivalent(otherFact.getAnotherFact())) ? 1 : 0;
        return trueCounts == 1;
    }

    /**
     * Renvoie un objet chaîne représentant l'objet spécifié.
     * @return une représentation sous forme de chaîne de ce ExclusionFact
     */
    @Override
    public String toString () {
        return this.oneFact + " oux " + this.anotherFact;
    }

    /**
     * Vérifie si un ExclusionFact est contradictoire avec une base de faits.
     * @param factBase est la base que nous utilisons pour les comparaisons
     * @return true si ExclusionFact est contradictoire à la base donnée, false sinon
     */
    @Override 
    public boolean isContradictory(Set<Fact> factsBase){
        boolean result1 = this.oneFact.isContradictory(factsBase);
        boolean result2 = this.anotherFact.isContradictory(factsBase);
        return ((result1 && result2) || (!result1 && !result2));
    }

    /**
     * Renvoie un code de hachage pour ce fait.
     * @return un code de hachage pour cet objet
     */
    @Override
    public int hashCode(){
        int result = this.oneFact.hashCode() + this.anotherFact.hashCode();
        return result * 12;
    }

    /**
     * Compare ce ExclusionFact à celui spécifié. Le résultat est vrai si et seulement si l'argument n'est pas nul et est un ExclusionFact qui représente les deux mêmes faits que cet objet.
     * @param other est l'objet avec lequel comparer ce fait
     * @return true si l'objet donné est le même que celui-ci ; false sinon
     */
    @Override 
    public boolean equals(Object other){
        if(other instanceof ExclusionFact){
            ExclusionFact otherFact = (ExclusionFact) other;
            return (this.oneFact.equals(otherFact.getOneFact()) && this.anotherFact.equals(otherFact.getAnotherFact())) || (this.oneFact.equals(otherFact.getAnotherFact()) && this.anotherFact.equals(otherFact.getOneFact()));
        }
        return false;
    }


}