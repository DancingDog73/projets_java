package util.mvc;


/**
 * Interface représentant un modèle écoutable.
 * Les objets qui implémentent cette interface permettent à d'autres objets (les écouteurs)
 * de s'enregistrer afin de recevoir des notifications lorsque l'état du modèle change.
 */
public interface ModeleEcoutable {

    /**
     * Ajoute un écouteur au modèle.
     * Cet écouteur sera notifié lorsque l'état du modèle changera.
     *
     * @param e L'écouteur à ajouter. Il doit implémenter l'interface {@link EcouteurModele}.
     */
    public void ajoutEcouteur(EcouteurModele e);

    /**
     * Retire un écouteur du modèle.
     * L'écouteur ne sera plus notifié des changements d'état du modèle après avoir été retiré.
     *
     * @param e L'écouteur à retirer. Il doit être un écouteur précédemment ajouté.
     */
    public void retraitEcouteur(EcouteurModele e);


}