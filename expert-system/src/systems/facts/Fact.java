package systems.facts;


import java.util.Set;

/**
 * L'interface Fact sert à représenter tous les types de faits.
 */
public interface Fact {
    /**
     * Compare ce fait au fait spécifié. Le résultat est vrai si ce fait est équivalent au fait spécifié.
     * @param other est l'autre fait auquel nous comparons ce fait
     * @return true si le fait donné est équivalent à celui-ci, false sinon
     */
    public boolean areEquivalent(Fact other);

    /**
     * Vérifie si un fait est contradictoire à une base de faits.
     * @param factBase est la base que nous utilisons pour les comparaisons
     * @return true si ce Fait est contradictoire à la base donnée, false sinon
     */


    public boolean isContradictory(Set<Fact> factsBase);

    /**
     * Vérifie si ce fait est défini.
     * @return true si ce fait est défini ; faux sinon
     */
    public boolean isSet ();
    
}

