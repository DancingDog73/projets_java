package modele.gameElements;
import util.GameElement;

public class Empty extends GameElement {
    public Empty(int x, int y) {
        super(x, y, null);
    }

    public String toString() {
        return "Empty";
    } 
}
