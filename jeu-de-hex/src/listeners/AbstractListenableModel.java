package listeners;

import java.util.ArrayList;
import java.util.List;

public class AbstractListenableModel implements ListenableModel {
    List<ListenerModel> listeners;

    public AbstractListenableModel() {
        listeners = new ArrayList<ListenerModel>();
    }

    public void addListener(ListenerModel l) {
        this.listeners.add(l);
    }

    public void removeListener(ListenerModel l) {
        this.listeners.remove(l);
    }
    
    public void firechangement() {
        for (ListenerModel listener : this.listeners) {
            listener.modeleMisAJour(this);
        }
    }
}
