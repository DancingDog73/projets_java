package systems.facts;

/**
 * La classe NonCompositeFact sert à représenter les faits non composites (booléens, numériques, symboliques).
 */
public abstract class NonCompositeFact<E> implements Fact {
    /**
     * Cette variable est celle qui sera représentée par ce fait
     */
    protected Variable<E> variable;

    /**
     * Construit un NonCompositeFact nouvellement alloué.
     * La variable @param est une instance de variable qui peut représenter un booléen, une chaîne ou un nombre
     */
    public NonCompositeFact (Variable<E> variable) {
        this.variable = variable;
    }

    /**
     * Renvoie le nom de la variable de ce NonCompositeFact.
     * @return le nom de la variable de ce NonCompositeFact
     */
    public String getName () {
        return this.variable.getName();
    }

    /**
     * Renvoie la variable de ce NonCompositeFact.
     * @return la variable de ce NonCompositeFact
     */
    public Variable<E> getVariable () {
        return this.variable;
    }

    /**
     * Renvoie la valeur de la variable de ce NonCompositeFact.
     * @return la valeur de la variable de ce NonCompositeFact
     */
    public E getValue () {
        return this.variable.getValue();
    }

    /**
     * Modifie la valeur de la variable de ce NonCompositeFact.
     */


    public void setValue (E newValue) {
        this.variable.setValue(newValue);
    }

    /**
     * Vérifie si la variable de ce fait est définie.
     * @return true si la variable de ce Fait est définie, false sinon
     */
    @Override
    public boolean isSet () {
        return this.variable.isSet();
    }


}