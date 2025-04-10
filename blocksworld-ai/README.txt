    KAGBERE Tchonchoko 22206473
    

L'archive contient le code du rendu dans src/ à sa racine. 

Compiler et exécuter les démos :

    javac -d ../build blocksworld/demos/cli/DemoModel.java
    java -cp ../build blocksworld.demos.cli.DemoModel

    javac -d ../build blocksworld/demos/cli/DemoModelling.java
    java -cp ../build blocksworld.demos.cli.DemoModelling

    javac -d ../build blocksworld/demos/cli/DemoPlanning.java
    java -cp ../build blocksworld.demos.cli.DemoPlanning

    javac -d ../build blocksworld/demos/cli/DemoCP.java
    java -cp ../build blocksworld.demos.cli.DemoCP

    javac -d ../build -cp .:../lib/bwgenerator.jar blocksworld/demos/cli/DemoDatamining.java
    java -cp ../build:../lib/bwgenerator.jar blocksworld.demos.cli.DemoDatamining

    javac -d ../build/ -cp .:../lib/blocksworld.jar blocksworld/demos/gui/GUIRepresentation.java
    java -cp ../build:../lib/blocksworld.jar blocksworld.demos.gui.GUIRepresentation

    javac -d ../build/ -cp .:../lib/blocksworld.jar blocksworld/demos/gui/GUIPlanning.java
    java -cp ../build:../lib/blocksworld.jar blocksworld.demos.gui.GUIPlanning


IMPORTANT, pour réaliser les tests, il faut nécessairement :
    avoir les librairies blocksworld.jar et bwgenerator.jar dans un dossier lib/ au même niveau de la structure que src/
