package util.mvc;


/**
 * Interface représentant un écouteur de modèle.
 * Les objets qui implémentent cette interface sont notifiés lorsque l'état du modèle
 * change. Ils doivent implémenter la méthode {@link #modeleMisAJour(Object)} pour
 * traiter ces notifications.
 */
public interface EcouteurModele {

    /**
     * Méthode appelée lorsque le modèle a subi un changement.
     * Cette méthode permet à l'écouteur de réagir aux modifications du modèle.
     *
     * @param source L'objet source qui a généré le changement (le modèle lui-même).
     */
    public void modeleMisAJour(Object source);


}