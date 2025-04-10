package systems.fx;

import systems.expertsystem.ExpertSystem;
import systems.base.*;
import systems.facts.*;
import systems.rules.*;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.layout.*;
import javafx.scene.*;

import javafx.scene.control.*;

import javafx.event.*;

import javafx.geometry.Insets;
import javafx.scene.text.Font;

/**
 * Permet d'afficher la page d'accueil de l'interface 
 * @param MainInterface 
 * @param Scene
 * @param ExpertSystem
 */

public class HomePage extends VBox {
    MainInterface mainInterface;
    public Scene scene;
    public ExpertSystem system;
    
    public HomePage(double spacing, Scene scene, MainInterface mainInterface, ExpertSystem system){
        super(spacing);
        this.scene = scene;
        this.mainInterface = mainInterface;
        this.system = system;
    }

    public HomePage(Scene scene, MainInterface mainInterface,  ExpertSystem system) { 
        this(10.0, scene, mainInterface, system);
        this.scene = scene;
        this.mainInterface = mainInterface;
        this.system = system;
        

    }
    /**
     * Définit la scène de l'accueil
     */

    public void setHomeScene(Scene sc){
        this.scene = sc;
    }

    /**
     * Retourne le Composant (Vertical) principal de la Page d'accueil
     * @return Retourne le Composant (Vertical) principal de la Page d'accueil
     */
    
    public VBox setVBox(){
    
        VBox home = new VBox(); //Main scene element
        //Scene mainViewScene = new Scene(home);
        
        //Home components
        Label actionLabel = new Label("Sélectionnez une action");
        HBox actionButtons = new HBox();
        actionButtons.getChildren().addAll(new Button("Consulter la base"), new Button("Intéragir avec la base"));
        
        //Adding components to scene
        home.getChildren().addAll(actionLabel, actionButtons);

        //Styling Home & components positions
        home.setMargin(actionLabel, new Insets(120.0, 0.0, 0.0, 200.0));
        home.setMargin(actionButtons, new Insets(20.0, 0.0, 0.0, 200.0));
        actionButtons.setLayoutX(250.0);
        actionButtons.setLayoutY(120.0);
        //actionButtons.setVisible(false);
        home.setStyle("-fx-background-color: #093768;");

        //Styling actionsButtonChildren & label
        //-> Buttons
        for (Node button : actionButtons.getChildren()){
            Button btn = (Button) button;
            actionButtons.setMargin(button, new Insets(0.0, 20.0, 0.0, 0.0));
            btn.setMaxSize(500, 400);
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #ff9646;-fx-text-fill:#4245769; -fx-font-size: 20px; -fx-font-family: ubuntu; -fx-box-shadow: 60px -16px teal;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #f9b17a;-fx-text-fill:#4245769; -fx-font-size: 20px; -fx-font-family: ubuntu; -fx-box-shadow: 60px -16px teal;"));
            
            if (btn.getText().equals("Consulter la base")){ 
                btn.setOnAction(e -> this.scene.setRoot(this.switchToBase()));
            } else {
                btn.setOnAction(e -> this.scene.setRoot(this.switchToInteraction()));
            }

            button.setStyle("-fx-background-color: #f9b17a; -fx-text-fill:#4245769; -fx-font-size: 20px; -fx-font-family: ubuntu; -fx-box-shadow: 60px -16px teal;");


        }
        
        actionLabel.setStyle("-fx-text-fill:teal; -fx-font-size: 20px; -fx-font-weight: 900;");
    
        return home;
    }
    /**
     * retourne une DisplayingBase vers laquelle on va déplacer la scène
     * @return retourne une DisplayingBase vers laquelle on va déplacer la scène
     */

    public DisplayingBase switchToBase(){
        return new DisplayingBase(this, this.scene, this.mainInterface, this.system);
    }

    /**
    * retourne une DisplayingBase vers laquelle on va déplacer la scène
    * @return retourne une DisplayingBase vers laquelle on va déplacer la scène
    */

    public Interaction switchToInteraction(){
        return new Interaction(this, this.scene, this.mainInterface, this.system);
    }

    

}