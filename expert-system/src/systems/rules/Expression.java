package systems.rules;

import java.util.Objects;

import systems.facts.Variable;


/**
 * La classe Expression permet de réprésenter les expressions.
 * Une expression peut être constituée uniquement d'une variable ({@link systems.facts.Variable}), comme par exemple <code>37</code>, ou <code>revenus</code>.
 * Ou elle peut être constituée de deux autres expressions, comme <code>revenus-impots</code> ou <code>2*3</code> ou <code>salaireMensuel * 12</code>.
 * Et la construction continue de manière récursive.
 */
public class Expression {

    /**Indique si l'expression est constituée uniquement d'une varibale (true) ou de deux autres expression (false)*/
    private boolean isVariable;
    /**La variable de l'expression, si elle est constituée uniquement d'une variable */
    private Variable<Float> variable;
    /**L'opérateur de l'expression (+,-,*,/) si elle n'est pas constituée uniquement d'une variable */
    private String operator;
    /**L'expression de gauche si elle n'est pas constituée uniquement d'une variable */
    private Expression left;
    /**L'expression de droite si elle n'est pas constituée uniquement d'une variable */
    private Expression right;

    /**
     * Construit une nouvelle expression constituée uniquement d'une variable.
     * @param variable la variable de l'expression
     */
    public Expression (Variable<Float> variable) {
        this.isVariable = true;
        this.variable = variable;
    }

    /**
     * Construit une nouvelle expression constituée de deux autres expressions et d'un opérateur.
     * @param left l'expression de gauche
     * @param operator l'opérateur de l'expression
     * @param right l'expression de droite
     */
    public Expression (Expression left, String operator, Expression right) {
        this.isVariable = false;
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    /**
     * Indique si l'expression est constituée uniquement d'une varibale (true) ou de deux autres expression (false).
     * @return un booléen indiquant si l'expression est une variable ou pas
     */
    public boolean isVariable () { return this.isVariable; }

    /**
     * Récupère la variable de l'expression.
     * @throws java.lang.NullPointerException si l'expression n'est pas constituée uniquement d'une variable
     * @return la variable de l'expression
     */
    public Variable<Float> getVariable () { return this.variable; }


    /**
     * Récupère l'opérateur de l'expression.
     * @throws java.lang.NullPointerException si l'expression est constituée uniquement d'une variable
     * @return l'opérateur de l'expression
     */
    public String getOperator () {
        return this.operator;
    }

    /**
     * Récupère l'expression de gauche de l'expression.
     * @throws java.lang.NullPointerException si l'expression est constituée uniquement d'une variable
     * @return l'expression de gauche de l'expression
     */
    public Expression getLeft () {
        return this.left;
    }

    /**
     * Récupère l'expression de droite de l'expression.
     * @throws java.lang.NullPointerException si l'expression est constituée uniquement d'une variable
     * @return l'expression de droite de l'expression
     */
    public Expression getRight () {
        return this.right;
    }


    /**
     * Evalue l'expression si cela est possible.
     * Pour que l'évaluation d'une expression soit possible, il faut que pour toute variable contenue dans l'expression, la méthode {@link systems.facts.Variable#isSet()} renvoie true
     * @throws java.lang.NullPointerException si l'évaluation n'est pas possible
     * @return l'évaluation de l'expression
     */
    public float evaluate () {
        if (!this.isVariable) {
            switch (this.operator) {
                case "+":
                    return this.getLeft().evaluate() + this.getRight().evaluate();
                case "-":
                    return this.getLeft().evaluate() - this.getRight().evaluate();
                case "*":
                    return this.getLeft().evaluate() * this.getRight().evaluate();
                default:
                    return this.getLeft().evaluate() / this.getRight().evaluate();
            }
        }
        return this.variable.getValue();
    }


    /**
     * Dis si deux expressions sont égales ou pas. Deux expressions sont égales si et seulement si :<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- elles sont toutes deux constituées d'une variable et leurs deux variables sont égales<br>
     *      &nbsp;&nbsp;&nbsp;&nbsp;- elles sont toutes deux constituées deux autres expression et leurs deux opérateurs sont égaux et leurs deux expressions sont égales (a+b = b+a = a+b)
     */
    @Override
    public boolean equals (Object other) {
        if (other instanceof Expression) {
            Expression otherAsExpression = (Expression) other;
            if (this.isVariable && otherAsExpression.isVariable()) {
                return this.variable.equals(otherAsExpression.getVariable());
            }
            if (!this.isVariable && !otherAsExpression.isVariable()) {
                return this.operator.equals(otherAsExpression.getOperator()) &&
                    (
                        this.left.equals(otherAsExpression.getLeft()) && this.right.equals(otherAsExpression.getRight()) ||
                        this.left.equals(otherAsExpression.getRight()) && this.right.equals(otherAsExpression.getLeft())
                    );
            }
        }
        return false;
    }

    @Override
    public int hashCode () {
        if (this.isVariable) {
            return Objects.hash(variable);
        }
        return Objects.hash(this.left, this.operator, this.right);
    }

    @Override
    public String toString () {
        return this.isVariable ? this.variable.toString() : this.left + this.operator + this.right;
    }

}