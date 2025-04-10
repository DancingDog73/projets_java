package taquin.viewler;

import javax.swing.*;
import java.awt.*;

import taquin.model.NavigationModel;


public class GoBackPanel extends JPanel {

    private JButton goBackButton;

    public GoBackPanel (NavigationModel navigator) {

        super();

        this.setLayout(new GridBagLayout());
        this.goBackButton = new JButton("<-");
        this.add(this.goBackButton);

        this.goBackButton.addActionListener((e) -> {
            String currentPage = navigator.getCurrentPage();
            if (currentPage == "NumbersSurface" || currentPage == "ImageSurface") {
                int willGoBack = JOptionPane.showConfirmDialog(this, "Give up the part ?");
                if (willGoBack == 0) {
                    navigator.goBack();
                }
            } else {
                navigator.goBack();
            }
        });

    }


}