package taquin.model;

import java.util.Stack;
import util.mvc.AbstractListenable;


public class NavigationModel extends AbstractListenable {

    private Stack<String> navigationStack;
    private int surfaceSize;

    public NavigationModel () {
        this.navigationStack = new Stack<>();
        this.surfaceSize = 4;
    }

    public String getCurrentPage () {
        return this.navigationStack.peek();
    }

    public int getSurfaceSize () {
        return this.surfaceSize;
    }

    public void setSurfaceSize (int newSize) {
        this.surfaceSize = newSize;
    }

    public void goTo (String page) {
        this.navigationStack.push(page);
        this.fireChanges();
    }

    public void goBack () {
        this.navigationStack.pop();
        this.fireChanges();
    }

}