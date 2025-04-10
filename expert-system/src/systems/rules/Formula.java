package systems.rules;

import java.util.Objects;

import systems.facts.Variable;


/**
 * La classe Formula permet de représenter les formules. Elles sont constituées d'un nom en String et d'une {@link systems.rules.Expression}.<br>
 * Des exemples de formules :<br>
 *      &nbsp;&nbsp;&nbsp;&nbsp;temperature = 37<br>
 *      &nbsp;&nbsp;&nbsp;&nbsp;pi = 22 / 7<br>
 *      &nbsp;&nbsp;&nbsp;&nbsp;pauvrete = naissance - chance<br>
 *      &nbsp;&nbsp;&nbsp;&nbsp;argent = liberte<br>
 *      &nbsp;&nbsp;&nbsp;&nbsp;revenus = salaireMensuel * 12 + primes - impots
 */
public class Formula {

    /**Le nom de la formule */
    private String name;
    /**L'expression de la formule */
    private Expression expression;

    /**
     * Créé une nouvelle formule à partir de son nom et de son expression.
     * @param name le nom de la formule
     * @param expression l'expression de la formule
     */
    public Formula (String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    /**
     * Récupère le nom de la formule.
     * @return le nom de la formule
     */
    public String getName () { return this.name; }

    /**
     * Récupère l'expression de la formule.
     * @return l'expression de la formule
     */
    public Expression getExpression () { return this.expression; }

    /**
     * Evalue la formule pour récupérer sa valeur, si cela est possible. L'évaluation est possible si pour toute variable ({@link systems.facts.Variable}) appartenant à son expression, la méthode {@link systems.facts.Varibale#isSet()} retourne true
     * Voir pour plus de détails {@link systems.rules.Expression#evaluate()}
     * @throws java.lang.NullPointerException si l'évaluation n'est pas possible
     * @return l'évaluation de la formule
     */
    public float evaluate () { return this.expression.evaluate(); }


    /**
     * Deux formules sont égales si et seulement si leurs deux noms sont égaux et leurs deux formules sont égales.
     */
    @Override
    public boolean equals (Object other) {
        if (other instanceof Formula) {
            Formula otherAsFormula = (Formula) other;
            return this.name.equals(otherAsFormula.getName()) && this.expression.equals(otherAsFormula.getExpression());
        }
        return false;
    }

    @Override
    public int hashCode () {
        return Objects.hash(this.name, this.expression);
    }

    @Override
    public String toString () {
        return this.name + " = " + this.expression;
    }

}