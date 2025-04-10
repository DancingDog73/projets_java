package util.mvc;

import java.util.Set;
import java.util.HashSet;


public abstract class AbstractListenable implements Listenable {

    private Set<Listener> listeners;

    public AbstractListenable () {
        this.listeners = new HashSet<>();
    }

    public AbstractListenable (Set<Listener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void addListener (Listener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener (Listener listener) {
        this.listeners.remove(listener);
    }

    public void fireChanges () {
        for (Listener listener : this.listeners) {
            listener.updatedModel(this);
        }
    }

}