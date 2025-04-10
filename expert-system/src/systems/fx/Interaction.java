package systems.fx; 


import systems.expertsystem.ExpertSystem;
import systems.base.*;
import systems.facts.*;
import systems.rules.*;
import systems.parsers.*;
import systems.inferenceengines.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.ObservableList;


import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.text.*;

import javafx.scene.control.*;

import javafx.event.*;

import javafx.geometry.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
/**
 * Permet d'afficher l'interface permettant l'interaction avec la base
 * @param HomePage home
 * @param Scene scene
 * @param MainInterface mainInterface
 * @param ExpertSystem system
 * @param ObservableList components
 * @param Label inferenceTextZone
 */

public class Interaction extends BorderPane{
    private HomePage home;
    private Scene scene;
    private MainInterface mainInterface;
    private ExpertSystem system = new ExpertSystem();
    private ObservableList components;
    private Label inferenceTextZone = new Label();
    
    private HBox interactionContainer = new HBox(15.0);

    TextField textField = new TextField();
    Label answer  = new Label();
    
    //private MenuContainer menu = new MenuContainer(10);
/*    private Button addFact = new Button("Ajout Fait");
    private Button addRule = new Button("Ajout Règle");
    private Button dropFact = new Button("Suppression Fait");
    private Button dropRule = new Button("Suppression Règle");
*/
    private List<Button> buttons = new ArrayList<> ();

    public Interaction(HomePage home, Scene scene, MainInterface mainInterface, ExpertSystem system){
        this.home = home;
        this.scene = scene;
        this.mainInterface = mainInterface;
        this.system = system;

        this.interactionContainer = interactionContainer;
        this.textField = textField;
        this.answer = answer;
        this.inferenceTextZone = inferenceTextZone;
        this.buttons = buttons;

        
        this.setPane();
    }

    /**
     * permet de retourner sur la page d'accueil
     */

    public void goBackHome(){
        this.scene.setRoot(this.home.setVBox());
    }

    /**
     * ensure permet d'initialiser les boutons différents boutons
     */

    public void initializeActionButtons(){
        String[] buttonsLabel = {"Questionner la base", "Ajout Fait", "Ajout Règle", "Suppression Fait", "Suppression Règle"};
        for(int i = 0; i < 5; i++){
            Button currentBtn = new Button(buttonsLabel[i]);
            this.buttons.add(currentBtn);
            this.interactionContainer.getChildren().add(currentBtn);

            //Styling Button
            this.setButtonStyle(currentBtn);
        } 

        this.handlingButtons();  
    }

    /**
     * permet de définir différentes actions sur les différents boutons
     */

    /**
     * Permet de définir des handlers sur les différents boutons
     */

    public void handlingButtons(){
        for (Button btn : this.buttons){
            if (btn.getText().equals("Ajout Fait")){
                btn.setOnAction(e -> this.addFact());
            } else if (btn.getText().equals("Suppression Fait")){
                btn.setOnAction(e -> this.dropFact());
            } else if (btn.getText().equals("Ajout Règle")){
                btn.setOnAction(e -> this.addRule());
            } else if (btn.getText().equals("Suppression Règle")){
                btn.setOnAction(e -> this.dropRule());
            } else if(btn.getText().equals("Questionner la base")){
                btn.setOnAction(e -> System.out.println(this.isIntheBase()));
            }
        }
    
    }
    /**
     * Permet d'afficher le texte de description du système
     */


    public void setCenterText(){
        String centerText = "";
        centerText += "Bienvenue sur la fenêtre interactive du Système expert.\n";
        centerText += "Les  2 princlipaux types d'actions sont :\n ";
        centerText += "> L'inférence de nouveaux faits à partir de la base de connaissances\n ";
        centerText +=    "   > La modification de la base existante\n ";
        centerText +=        "       * Ajout d'un nouveau fait\n";
        centerText +=         "       * Suppression d'un fait existant\n";
        centerText +=         "       * Ajout d'une nouvelle règle\n" ;
        centerText +=         "       * Suppression d'une règle existante\n ";
        centerText += "Grammaire\n " ;
        centerText +=   "    > D'un fait\n  :";
        centerText +=        "       * Booléen   : intelligent, non intelligent\n ";
        centerText +=         "       * Numérique : quatre < 5, cinq = 5, temperature > 30\n" ;
        centerText +=         "       * Symbolique(Ou NanFact) : profession = informaticien\n"; 
        centerText +=     "   > D'une Règle :\n ";
        centerText +=         "       * Si \"Prémice\" alors \"Conclusion\"\n ";
        centerText +=         "       * Si (Ensemble Règles) alors \"Conclusion\"\n"; 

        centerText +="... A noter ... :\n  Il faut absolument respecter la grammaire telle que présentée.";
        this.setLeft(new Label(centerText));
        Label center = (Label) this.getLeft();
        center.setTextFill(Color.web("#fff"));
    }

    /**
     * Définit le style de la fenêtre
     */

    public void stylingPane(){
        this.setStyle("-fx-background-color: #093768;");
    }
    /**
     * Permet de définir le contenu du composant de droite de l'interface
     */

    public void setInteractionRight(){
        VBox inferenceZone = new VBox(15.0);
        Button inferenceBtn = new Button("Inférer de nouveaux faits");
    
        inferenceBtn.setOnAction(e -> this.inference());
        this.setButtonStyle(inferenceBtn);
        this.setRight(inferenceZone);
        inferenceZone.getChildren().addAll(inferenceBtn, this.inferenceTextZone);
    }
    /**
     * Permet de styliser les boutons
     */

    public void setButtonStyle(Button btn){
        
        btn.setStyle("-fx-background-color: #f9b17a;-fx-text-fill:#4245769; -fx-font-size: 13px; -fx-font-family: ubuntu; -fx-box-shadow: 60px -16px teal;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #ff9646;-fx-text-fill:#4245769; -fx-font-size: 13px; -fx-font-family: ubuntu; -fx-box-shadow: 60px -16px teal;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #f9b17a;-fx-text-fill:#4245769; -fx-font-size: 13px; -fx-font-family: ubuntu; -fx-box-shadow: 60px -16px teal;"));

    }
    /**
     * @param type
     * Permet de styliser la réponse devant s'afficher à l'écran
     */

    public void stylingAnswer(String type){
        if (type.equals("right")){
            this.answer.setStyle("-fx-background-color: #1c9d0c; -fx-text-fill:#fff; fx-opacity:0.4;");
        } else if(type.equals("wrong")){
            this.answer.setStyle("-fx-background-color: # #900c3f ; -fx-text-fill:#fff; fx-opacity:0.4;");
        }
    }
    /**
     * Permet de définir le contenu de l'interface d'intéraction avec la base
     */

    public void setInteractionContainer(){
        Button homeBtn = new Button("Accueil");

        homeBtn.setOnAction(e -> this.goBackHome());

        this.interactionContainer.getChildren().add(this.textField);
        this.textField.resize(700, 400);

        this.initializeActionButtons();
        this.interactionContainer.getChildren().add(homeBtn);
        this.setCenterText();
        this.setInteractionRight();
        this.setBottom(new VBox(15));
        VBox interactionBottom = (VBox) this.getBottom();
        interactionBottom.getChildren().addAll(this.interactionContainer, this.answer);
    }
    /**
     * Permet d'ajouter des faits
     */
    

    public void addFact(){
        System.out.println("C'est entré pourtant");
        if (!(this.textField.getText().equals(""))){ 
            if (this.system.getKnowledgeBase().addFactFromUser(this.textField.getText()).equals("Fait déjà existant")){
                    this.stylingAnswer("wrong");
                    this.answer.setText("Fait déjà existant");
                } else if (this.system.getKnowledgeBase().addFactFromUser(this.textField.getText()).equals("Fait ajouté avec succès")){
                    this.stylingAnswer("right");
                    this.answer.setText("Le fait a été ajouté avec succès");
                    System.out.println("Fait ajouté avec succès");
                        
                } else if (this.system.getKnowledgeBase().addFactFromUser(this.textField.getText()).equals("Désolé, veuillez revoir la structure de votre fait")){
                    this.stylingAnswer("wrong");
                    this.answer.setText("Il est nécessaire de rentrer quelque chose de valide");                    
                } 
        } else {
            this.answer.setText("Il faut entrer quelque chose !");
        }

    }
    /**
     * Permet de supprimer des faits 
    */
    public void dropFact(){
        if (!(this.textField.getText().equals(""))){
            if (this.system.getKnowledgeBase().dropFact(this.textField.getText()).equals("Le fait n'est pas contenu dans la base")){
                this.stylingAnswer("wrong");
                this.answer.setText("Le fait n'est pas contenu dans la base");
            } else {
                this.stylingAnswer("right");
                this.answer.setText("Fait supprimé avec succès");
                }
        } else {
            this.stylingAnswer("wrong");
            this.answer.setText("Il faut entrer quelque chose !");
        }

    }

    /**
     * Permet d'ajouter des règles 
    */

    public void addRule(){
        
        if (!(this.textField.getText().equals(""))){
            if (this.system.getKnowledgeBase().addRuleFromUser(this.textField.getText()).equals("Règle déjà existante")){
                this.stylingAnswer("wrong");
                System.out.println("Règle déjà existante");
            } else {
                this.system.getKnowledgeBase().addRuleFromUser(this.textField.getText());
                this.stylingAnswer("right");
                this.answer.setText("Règle ajoutée avec succès");

                }
        } else {
            this.stylingAnswer("wrong");
            this.answer.setText("Il faut entrer quelque chose !");
        }

    }

    /**
     * Permet de supprimer des faits 
    */

    public void dropRule(){
        
        if (!(this.textField.getText().equals(""))){
            if (this.system.getKnowledgeBase().dropRule(this.textField.getText()).equals("Règle supprimée avec succès")){
                this.system.getKnowledgeBase().dropRule(this.textField.getText());
                this.stylingAnswer("right");
                this.answer.setText("Règle supprimée avec succès");
            } else {
                this.stylingAnswer("wrong");
                this.answer.setText("La règle n'est pas contenue dans la base");
            }
        } else {
            this.stylingAnswer("wrong");
            this.answer.setText("Il faut entrer quelque chose !");
        }

    }

    /**
     * Définit le Pane de Interaction
     */

    public void setPane(){
        
        this.stylingPane();
        this.setInteractionContainer();

    }
    /**
     * Permet d'inférer de nouveaux faits à partir de la base
     */

    public void inference(){
        KnowledgeBase base = this.system.getKnowledgeBase();
        Engine engine = this.system.getEngine();
        engine.forwardChaining();
        Set<Fact> addedFacts = engine.getAddedFacts();
        System.out.println(addedFacts);

        if (addedFacts.size() == 0){
            this.inferenceTextZone.setText("Désolé, aucun n'a pu être inféré");
            this.inferenceTextZone.setTextFill(Color.web("#a12e0b")); 
            
        } else {
            String str = "Nouveaux faits inférés : \n";
            
            for (Fact fact : addedFacts){
                str =  str + "  > " +  fact.toString() + '\n';     
            }
           
            this.inferenceTextZone.setTextFill(Color.web("#fff"));
            this.inferenceTextZone.setText(str);
        }
        
    }
    /**
     * Permet de vérifier si un fait se trouve dans la base
     */

    public boolean isIntheBase(){
        ObjectsCreator creator = new ObjectsCreator();
        Parser parser = new Parser();
        if (!(this.textField.getText().trim().length() == 0)){ 
            Fact f = creator.createFactFrom(parser.parseFact(this.textField.getText()));
            if (this.system.getEngine().backwardChaining(f)){
                this.stylingAnswer("right");
                this.answer.setText("Le fait est bien contenu dans la base");
                return true;
            } 
    
        }

        this.answer.setText("Le fait n'est pas contenu dans la base");
        return false;

    }



}