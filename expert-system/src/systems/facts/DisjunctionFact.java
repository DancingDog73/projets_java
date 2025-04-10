package systems.facts;

import java.util.Set;
import java.util.HashSet;

/**
 * La classe DisjunctionFact sert à représenter une disjonction entre deux faits.
 * Une disjonction est un ou logique entre deux faits
 */
public class DisjunctionFact extends CompositeFact {

    /**
     * Construit un DisjunctionFact nouvellement alloué qui représente une disjonction entre deux faits.
     * @param oneFact est une instance de Fact
     * @param anotherFact est une instance de Fact
     */
    public DisjunctionFact (Fact oneFact, Fact anotherFact) {
        super(oneFact, anotherFact);
    }

    /**
     * Compare ce DisjunctionFact au fait spécifié. Le résultat est vrai si ce fait est équivalent au fait spécifié.
     * @param other est l'autre fait auquel nous comparons ce fait
     * @return true si le fait donné est équivalent à celui-ci, false sinon
     */
    @Override
    public boolean areEquivalent (Fact other) {
        if (other instanceof NonCompositeFact){
            return this.oneFact.areEquivalent(other) || this.anotherFact.areEquivalent(other);
        }
        if (other instanceof CompositeFact){
            CompositeFact otherFact = (CompositeFact) other;
            return (this.oneFact.areEquivalent(otherFact.getOneFact()) || this.anotherFact.areEquivalent(otherFact.getAnotherFact())) || (this.oneFact.areEquivalent(otherFact.getAnotherFact()) || this.anotherFact.areEquivalent(otherFact.getOneFact()));
        }
        return false;
        
    }

    /**
     * Renvoie un objet chaîne représentant l'objet spécifié.
     * @return une représentation sous forme de chaîne de ce DisjunctionFact
     */
    @Override
    public String toString () {
        return this.oneFact + " ou " + this.anotherFact;
    }

    /**
     * Vérifie si un DisjunctionFact est contradictoire avec une base de faits.
     * @param factBase est la base que nous utilisons pour les comparaisons
     * @return true si ce DisjunctionFact est contradictoire avec la base donnée, false sinon
     */
    @Override 
    public boolean isContradictory(Set<Fact> factsBase){
        boolean result1 = this.oneFact.isContradictory(factsBase);
        boolean result2 = this.anotherFact.isContradictory(factsBase);
        return (result1 && result2);
    }

    /**
     * Renvoie un code de hachage pour ce fait.
     * @return un code de hachage pour cet objet
     */
    @Override
    public int hashCode(){
        int result = this.oneFact.hashCode() + this.anotherFact.hashCode();
        return result * 13;
    }

    /**
     * Compare ce DisjunctionFact à celui spécifié. Le résultat est vrai si et seulement si l'argument n'est pas nul et est un DisjunctionFact qui représente les deux mêmes faits que cet objet.
     * @param other est l'objet avec lequel comparer ce fait
     * @return true si l'objet donné est le même que celui-ci ; faux sinon
     */
    @Override 
    public boolean equals(Object other){
        if(other instanceof DisjunctionFact){
            DisjunctionFact otherFact = (DisjunctionFact) other;
            return (this.oneFact.equals(otherFact.getOneFact()) && this.anotherFact.equals(otherFact.getAnotherFact())) || (this.oneFact.equals(otherFact.getAnotherFact()) && this.anotherFact.equals(otherFact.getOneFact()));
        }
        return false;
    }

}