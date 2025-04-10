package systems.parsers;

import java.nio.file.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;





/*
*** Configuration File Structure ***

Facts
    Intelligent
    Zoba
    Temperature > 5
    Tension < 20
    Profession est professeur
    Maison est belle
Formulas
    albedo = energieReflechie / energieIncidente

Known Rules
    Si fact1 alors fact2
    Si fact2 alors fact4
*/

/**
 * Permet de lire un fichier de configuration contenant une base de connaissances.
 * De celui-ci sont extraits bases de faits et de règles.
 * @param knowledgeBaseFilePath     Chemin vers le fichier de configuration.
 */

public class Reader{
    private String knowledgeBaseFilePath;
    
    public Reader(String knowledgeBaseFilePath){
        this.knowledgeBaseFilePath = knowledgeBaseFilePath;
    }
    /**
     * Permet d'extraire le chemin absolu du fichier de configuration
     * @require Il faut que le fichier existe.
     * @return  retourne la chaîne contenant le chemin absolu vers le fichier
     * @see     String
     * 
     */

    private String getAbsoluteBasePath(){
        Path filePath = Paths.get(this.knowledgeBaseFilePath);
        String absolutePath  = filePath.toAbsolutePath().toString();
        String[] splitPath = absolutePath.split("src/../../../");
        
        return splitPath[0] + splitPath[1];

    }
    /**
     * Lit le fichier de configuration qui contient la base de connaissances
     * @ensure  Lit le fichier de contenant la base passé en argument de la classe
     * @return  Retourne une <code>List<String></code> contenant chacune des lignes du fichier s'il n'est pas vide.
     * @see     List<String> 
     */

    public List<String> readBase(){
        File f = new File(this.getAbsoluteBasePath());
        try {
            FileInputStream input = new FileInputStream(f);
            int c;
            int i = 0;
            String line = "";
            ArrayList<String> lines = new ArrayList<> ();
            while((c = input.read()) != -1){
                // System.out.println((char) c);
                
                if ((char) c == '\n'){
                    i += 1;
                    lines.add(line);
                    line = "";
                }
                line += (char) c;
            }
            return lines;
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Va vérifier dans le fichier de configuration si la première ligne contient "Facts".
     * De là, toutes les lignes qui suivent sont censées être des faits seront ajoutés à la liste de faits.
     * @require Le fichier doit contenir en première ligne "Facts".
     * @return  Retourne l'ensemble des faits  contenus dans le fichier où se trouve la base sous forme de chaîne de caractères.
     * @see     List<String>
     */

    public List<String> getAllFacts(){
        List<String> base = this.readBase();        
        int i = 0;
        List<String> listFacts = new ArrayList<> ();
        
        while (!base.get(i).contains("Formulas")){
            if (!base.get(i).trim().isEmpty()){
                listFacts.add(base.get(i).trim());

            }
            i++;
        }
        
        return listFacts.subList(1, listFacts.size());
    }
    /**
     * Va vérifier dans le fichier de configuration si le fichier contient une ligne contenant  "Formulas".
     * De là, toutes les lignes qui suivent sont censées être des formules seront ajoutés à la liste de formules.
     * @require Le fichier doit contenir la ligne "Formulas".
     * @return  Retourne l'ensemble des formules  contenus dans le fichier où se trouve la base sous forme de chaîne de caractères.
     * @see     List<String>
     */

    public List<String> getAllFormulas(){
        List<String> base = this.readBase();
        List<String> formulas = new ArrayList<> ();
        int i = 0;
        int j = 0;
        while (!base.get(i).contains("Known Rules")){
            if (base.get(i).contains("Formulas")){
                j = i;
            }
                
            i++;
        }
        
        for (int k = j+1; k < i; k++){
            if (!base.get(k).trim().isEmpty()){
                formulas.add(base.get(k).trim());
            }
        }


        return formulas;
        
    }

    /**
     * Va vérifier dans le fichier de configuration si le fichier contient une ligne contenant  "Known Rules".
     * De là, toutes les lignes qui suivent sont censées être des règles seront ajoutés à la liste de règles.
     * @require Le fichier doit contenir la ligne "Known Rules".
     * @return  Retourne l'ensemble des règles  contenus dans le fichier où se trouve la base sous forme de chaîne de caractères.
     * @see     List<String>
     */

      public List<String> getAllRules(){
        List<String> base = this.readBase();
        List<String> rules = new ArrayList<> ();
        int i = 0;
        while (i < base.size()){
            if (base.get(i).contains("Known Rules")) {
                break;
            }
            i++;
        }

        for(int j = i+1; j < base.size(); j++){
            if(!base.get(j).trim().isEmpty()){
                rules.add(base.get(j).trim());
            }
        }



        return rules;
    }

    
}
