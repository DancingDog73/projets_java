*Commandes pour *

    -> compiler MainInterface avec les modules javafx en plus depuis le dossier src
        javac --module-path ../../bakeloula-kagbere-khadiatou-yendoumban/lib/javafx-sdk-17.0.10/lib/ --add-modules javafx.controls  -d ../build/ systems/fx/MainInterface.java

    -> exécuter MainInterface avec les modules javafx en plus depuis le dossier src
        java --module-path ../../bakeloula-kagbere-khadiatou-yendoumban/lib/javafx-sdk-17.0.10/lib/ --add-modules javafx.controls  -cp ../build/ systems.fx.MainInterface
    
    -> Exécuter le fichier .jar depuis la racine du projet
        java --module-path lib/javafx-sdk-17.0.10/lib/ --add-modules javafx.controls -jar systems-ant.jar 
    
    -> Compiler les tests depuis le répertoire src  
        javac -d ../build/ tests/Tester.java
    
    -> Exécuter les tests depuis le répertoire src  
        java -cp ../build/ tests.Tester

*Problème rencontré avec le fichier .jar*
    On a rencontré un problème qui est que quand on veut exécuter la classe principale depuis le fichier jar,
    Le programme ne retrouve pas le fichier de configuration qui doit exécuté. On essayé de rajouter celui-ci 
    Mais force est de constater que cela ne fonctionne toujours pas.


