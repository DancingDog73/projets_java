package systems.fx;

/*
Compilation : 
javac --module-path ../../bakeloula-kagbere-khadiatou-yendoumban/lib/javafx-sdk-17.0.10/lib/ --add-modules javafx.controls  -d ../build/ systems/fx/MainInterface.java
Exécution : 
java --module-path ../../bakeloula-kagbere-khadiatou-yendoumban/lib/javafx-sdk-17.0.10/lib/ --add-modules javafx.controls  -cp ../build/ systems.fx.MainInterface
*/

import systems.expertsystem.ExpertSystem;
import systems.base.*;
import systems.facts.*;
import systems.rules.*;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.text.*;

import javafx.scene.control.*;

import javafx.event.*;

import javafx.geometry.Insets;
import javafx.scene.text.Font;
/**
 * Permet l'affichage de l'interface de bienvenue du système
 * @param ExpertSystem system
 * @param HomePage home
 * @param Scene mainScene
 */

public class MainInterface extends Application {
    public ExpertSystem system = new ExpertSystem();
    public HomePage home  = new HomePage(null, this, system);
    public Scene mainScene = new Scene(home.setVBox());  
    

    @Override
    public void start(Stage stage) {
        // Créer une VBox pour contenir les boutons du menu
        home.setHomeScene(mainScene);


        // Créer la scène et l'afficher
        //Scene scene = new Scene(menuBar, 150, 400); // Ajuster la taille selon vos besoins
        stage.centerOnScreen();
        stage.setWidth(950);
        stage.setHeight(400);
        stage.setScene(mainScene);
        //stage.setTitle(this.setStageTitle(home));
        stage.show();
    }
    /**
     * @return Retourne la Scène associée à la classe.
     */
    public Scene getScene(){
        return mainScene;
    } 

    public static void main(String[] args) {
        launch(args);
    }

    //public switchScenes()
}
