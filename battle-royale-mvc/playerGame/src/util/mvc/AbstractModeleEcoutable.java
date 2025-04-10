package util.mvc;

import java.util.*;


/**
 * Classe abstraite représentant un modèle écoutable, qui gère une liste d'écouteurs
 * et notifie ces écouteurs en cas de changement dans l'état du modèle.
 * Cette classe implémente l'interface {@link ModeleEcoutable} et fournit des méthodes
 * pour ajouter, retirer des écouteurs et déclencher des notifications.
 */
public abstract class AbstractModeleEcoutable implements ModeleEcoutable{

    private List<EcouteurModele> ecouteurs;

    /**
     * Constructeur de la classe {@code AbstractModeleEcoutable}.
     * Initialise la liste des écouteurs.
     */
    public AbstractModeleEcoutable(){
        this.ecouteurs = new ArrayList<>();
    }

    /**
     * Ajoute un écouteur au modèle. 
     * L'écouteur sera notifié en cas de changement dans le modèle.
     *
     * @param e L'écouteur à ajouter.
     */
    @Override 
    public void ajoutEcouteur(EcouteurModele e){
        this.ecouteurs.add(e);
    }

    /**
     * Retire un écouteur du modèle.
     * Cet écouteur ne sera plus notifié des changements dans le modèle.
     *
     * @param e L'écouteur à retirer.
     */
    @Override
    public void retraitEcouteur(EcouteurModele e){
        this.ecouteurs.remove(e);
    }

    /**
     * Notifie tous les écouteurs enregistrés qu'un changement a eu lieu dans le modèle.
     * Cette méthode appelle la méthode {@code modeleMisAJour} sur chaque écouteur.
     */
    protected void fireChangement(){
        for(EcouteurModele e: this.ecouteurs){
            e.modeleMisAJour(this);
        }
    }



}