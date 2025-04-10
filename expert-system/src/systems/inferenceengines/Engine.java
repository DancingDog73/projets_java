package systems.inferenceengines;
import systems.facts.*;
import systems.base.*;
import systems.rules.*;
import systems.parsers.*;
import java.util.*;

/**
 * La classe Engine sert à représenter un moteur d'inférence 
 */
public class Engine{

    /**
     * Cette base de connaissance sera celle qui sera utilisée pour les chainages
     */
    private KnowledgeBase base;
    private Set<Fact> addedFacts ;

    /**
     * Construit un moteur nouvellement alloué.
     * @param base est une base de connaissances que ce moteur utilisera
     */
    public Engine(KnowledgeBase base){
        this.base = base;
        Set<Fact> addedFacts = new HashSet<>();
        this.addedFacts = addedFacts;

    }

    /**
     * Renvoie la base de connaissances de ce moteur.
     * @return la base de connaissances de ce moteur
     */
    public KnowledgeBase getBase(){
        return this.base;
    }

    
    /**
     * Applique un forwardChaining sur la base de connaissances de ce moteur.
     */
    public void forwardChaining(){
        while(true){
            List<Rule> triggerable = new ArrayList<>();
            for (Rule r: this.base.getRulesBase()){
                if (this.base.isFactKnown(r.getPremise())){
                    if (!(this.base.getFactsBase().contains(r.getConclusion()) && !(r.getPremise().isContradictory(this.base.getFactsBase())))){
                        triggerable.add(r);
                    }
                }
            }
            if (triggerable.size() == 0){
                break;
            }
            for (Rule r: triggerable){
                this.base.addFact(r.getConclusion());
                this.addedFacts.add(r.getConclusion());
            }
        }
    }

    /**
     * Vérifie si un fait peut être obtenu par la base de connaissances de ce moteur.
     * @param f est l'objectif que nous essayons de prouver
     * @return true si un fait peut être atteint par la base de connaissances de ce moteur, false sinon
     */
    public boolean backwardChaining(Fact f){
        for (Fact fa: this.base.getFactsBase()){
            if (f.areEquivalent(fa)){
                return true;
            }
        }

        if (f instanceof ConjunctionFact){
            ConjunctionFact other = (ConjunctionFact) f;
            return backwardChaining(other.getOneFact()) && backwardChaining(other.getAnotherFact());
        } else if (f instanceof DisjunctionFact){
            DisjunctionFact other = (DisjunctionFact) f;
            return backwardChaining(other.getOneFact()) || backwardChaining(other.getAnotherFact());
        } else if  (f instanceof ExclusionFact){
            ExclusionFact other = (ExclusionFact) f;
            return (backwardChaining(other.getOneFact()) && !backwardChaining(other.getAnotherFact())) || (!backwardChaining(other.getOneFact()) && backwardChaining(other.getAnotherFact()));
        }

        List<Fact> conflict = new ArrayList<>();
        for(Rule r: this.base.getRulesBase()){
            if(f.equals(r.getConclusion())  && !(r.getPremise().isContradictory(this.base.getFactsBase()))){
                conflict.add(r.getPremise());
            }
        }
        if (conflict.size() == 0){
            return false;
        }
        return backwardChaining(conflict.get(0));
    }

    /**
     * Renvoie une liste des faits ajoutés lors du forwardChaining.
     * @return une liste des faits ajoutés lors du forwardChaining
     */
    public Set<Fact> getAddedFacts(){
        return this.addedFacts;
    }

    public static void main (String[] argv){
        
        KnowledgeBase B = new KnowledgeBase("../../../res/bases/sante.txt");
        Engine E = new Engine(B);
        ObjectsCreator G = new ObjectsCreator();
        Parser P = new Parser();

        System.out.println(E.getBase().getRulesBase());
        /*System.out.println(E.getBase().getFactsBase());
        System.out.println();
        System.out.println(E.getBase().getRulesBase());
        System.out.println(E.getBase().getFactsBase());*/
        E.forwardChaining();
        /*System.out.println(E.getBase().getRulesBase());
        System.out.println(E.getBase().getFactsBase());*/
        System.out.println(E.getAddedFacts());
        System.out.println(B.getFactsBase());
        for (Formula formula : B.getFormulas()) {
            System.out.println(formula + " : " + B.isFormulaApplicable(formula));
        }

    }

}