package systems.facts;

import java.util.Objects;



/**
 * La classe Variable sert à représenter les variable.
 * Elles ont un nom (string) et une valeur (générique)
 */
public class Variable<E> {
    /**
     * Le nom de la variable
     */
    private String name;
    /**
     * La valeur de cette variable
     */
    private E value;
    /**
     * La variable secondaire de la variable
     */
    private Variable<E> other;
    
    /**
     * Construit une variable nouvellement allouée.
     * @param name est une chaîne qui représente le nom de cette variable
     * @param value est la valeur de cette variable
     */
    public Variable (String name, E value) {
        this.name = name;
        this.value = value;
    }
    
   /**
     * Construit une variable nouvellement allouée.
     * @param name est une chaîne qui représente le nom de cette variable
     * @param otherVariable une autre instance de variable
     */
    public Variable (String name, Variable<E> otherVariable) {
        this.name = name;
        this.other = otherVariable;
        this.value = otherVariable.getValue();
    }

    /**
     * Construit une variable nouvellement allouée.
     * @param name est une chaîne qui représente le nom de cette variable
     */
    public Variable (String name) {
        this.name = name;
    }

    /**
     * Renvoie le nom de cette variable.
     * @return le nom de cette variable
     */
    public String getName () {
        return this.name;
    }

    /**
     * Vérifie si la valeur de cette variable n'est pas nulle.
     * @return true si la valeur de cette variable n'est pas nulle ; false sinon
     */


    public boolean isSet () {
        return this.value != null;
    }

    /**
     * Renvoie la valeur de cette variable.
     * @return la valeur de cette variable
     */
    public E getValue () {
        return this.value;
    }

    /**
     * Renvoie l'autre variable de cette variable.
     * @return l'autre variable de cette variable
     */
    public Variable<E> getOther() {
        return this.other;
    }

    /**
     * Change la valeur de cette variable.
     * @param newValue est la nouvelle valeur de cette variable
     */
    public void setValue (E newValue) {
        this.value = newValue;
    }

    /**
     * compare cette variable à celle spécifiée. Le résultat est vrai si et seulement si l'argument n'est pas nul et est une variable qui représente les deux mêmes faits que cet objet.
     * @param other est l'objet avec lequel comparer cette variable
     * @return true si l'objet donné est le même que celui-ci ; false sinon
     */
    @Override
    public boolean equals (Object other) {
        if (other instanceof Variable) {
            Variable<?> otherAsVariable = (Variable<?>) other;
            if (this.isSet() && otherAsVariable.isSet()) {
                return this.name.equals(otherAsVariable.getName()) && this.value.equals(otherAsVariable.getValue());
            }
            if (this.other != null && otherAsVariable.getOther() != null) {
                return this.name.equals(otherAsVariable.getName()) && this.other.equals(otherAsVariable.getOther());
            }
            if (this.other == null && !this.isSet() && otherAsVariable.getOther() == null && !otherAsVariable.isSet()) {
                return this.name.equals(otherAsVariable.getName());
            }
        }
        return false;
    }

    /**
     * Renvoie un code de hachage pour cette variable.
     * @return un code de hachage pour cet objet
     */
    @Override
    public int hashCode () {
        if (this.isSet()) {
            return Objects.hash(this.name, this.value);
        }
        return Objects.hash(this.name, this.other);
    }

    /**
     * renvoie un objet chaîne représentant l'objet spécifié.
     * @return une représentation sous forme de chaîne de cette variable
     */
    @Override
    public String toString () {
        if (this.name.equals("")) {
            return "" + this.value;
        }
        if (this.other != null) {
            return this.name + " = " + this.other;
        }
        return this.name;
    }

}