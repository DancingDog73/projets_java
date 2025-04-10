package systems.facts;

import java.util.Set;
import java.util.HashSet;

/**
 * La classe BooleanFact sert à représenter les faits booléens.
 * Un fait booléen est composé d'une variable (@see systems.facts.Variable) représentée par  un nom( string) et un booléen 
 */
public class BooleanFact extends NonCompositeFact<Boolean> {

    /**
     * Construit un BooleanFact nouvellement alloué qui représente  la variable spécifée.
     * @param variable est une variable booléenne à représenter par l'objet BooleanFact
     */
    
    public BooleanFact (Variable<Boolean> variable) {
        super(variable);
    }

    /**
     * Compare ce fait  au fait  spécifié. Le résultat est true si ce  fait  est equivalent au fait spécifié.
     * @param other est l'autre fait auquel nous comparons ce fait
     * @return si le fait donné est équivalent à celui-ci, false sinon
     */

    @Override
    public boolean areEquivalent(Fact other){
        return this.equals(other);
    }
    
    /**
     * Vérifier si un fait booléen est contradictoire à une base de faits.
     * @param factsBase est la base que nous utilisons pour les comparaisons
     * @return si ce fait est contradictoire avec la base donnée, false sinon
     */
    @Override
    public boolean isContradictory(Set<Fact> factsBase){
        for (Fact other: factsBase){
            if (other instanceof BooleanFact){
                BooleanFact otherAsBooleanFact = (BooleanFact) other;
                if (this.getName().equals(otherAsBooleanFact.getName())){
                    if(this.getValue() != otherAsBooleanFact.getValue()){
                        return true;
                    }
                } 
            }
        }
        return false;
    }

    /**
     * Compare ce booleanfact à celui spécifié. Le résultat est vrai si et seulement si l'argument n'est pas nul et est un fait booléen qui représente le même nom et la même valeur que cet objet.
     * @param other est l'objet avec lequel comparer ce fait
     * @return true si l'objet donné est le même que celui-ci
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof BooleanFact){
            BooleanFact otherAsBooleanFact = (BooleanFact) other;
            return this.getName().equals(otherAsBooleanFact.getName()) && this.getValue() == otherAsBooleanFact.getValue();
        }
        return false;
    }
    
    /**
     * Renvoie un code de hachage pour ce fait.
     * @return un code de hachage pour cet objet
     */
    @Override
    public int hashCode(){
        int hash = 1;
        int value2 = this.getValue() ? 1 : 0;
        hash = hash * 73 + this.getName().hashCode();
        hash = hash * 10 + value2;
        return hash;
    }

    /**
     * Renvoie un objet string représentant l'objet spécifié.
     * @return  une représentation sous forme de string de ce fait booléen
     */

    @Override
    public String toString(){
        return this.getValue() ? this.getName() : "non " + this.getVariable();
    }


}