package systems.facts;

import java.util.Set;
import java.util.HashSet;

/**
 * La classe CompositeFact sert à représenter les faits qui sont des conjonctions, des dijonctions ou des exclusions
 */
public abstract class CompositeFact implements Fact {
    /**
     * Ce fait est le premier fait de ce fait composé
     */
    protected Fact oneFact;
    /**
     * Ce fait est le second fait de ce fait composé
     */
    protected Fact anotherFact;

    /**
     * Construit un CompositeFact nouvellement alloué qui représente une conjonction, une disjonction ou une exclusion entre deux faits.
     * @param oneFact est une instance de Fact
     * @param anotherFact est une instance de Fact
     */
    public CompositeFact (Fact oneFact, Fact anotherFact) {
        this.oneFact = oneFact;
        this.anotherFact = anotherFact;
    }

    /**
     * Renvoie un ensemble avec les deux faits de ce CompositeFact.
     * @return un ensemble avec les deux faits de ce CompositeFact
     */
    public Set<Fact> getFacts () {
        Set<Fact> facts = new HashSet<>();
        facts.add(this.oneFact);
        facts.add(this.anotherFact);
        return facts;
    }

    /**
     * Renvoie le premier fait de ce CompositeFact.
     * @return le premier fait de ce CompositeFact
     */
    public Fact getOneFact(){
        return this.oneFact;
    }

    /**
     * Renvoie le deuxième fait de ce CompositeFact.
     * @return le deuxième fait de ce CompositeFact
     */
    public Fact getAnotherFact(){
        return this.anotherFact;
    }

    /**
     * Vérifie si les deux faits de ce CompositeFact ne sont pas nuls.
     * @return true si si les deux faits de ce CompositeFact ne sont pas nuls, false sinon
     */
    @Override
    public boolean isSet () {
        return this.oneFact.isSet() && this.anotherFact.isSet();
    }

    /**
     * Vérifier si un CompositeFact est contradictoire avec une base de faits.
     * @param factBase est la base que nous utilisons pour les comparaisons
     * @return true si ce CompositeFact est contradictoire avec la base donnée, false sinon
     */
    @Override
    public boolean isContradictory(Set<Fact> factsBase){
        return true;
    }

}