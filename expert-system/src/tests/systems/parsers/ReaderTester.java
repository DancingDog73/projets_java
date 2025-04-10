package tests.systems.parsers;

import systems.parsers.*;
import java.util.*;

public class ReaderTester{
    public Reader readerTestBase = new Reader("../../../res/testsbases/TestBase1.txt");

    public boolean testAll () {
        boolean results = true;
        results = results && this.testImplements();
        results = results && this.testReadBase();
        results = results && this.testGetAllFacts();
        results = results && this.testGetAllFormulas();
        results = results && this.testGetAllRules();
        return results;
    }

    public boolean testImplements(){
        System.out.println("[Tests] [ReaderTester::testImplements] launched");
            if (!(this.readerTestBase instanceof Reader)){
                System.out.println("[Tests] [ReaderTester::testImplements] failed");
                return false;

            }
        System.out.println("[Tests] [ReaderTester::testImplements] passed");
        return true;

    }

    public boolean testReadBase(){
        System.out.println("[Tests] [ReaderTester::testReadBase] launched");

        try{
            this.readerTestBase.readBase();
            System.out.println("[Tests] [ReaderTester::testReadBase] passed");
            return true;

        } catch (Exception e){
            System.out.println("[Tests] [ReaderTester::testReadBase] failed");
            return false; 

        }
    }


    public boolean testGetAllFacts(){
        System.out.println("[Tests] [ReaderTester::testGetAllFacts] launched");

        List<String> facts = new ArrayList<> (List.of( "chaud", "soilleux et invivable", "temperature = 50","pluie = 10", "air est chaud", "soleil est auZenith", "liberte = 10000000000000000", "salaire = 1000", "primes = 300", "impots = 5000", "j = 1", "m = 4", "c = 5"));
        if (this.readerTestBase.getAllFacts().equals(facts)){
            System.out.println("[Tests] [ReaderTester::testGetAllFacts] passed");
            return true;

        } else {
        

        System.out.println("[Tests] [ReaderTester::testGetAllFacts] failed");
        return false;
        }
    }

    public boolean testGetAllFormulas(){

        System.out.println("[Tests] [ReaderTester::testGetAllFormulas] launched");
        List<String> formulas = new ArrayList<> (List.of("conditions = temperature / pluie", "argent = liberte", "revenus = salaire*12+primes-impots", "i = j+1", "e = m*c", "temperatureD = 37"));
    
        if (this.readerTestBase.getAllFormulas().equals(formulas)){
                System.out.println("[Tests] [ReaderTester::testGetAllFormulas] passed");
                return true;
            }

        System.out.println("[Tests] [ReaderTester::testGetAllFormulas] failed");
        return false;
        
    }

    public boolean testGetAllRules(){
        System.out.println("[Tests] [ReaderTester::testGetAllRules] launched");
        List<String> rules = new ArrayList<> (List.of("Si temperature > 50 alors chaud", "Si chaud alors soleil est auZenith", "Si air est chaud alors invivable"));



        if (this.readerTestBase.getAllRules().equals(rules)){
            System.out.println("[Tests] [ReaderTester::testGetAllRules] passed");
            return true;
        } 

        
        System.out.println("[Tests] [ReaderTester::testGetAllRules] failed");
        return false;

        
        
    }


}
