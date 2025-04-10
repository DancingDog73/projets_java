package taquin.viewler;

import javax.swing.*;
import java.awt.*;

import taquin.model.NavigationModel;


public class HomePanel extends JPanel {

    private JButton playButton;
    private JButton exitButton;
    private NavigationModel navigator;

    public HomePanel (NavigationModel model) {

        super();
        this.playButton = new JButton("play");
        this.navigator = model;
        this.init();

    }


    public void init () {

        this.setLayout(new GridBagLayout());
        //this.setPreferredSize(new Dimension(800,200));
        this.add(this.playButton);

        this.playButton.addActionListener((e) -> this.navigator.goTo("SizeChoice"));

    }


}