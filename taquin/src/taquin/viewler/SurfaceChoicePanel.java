package taquin.viewler;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import taquin.model.NavigationModel;


public class SurfaceChoicePanel extends JPanel {

    private JButton playWithNumbersButton;
    private JButton playWithImagesButton;
    private NavigationModel navigator;

    public SurfaceChoicePanel (NavigationModel model) {
        super();
        this.playWithNumbersButton = new JButton("numbers");
        this.playWithImagesButton = new JButton("images");
        this.navigator = model;
        this.init();
    }


    public void init () {

        this.setLayout(new GridLayout(3,1));
        this.setBackground(Color.GREEN);
        //this.setPreferredSize(new Dimension(800,200));

        JPanel header = new JPanel();
        header.setLayout(new GridBagLayout());
        header.add(new JLabel("Do you want to play with ?"));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2,1));
        JPanel firstButtonPanel = new JPanel(new GridBagLayout());
        firstButtonPanel.add(this.playWithNumbersButton);
        JPanel secondButtonPanel = new JPanel(new GridBagLayout());
        secondButtonPanel.add(this.playWithImagesButton);
        buttonsPanel.add(firstButtonPanel);
        buttonsPanel.add(secondButtonPanel);

        this.add(header);
        this.add(buttonsPanel);
        this.add(new GoBackPanel(this.navigator));
        
        this.playWithImagesButton.addActionListener((e) -> this.navigator.goTo("ImageChoice"));
        this.playWithNumbersButton.addActionListener((e) -> this.playWithNumbers());

    }


    public void playWithNumbers () {
        this.navigator.goTo("NumbersSurface");
    }


}