package taquin.viewler;

import javax.swing.*;
import java.awt.*;


public class GameWindow extends JFrame {

    public GameWindow () {    
        super("TAQUIN");
        this.init();
    }


    public void init () {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setContentPane(new MainPanel());

        this.setVisible(true);
    }


    
    public static void main (String[] args) {

        GameWindow window = new GameWindow();
        
    }

}