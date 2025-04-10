package systems.facts;

import java.util.*;

/**
 * La classe NumericFact sert à représenter les faits numeriques (par expemple age = 12).
 * Un fait numérique est composé d'une variable (@see systems.facts.Variable) représentée par  un nom( string) et une valeur float
 */
public class NumericFact extends NonCompositeFact<Float> {

    /**
     * L'opérateur de ce fait numérique 
     */
    private String operator;

    /**
     * Construit un NumericFact nouvellement alloué qui représente la variable spécifiée.
     * @param variable est  une variable numérique à représenter par l'objet NumericFact
     * @param operator un symbole de comparaison de chaîne qui donne des informations supplémentaires sur le fait
     */
    public NumericFact(Variable<Float> variable, String operator) {
        super(variable);
        this.operator = operator;
    }

    /**
     * Renvoie l'opérateur de ce fait numérique.
     * @return une chaîne qui représente l'opérateur de ce NumericFact
     */
    public String getOperator(){
        return this.operator;
    }

    /**
     * Compare ce Numericfact au fait spécifié. Le résultat est vrai si ce fait est équivalent au fait spécifié.
     * @param other est l'autre fait auquel nous comparons ce fait
     * @return true si le fait donné est équivalent à celui-ci, false sinon
     */
    @Override
    public boolean areEquivalent(Fact other){
        if (other instanceof NumericFact){

            NumericFact otherAsNumericFact = (NumericFact) other;
            if (this.equals(otherAsNumericFact)){
                return true;
            }

            if(this.getName().equals(otherAsNumericFact.getName()) && this.isSet() && otherAsNumericFact.isSet()) {

                if (this.operator.equals(otherAsNumericFact.getOperator()) && this.operator.equals("=")){
                    return this.getValue() == otherAsNumericFact.getValue();
                }

                if (this.operator.equals("=")){
                    if(otherAsNumericFact.getOperator().equals(">")){
                        return this.getValue() > otherAsNumericFact.getValue();
                    } else {
                        return this.getValue() < otherAsNumericFact.getValue();
                    }
                }

                if (this.operator.equals(">")){
                    return otherAsNumericFact.getValue() > this.getValue();
                }

                if (this.operator.equals("<")){
                    return otherAsNumericFact.getValue() < this.getValue();
                }

                if (this.operator.equals("<=")){
                    return otherAsNumericFact.getValue() <= this.getValue();
                }

                if (this.operator.equals(">=")){
                    return otherAsNumericFact.getValue() >= this.getValue();
                }

            }
        }
        return false;
    }
    
    /**
     * Vérifie si un NumericFact est contradictoire avec une base de faits.
     * @param factBase est la base que nous utilisons pour les comparaisons
     * @return true si ce NumericFact est contradictoire avec la base donnée, false sinon
     */
    @Override
    public boolean isContradictory(Set<Fact> factsBase){
        for (Fact other : factsBase){
            if (other instanceof NumericFact){
                NumericFact otherAsNumericFact = (NumericFact) other;
                if(this.getName().equals(otherAsNumericFact.getName())){
                    if (!(this.areEquivalent(otherAsNumericFact))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Compare ce NumericFact à celui spécifié. Le résultat est vrai si et seulement si l'argument n'est pas nul et est un NumericFact qui représente le même nom, la même valeur et le même opérateur que cet objet.
     * @param other est l'objet avec lequel comparer ce fait
     * @return true si l'objet donné est le même que celui-ci ; faux sinon
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof NumericFact){       
            NumericFact otherAsNumericFact = (NumericFact) other;
            return this.getVariable().equals(otherAsNumericFact.getVariable()) && this.operator.equals(otherAsNumericFact.getOperator());
        }
        return false;
    }
    
    /**
     * Renvoie un code de hachage pour ce fait.
     * @return un code de hachage pour cet objet
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.getVariable(), this.operator);
    }

    /**
     * renvoie un objet chaîne représentant l'objet spécifié.
     * @return une représentation sous forme de chaîne de ce fait numérique
     */
    @Override
    public String toString(){
        return this.getName() + " " + this.operator + " " + (this.variable.getOther() == null ? this.getValue() : this.variable.getOther().getName());
    }


}