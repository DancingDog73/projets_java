package taquin.viewler;


import javax.swing.*;
import java.awt.*;

import taquin.model.NavigationModel;



public class SizeChoicePanel extends JPanel {

    
    private final static int SIZE_MIN = 3;
    private final static int SIZE_MAX = 7;

    private int chosenSize;
    private JLabel sizeLabel;
    private JButton sizeIncreasingButton;
    private JButton sizeDecreasingButton;
    private JButton nextButton;
    private NavigationModel navigator;

    
    public SizeChoicePanel (NavigationModel model) {

        super();

        this.chosenSize = 4;
        this.sizeLabel = new JLabel("" + this.chosenSize);
        this.sizeIncreasingButton = new JButton("+");
        this.sizeDecreasingButton = new JButton("-");
        this.nextButton = new JButton("->");
        this.navigator = model;

        this.init();

    }

    
    public void init () {

        JPanel header = new JPanel();
        header.setLayout(new GridBagLayout());
        header.add(new JLabel("Choose the size of your puzzle surface"));

        JPanel main = new JPanel(new GridLayout(2,1));
        JPanel controlPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        controlPanel.add(this.sizeDecreasingButton);
        controlPanel.add(this.sizeLabel);
        controlPanel.add(this.sizeIncreasingButton);
        buttonPanel.add(this.nextButton);
        main.add(controlPanel);
        main.add(buttonPanel);

        //this.setPreferredSize(new Dimension(800,200));
        this.setLayout(new GridLayout(3,1));
        this.add(header);
        this.add(main);
        this.add(new GoBackPanel(this.navigator));

        this.sizeIncreasingButton.addActionListener((e) -> this.increaseSize());
        this.sizeDecreasingButton.addActionListener((e) -> this.decreaseSize());
        this.nextButton.addActionListener((e) -> this.navigator.goTo("SurfaceChoice"));

    }

    
    public int getChosenSize () {
        return this.chosenSize;
    }

    public void increaseSize () {
        if (this.chosenSize < SIZE_MAX) {
            this.chosenSize += 1;
            this.updateSize();
        }
    }

    public void decreaseSize () {
        if (this.chosenSize > SIZE_MIN) {
            this.chosenSize -= 1;
            this.updateSize();
        }
    }

    public void updateSize () {
        this.navigator.setSurfaceSize(this.chosenSize);
        this.sizeLabel.setText("" + this.chosenSize);
    }


}