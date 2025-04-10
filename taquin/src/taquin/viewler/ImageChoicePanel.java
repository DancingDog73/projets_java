package taquin.viewler;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.io.*;

import taquin.model.NavigationModel;
import util.processors.ImageGenerator;


public class ImageChoicePanel extends JPanel {

    private JButton generatingImageButton;
    private JButton choosingImageButton;
    private NavigationModel navigator;

    private File chosenImage;

    public ImageChoicePanel (NavigationModel model) {
        this.generatingImageButton = new JButton("generate an image");
        this.choosingImageButton = new JButton("choose your own image");
        this.navigator = model;
        this.init();
    }

    public void init () {

        //this.setPreferredSize(new Dimension(800,200));
        this.setLayout(new GridLayout(3,1));

        JPanel header = new JPanel(new GridBagLayout());
        header.add(new JLabel("Would you rather"));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2,1));
        JPanel firstButtonPanel = new JPanel(new GridBagLayout());
        firstButtonPanel.add(this.generatingImageButton);
        JPanel secondButtonPanel = new JPanel(new GridBagLayout());
        secondButtonPanel.add(this.choosingImageButton);
        buttonsPanel.add(firstButtonPanel);
        buttonsPanel.add(secondButtonPanel);

        this.add(header);
        this.add(buttonsPanel);
        this.add(new GoBackPanel(this.navigator));

        this.generatingImageButton.addActionListener((e) -> this.generateImageThenPlay());
        this.choosingImageButton.addActionListener((e) -> this.chooseImageThenPlay());

    }


    public void generateImageThenPlay () {
        ImageGenerator generator = new ImageGenerator();
        this.chosenImage = generator.generateImage();
        this.playWithImage();
    }
    

    public void chooseImageThenPlay () {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Only images", "jpg", "jpeg"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.chosenImage = chooser.getSelectedFile();
            this.playWithImage();
        }
    }


    public void playWithImage () {
        this.navigator.goTo("ImageSurface");
    }


    public File getChosenImage () {
        return this.chosenImage;
    }

}