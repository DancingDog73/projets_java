package systems.facts;

import java.util.*;

/**
 * La classe NanFact sert à représenter les faits sympoliques (par expemple profession est enseignant).
 * Un fait symbolique est composé d'une variable (@see systems.facts.Variable) représentée par  un nom( string) et une valeur string
 */
public class NanFact extends NonCompositeFact<String> {

   
    /**
     * Construit un NanFact nouvellement alloué qui représente la variable spécifiée.
     * @param est une variable nan à représenter par l'objet NanFact
     */
    public NanFact (Variable<String> variable){
        super(variable);
    }

    /**
     * Compare ce fait au fait spécifié. Le résultat est vrai si ce fait est équivalent au fait spécifié.
     * @param other est l'autre fait auquel nous comparons ce fait
     * @return true si le fait donné est équivalent à celui-ci, false sinon
     */
    @Override
    public boolean areEquivalent(Fact other){
        return this.equals(other);
    }

    /**
     * Vérifie si un fait symbolique est contradictoire à une base de faits.
     * @param factBase est la base que nous utilisons pour les comparaisons
     * @return true si ce nan fait est contradictoire à la base donnée, false sinon
     */
    @Override
    public boolean isContradictory(Set<Fact> factsBase){
        for (Fact other: factsBase){
            if (other instanceof NanFact){
                NanFact otherAsNanFact = (NanFact) other;
                if (this.getName().equals(otherAsNanFact.getName())){
                    if (!(this.getValue().equals(otherAsNanFact.getValue()))){
                        return true;
                    }
                } 
            }
            
        }
        
        return false;
    }

    /**
     * Compare ce Nanfact à celui spécifié. Le résultat est vrai si et seulement si l'argument n'est pas nul et est un fait nan qui représente le même nom et la même valeur que cet objet.
     * @param other est l'objet avec lequel comparer ce fait
     * @return true si l'objet donné est le même que celui-ci ; faux sinon
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof NanFact){
            NanFact otherAsNanFact = (NanFact) other;
            return this.getVariable().equals(otherAsNanFact.getVariable());
        }
        return false;
    }
    
    /**
     * Renvoie un code de hachage pour ce fait.
     * @return un code de hachage pour cet objet
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.getVariable());
    }
    
    /**
     * Renvoie un objet chaîne représentant l'objet spécifié.
     * @return une représentation sous forme de chaîne de ce fait nan
     */
    @Override
    public String toString(){
        return this.getName() + " est " + (this.variable.getOther() == null ? this.getValue() : "_" + this.variable.getOther().getName());
    }


}