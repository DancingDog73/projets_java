package tests;

import tests.systems.base.*;
import tests.systems.parsers.*;
import tests.systems.facts.*;
import tests.systems.rules.*;


public class Tester {

    public static void main (String[] args) {

        boolean allTestsPassed = true;

        System.out.println();

        System.out.println("READER TESTER");
        ReaderTester readerTester = new ReaderTester();
        allTestsPassed = allTestsPassed && readerTester.testAll();
        System.out.println();

        System.out.println("PARSER TESTER");
        ParserTester parserTester = new ParserTester();
        allTestsPassed = allTestsPassed && parserTester.testAll();
        System.out.println();

        System.out.println("OBJECTS CREATOR TESTER");
        ObjectsCreatorTester godTester = new ObjectsCreatorTester();
        allTestsPassed = allTestsPassed && godTester.testAll();
        System.out.println();

        System.out.println("KNOWLEDGEBASE TESTER");
        KnowledgeBaseTester knowledgeBaseTester = new KnowledgeBaseTester();
        allTestsPassed = allTestsPassed && knowledgeBaseTester.testAll();
        System.out.println();

        System.out.println("VARIABLE TESTER");
        VariableTester variableTester = new VariableTester();
        allTestsPassed = allTestsPassed && variableTester.testAll();
        System.out.println();

        System.out.println("FACTS TESTER");
        FactsTester factsTester = new FactsTester();
        allTestsPassed = allTestsPassed && factsTester.testAll();
        System.out.println();

        System.out.println("RULES TESTER");
        RuleTester ruleTester = new RuleTester();
        allTestsPassed = allTestsPassed && ruleTester.testAll();
        System.out.println();

        System.out.println("FORMULAS TESTER");
        FormulaTester formulaTester = new FormulaTester();
        allTestsPassed = allTestsPassed && formulaTester.testAll();
        System.out.println();


        System.out.println(allTestsPassed ? "All tests passed" : "At least one test failed");

        System.out.println();

    }

}