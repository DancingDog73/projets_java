package systems.fx;

import systems.base.*;
import systems.facts.*;
import systems.rules.*;
import systems.expertsystem.ExpertSystem;


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
 * Permet d'afficher les différents éléments contenus dans la base
 * @param HomePage home
 * @param Scene scene
 * @param MainInterface mainInterface
 * @param ExpertSystem system

 */

public class DisplayingBase extends BorderPane{
    private HomePage home;
    private Scene scene;
    private MainInterface mainInterface;
    public ExpertSystem system;

    public DisplayingBase(HomePage home, Scene scene, MainInterface mainInterface, ExpertSystem system){
        super();
        this.home = home;
        this.scene = scene;
        this.mainInterface = mainInterface;
        this.system = system;
        
        //Main scene element
        //Scene mainViewScene = new Scene(this.home);
        
        //Home components
        Label actionLabel = new Label("Consulter la base");
        HBox actionButtons = new HBox();
        //this.home.setBottom(actionButtons);
        
        //Adding components to scene
        this.setCenter(actionLabel);
        this.setBottom(actionButtons);

        //Styling Home & components positions
        this.setMargin(actionLabel, new Insets(120.0, 0.0, 0.0, 200.0));
        this.setMargin(actionButtons, new Insets(20.0, 0.0, 0.0, 200.0));
        actionButtons.setLayoutX(250.0);
        actionButtons.setLayoutY(120.0);
        //actionButtons.setVisible(false);
        this.setStyle("-fx-background-color: #093768;");
        //actionButtons.setStyle("-fx-background-color: #093768;");


        //Setting & Styling base buttons' & actionLabel
        this.setBaseButtons(actionButtons);
        actionLabel.setStyle("-fx-text-fill:teal; -fx-font-size: 20px; -fx-font-weight: 900;");

    }
    /**
     * Permet de définir les différents boutons permettant la consultation de la base
     */

    public void setBaseButtons(HBox actionButtons){

        actionButtons.getChildren().addAll(new Button("Consulter la base de faits"), new Button("Consulter la base de règles"), new Button("Consulter la base de connaissances"), new Button("Accueil"));

        for (Node button : actionButtons.getChildren()){
            Button btn = (Button) button;
            actionButtons.setMargin(btn, new Insets(0.0, 20.0, 0.0, 0.0));
            btn.setMaxSize(500, 400);
            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #ff9646;-fx-text-fill:#4245769; -fx-font-size: 20px; -fx-font-family: ubuntu; -fx-box-shadow: 60px -16px teal;"));
            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #f9b17a;-fx-text-fill:#4245769; -fx-font-size: 20px; -fx-font-family: ubuntu; -fx-box-shadow: 60px -16px teal;"));
            if (btn.getText().equals("Consulter la base de faits")){
                btn.setOnAction(e ->this.displayBaseFacts());
            } else if(btn.getText().equals("Consulter la base de règles")){
                btn.setOnAction(e -> this.displayRulesBase());
            } else if (btn.getText().equals("Consulter la base de connaissances")){
                btn.setOnAction(e -> this.displayKnowledgeBase());
            } else{
                btn.setOnAction(e -> this.goBackHome());
            }

            btn.setStyle("-fx-background-color: #f9b17a; -fx-text-fill:#4245769; -fx-font-size: 20px; -fx-font-family: ubuntu; -fx-box-shadow: 60px -16px teal;");
        }
    }
    /**
     * Permet de retourner à la page d'accueil
     */

    public void goBackHome(){
        this.scene.setRoot(this.home.setVBox());
    }

    /**
     *  Permet d'afficher l'ensemble des faits de la base
     */
    public void displayBaseFacts(){
        int j = 1;
        KnowledgeBase base = this.system.getKnowledgeBase();
        Text currentText = new Text();
        ScrollPane scrollPane = new ScrollPane(currentText);
        scrollPane.setFitToHeight(true);
        currentText.setFont(new Font("SansSerif", 15));
        currentText.setTextAlignment(TextAlignment.JUSTIFY);
        currentText.setLineSpacing(5.0);
        this.setCenter(scrollPane);
        scrollPane.setStyle("-fx-background-color: #093768;");

        String  str = "";
        for (Fact f : base.getFactsBase()){
            str += j + ". "  + f.toString() + "\n";
            j++;
        } 

        currentText.setText(str);
    }
    /**
     *  Permet d'afficher l'ensemble des faits de la base
     */

    public void displayRulesBase(){
        int i = 1;
        KnowledgeBase base = this.system.getKnowledgeBase();

        Text currentText = new Text();
        ScrollPane scrollPane = new ScrollPane(currentText);
        scrollPane.setFitToHeight(true);
        currentText.setFont(new Font("SansSerif", 15));
        currentText.setTextAlignment(TextAlignment.JUSTIFY);
        currentText.setLineSpacing(5.0);
        this.setCenter(scrollPane);
        String  str = "";
        for (Rule rule : base.getRulesBase()){
            str += i + ". Si " + rule.getPremise() + " alors " + rule.getConclusion() + '\n';
            i++;
        }
        
        currentText.setText(str);
    }
    /**
     *  Permet d'afficher la base de connaissances
     */

    public void displayKnowledgeBase(){
        int i = 1;
        int j = 1;
        
        KnowledgeBase base = this.system.getKnowledgeBase();

        String  strRules = "Base de règles : \n";
        String strFacts = "Base de faits : \n";
        HBox kBase = new HBox(15.0);
        
        ScrollPane scrollPane = new ScrollPane(kBase);
        scrollPane.setFitToHeight(true);

        for (Rule rule : base.getRulesBase()){
            strRules += i + ". Si " + rule.getPremise() + " alors " + rule.getConclusion() + '\n';
            i++;
        }

        for (Fact fact : base.getFactsBase()){
            if (fact instanceof BooleanFact){
                BooleanFact f = (BooleanFact) fact;
                strFacts =  strFacts + j + ". " +  f.getVariable() + '\n';
            } else if (fact instanceof NumericFact){
                NumericFact f = (NumericFact) fact;
                strFacts += j + ". "  + f.getVariable() + " = " + f.getValue() + '\n';
            }  else if (fact instanceof NanFact){
                NanFact f = (NanFact) fact;
                strFacts += j + ". "  + f.getVariable() + " = " + f.getValue() + '\n';

            }
            j++;
        }

        kBase.getChildren().addAll(new Text(strRules), new Text(strFacts));
        for (Node text : kBase.getChildren()){
            Text txt = (Text) text;
            txt.setFont(new Font("SansSerif", 15));
            txt.setTextAlignment(TextAlignment.JUSTIFY);
            txt.setLineSpacing(5.0);
        }

        this.setCenter(scrollPane);


    }

}