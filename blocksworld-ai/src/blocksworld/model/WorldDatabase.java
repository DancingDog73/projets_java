package blocksworld.model;

import modelling.*;
import java.util.*;


public class WorldDatabase extends BlocksWorldStructure{

    public WorldDatabase(int blocksCount, int stacksCount){
        super(blocksCount, stacksCount);
    }

    //Permet de générer toutes les variables boolénennes destinées à l'extraction des motifs
    public Set<BooleanVariable> getBooleanVariables(){
        Set<BooleanVariable> variables = new HashSet<>();
        for (int block = 0; block < this.blocksCount; block += 1) {
            //Pour chaque bloc on crée la variable booléenne fixed qui dit si il est fixé
            variables.add((BooleanVariable) this.blockFixedVariable(block));
            //Pour chaque paire de blocs, on crée la variable onb1,b2
            for(int secondBlock = 0; secondBlock < this.blocksCount; secondBlock++){
                if(secondBlock != block){
                    variables.add(new BooleanVariable("on"+block+","+secondBlock));
                }
            }
            //Pour chaque pile et chaque bloc on créé on-tableb,p
            for (int stack = 1; stack <= this.stacksCount; stack += 1) {
                variables.add(new BooleanVariable("on-table"+block+","+ -stack));

            }
        }
        //Pour chaque pile on crée la variable booléenne free qui dit si elle est libre
        for (int stack = 1; stack <= this.stacksCount; stack += 1) {
            variables.add((BooleanVariable) this.stackFreeVariable(stack));
            
        }
        
        return variables;
    }

    //Génère les variables correspondant à un état donné
    public Set<BooleanVariable> getTransactions(List<List<Integer>> transactions){
        Set<BooleanVariable> res = new HashSet<>();
        for(int i=0; i<transactions.size(); i++){
            int size = transactions.get(i).size();
            if(size == 0){
                res.add((BooleanVariable) this.stackFreeVariable(i+1)); //Si la sous-liste est vide alors  la pile correspondante est vide
            }else {
                for(int j=0; j<size; j++){

                    if(j==0){
                        int position = i+1;
                        res.add(new BooleanVariable("on-table"+transactions.get(i).get(j)+","+ position)); //On place le premier élément de la sous-liste sur la table
                    }
                    if(j > 0){
                        // Si ce n'est pas la première valeur on crée la variable  oni,i-1 et on ajoute que i-1 est fixe;
                        res.add(new BooleanVariable("on"+transactions.get(i).get(j)+","+transactions.get(i).get(j-1)));
                        res.add((BooleanVariable) this.blockFixedVariable(transactions.get(i).get(j-1)));
                    }
                    
                }
            }
            
        }
        
        return res;
    }
    
}
