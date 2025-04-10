package blocksworld.model;

import java.util.*;
import modelling.*;
import planning.*;


public class WorldActions extends BlocksWorldStructure {


    public WorldActions (int blocksCount, int stacksCount) {
        super(blocksCount, stacksCount);
    }

    
    // Déplacer un bloc d'un bloc vers un autre bloc 
    public Action moveBtoB (int block, int base, int destination) {
        return this.move(
            block,
            base, this.blockFixedVariable(base), false,
            destination, this.blockFixedVariable(destination), true,
            "Déplacer le block " + block + " du block " + base + " vers le block " + destination
        );
    }

    // Déplacer un bloc d'un bloc vers une pile
    public Action moveBtoS (int block, int base, int destination) {
        return this.move(
            block,
            base, this.blockFixedVariable(base), false,
            -destination, this.stackFreeVariable(destination), false,
            "Déplacer le block " + block + " du block " + base + " vers la pile " + destination
        );
    }

    // Déplacer un bloc d'une pile vers un bloc 
    public Action moveStoB (int block, int base, int destination) {
        return this.move(
            block,
            -base, this.stackFreeVariable(base), true,
            destination, this.blockFixedVariable(destination), true,
            "Déplacer le block " + block + " de la pile " + base + " vers le block " + destination
        );
    }

    //Déplacer un bloc d'une pile vers une pile
    public Action moveStoS (int block, int base, int destination) {
        return this.move(
            block,
            -base, this.stackFreeVariable(base), true,
            -destination, this.stackFreeVariable(destination), false,
            "Déplacer le block " + block + " de la pile " + base + " vers la pile " + destination
        );
    }

    //Cette Méthode permet de créer chaque action
    public Action move (int block, int base, Variable baseAvailable, boolean baseAvailableValue, int destination, Variable destinationAvailable, boolean destinationAvailableValue, String description) {
        
        Map<Variable,Object> preconditions = new HashMap<>();
        Map<Variable,Object> effects = new HashMap<>();
        Variable onBlock = this.onBlockVariable(block);
        Variable blockFixedVariable = this.blockFixedVariable(block);
        
        preconditions.put(onBlock, base); //Il faut que la base du bloc correspndent à celle qui est indiquée à la méthode
        preconditions.put(blockFixedVariable, false); //Il faut que le bloc soit déplaçable (pas fixé)
        preconditions.put(destinationAvailable, !destinationAvailableValue); 
        //Ensuite lorsque le bloc peut être déplacé, on modifie le monde
        effects.put(onBlock, destination);
        effects.put(baseAvailable, baseAvailableValue);
        effects.put(destinationAvailable, destinationAvailableValue);
        
        return new LabeledAction(preconditions, effects, 1, description); 
    }

    //Génère toutes les actions possibles
    public Set<Action> getActions () {

        Set<Action> actions = new HashSet<>();
        //Pour chaque triplet de blocs b1,b2,b3, on crée l'action qui consiste à déplacer b1 de b2 vers b3
        for (int firstBlock = 0; firstBlock < this.blocksCount; firstBlock += 1) {
            for (int secondBlock = 0; secondBlock < this.blocksCount; secondBlock += 1) {
                for (int thirdBlock = 0; thirdBlock < this.blocksCount; thirdBlock += 1) {
                    if (firstBlock != secondBlock && firstBlock != thirdBlock && secondBlock != thirdBlock) {
                        actions.add(this.moveBtoB(firstBlock, secondBlock, thirdBlock));                        
                    }
                }
            }
        }

        //Pour deux bloc b1,b2 et un pile p on déplace b1 de b2 vers p et on déplace b1 de p vers b2
        for (int firstBlock = 0; firstBlock < this.blocksCount; firstBlock += 1) {
            for (int secondBlock = 0; secondBlock < this.blocksCount; secondBlock += 1) {
                if (firstBlock != secondBlock) {
                    for (int stack = 1; stack <= this.stacksCount; stack += 1) {
                        actions.add(this.moveBtoS(firstBlock, secondBlock, stack));
                        actions.add(this.moveStoB(firstBlock, stack, secondBlock));
                    }
                }
            }
        }

        //Enfin, Pour un bloc b et deux piles p1 et p2 on crée l'action qui consiste à déplacer b de p1 vers p2
        for (int firstStack = 1; firstStack <= this.stacksCount; firstStack += 1) {
            for (int secondStack = 1; secondStack <= this.stacksCount; secondStack += 1) {
                if (firstStack != secondStack) {
                    for (int block = 0; block < this.blocksCount; block += 1) {
                        actions.add(this.moveStoS(block, firstStack, secondStack));
                    }
                }
            }
        }

        return actions;

    }


    @Override
    public String toString () {
        String representation = "BLOCKS WORLD ACTIONS {blocksCount: " + this.blocksCount + ", stacksCount: " + this.stacksCount + "}\n";
        for (Action action : this.getActions()) {
            representation += "\t" + action + "\n";
        }
        return representation.trim();
    }

}