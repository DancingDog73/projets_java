package systems.parsers;

import java.util.Objects;

/**
 * La classe ParsedFact représente les faits parsés. Un fait parsé possède un type, une variable, un opérateur et une valeur s'il n'est pas composite ;
 * un compositeur et deux autres faits parsé s'il est composite.
 */
public class ParsedFact {

    /**Type du fait parsé */
    private String type;
    /**Variable du fait parsé */
    private String variable;
    /**Opérateur du fait parsé */
    private String operator;
    /**Valeur du fait parsé */
    private String value;

    /**Indique si un fait parsé est composite ou pas */
    private boolean isComposite;
    /**Compositeur (ou, et, oux) du fait parsé */
    private String compositor;
    /**Premier composant du fait parsé composite */
    private ParsedFact oneParsedFact;
    /**Deuxième composant du fait parsé composite */
    private ParsedFact anotherParsedFact;

    /**
     * Construit un nouveau fait parsé non composite.
     * @param type le type du fait
     * @param variable lae variable du fait
     * @param operator l'opérateur' du fait
     * @param value la valeur du fait
     */
    public ParsedFact (String type, String variable, String operator, String value) {
        this.type = type;
        this.variable = variable;
        this.operator = operator;
        this.value = value;
        this.isComposite = false;
    }

    /**
     * Construit un nouveau fait parsé composite.
     * @param compositor le compositeur du fait ("et" pour la conjonction, "ou" pour la disjonction, "oux" pour l'exclusion)
     * @param oneParsedFact premier composant du fait parsé composite
     * @param anotherParsedFact deuxième composant du fait parsé composite
     */
    public ParsedFact (String compositor, ParsedFact oneParsedFact, ParsedFact anotherParsedFact) {
        this.compositor = compositor;
        this.oneParsedFact = oneParsedFact;
        this.anotherParsedFact = anotherParsedFact;
        this.isComposite = true;
    }

    /**
     * Récupère le type du fait parsé.
     * @return le type du fait parsé, null si le fait est composite
     */
    public String getType () {
        return this.type;
    }

    /**
     * Récupère la variable du fait parsé.
     * @return la variable du fait parsé, null si le fait est composite
     */
    public String getVariable () {
        return this.variable;
    }

    /**
     * Récupère l'opérateur du fait parsé.
     * @return l'opérateur du fait parsé, null si le fait est composite
     */
    public String getOperator () {
        return this.operator;
    }

    /**
     * Récupère la valeur du fait parsé.
     * @return la valeur du fait parsé, null si le fait est composite
     */
    public String getValue () {
        return this.value;
    }

    /**
     * Indique si un fait est composite ou pas.
     * @return true si le fait est composite, false sinon
     */
    public boolean isComposite () {
        return this.isComposite;
    }

    /**
     * Récupère le compositeur du fait parsé.
     * @return le compositeur du fait parsé, null si le fait n'est pas composite
     */
    public String getCompositor () {
        return this.compositor;
    }

    /**
     * Récupère le premier composant du fait parsé.
     * @return le premier composant du fait parsé, null si le fait n'est pas composite
     */
    public ParsedFact getOneParsedFact () {
        return this.oneParsedFact;
    }

    /**
     * Récupère le deuxième composant du fait parsé.
     * @return le deuxième composant du fait parsé, null si le fait n'est pas composite
     */
    public ParsedFact getAnotherParsedFact () {
        return this.anotherParsedFact;
    }


    /**
     * Dis si deux faits parsés sont égaux ou pas. Deux faits parsés sont égaux si et seulement si :<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;- ils sont tous deux non composites et leurs attributs (type, variable, operator, value) sont tous égaux<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;- ils sont tous deux  composites et leurs attributs (compositor, oneParsedFact, anotherParsedFact) sont tous égaux
     */
    @Override
    public boolean equals (Object other) {
        if (other instanceof ParsedFact) {
            ParsedFact otherAsParsedFact = (ParsedFact) other;
            if ((!this.isComposite()) && (!otherAsParsedFact.isComposite())) {
                return this.type.equals(otherAsParsedFact.getType())
                    && this.variable.equals(otherAsParsedFact.getVariable())
                    && this.operator.equals(otherAsParsedFact.getOperator())
                    && this.value.equals(otherAsParsedFact.getValue());
            }
            if (this.isComposite() && otherAsParsedFact.isComposite()) {
                return this.oneParsedFact.equals(otherAsParsedFact.getOneParsedFact())
                    && this.compositor.equals(otherAsParsedFact.getCompositor())
                    && this.anotherParsedFact.equals(otherAsParsedFact.getAnotherParsedFact());
            }
        }
        return false;
    }

    @Override
    public int hashCode () {
        if (!this.isComposite) { return Objects.hash(this.type, this.variable, this.operator, this.value); }
        return Objects.hash(this.oneParsedFact, this.operator, this.anotherParsedFact);
    }

    @Override
    public String toString () {
        if (!this.isComposite) {
            return "{type: " + this.type + ", variable: " + this.variable + ", operator: " + this.operator + ", value: " + this.value + "}";
        }
        return "[" + this.compositor + ", " + this.oneParsedFact + ", " + this.anotherParsedFact + "]";
    }

}