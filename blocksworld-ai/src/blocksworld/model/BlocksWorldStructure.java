package blocksworld.model;

import java.util.*;
import modelling.*;


public abstract class BlocksWorldStructure {

    protected int blocksCount;
    protected int stacksCount;

    public BlocksWorldStructure (int blocksCount, int stacksCount) {
        this.blocksCount = blocksCount;
        this.stacksCount = stacksCount;
    }


    //Permet de générer une variable sous la forme on(block) avec son domaine (les blocs qui peuvent être dessus)
    public Variable onBlockVariable (int block) throws IllegalArgumentException {
        if (block >= 0 && block < this.blocksCount) {
            Set<Object> domain = new HashSet<>();
            for (int component = -this.stacksCount; component < this.blocksCount; component+=1) {
                domain.add(component); //On met tous les blocs dans le domaine
            }
            domain.remove(block); //On retire le bloc lui-même car un bloc ne peur pas être posé sur lui-même
            return new Variable("on(" + block + ")", domain);
        }
        throw new IllegalArgumentException("Block " + block + " not present in this world !");
    }

    //Permet de générer une variable booléenne qui indique si un bloc est fixé
    public Variable blockFixedVariable (int block) throws IllegalArgumentException {
        if (block >= 0 && block < this.blocksCount) {
            return new BooleanVariable("fixed(" + block + ")");
        }
        throw new IllegalArgumentException("Block " + block + " not present in this world !");
    }

    //Permet de générer une variable booléenne qui indique si une pile est libre
    public Variable stackFreeVariable (int stack) throws IllegalArgumentException {
        if (stack > 0 && stack <= this.stacksCount) {
            return new BooleanVariable("free("+ stack + ")");
        }
        throw new IllegalArgumentException("Stack " + stack + " not present in this world !");
    }


}