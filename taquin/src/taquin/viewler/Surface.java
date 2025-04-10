package taquin.viewler;


import javax.swing.*;
import java.awt.*;
import java.util.List;

import taquin.model.*;
import util.mvc.Listener;


public class Surface<E> extends JPanel implements Listener {

    private JPanel header;
    private JPanel gameSurface;
    private JPanel footer;
    private GameModel<E> gamer;
    private NavigationModel navigator;


    public Surface (GameModel<E> gamer, NavigationModel model) {
        super();
        this.header = new JPanel();
        this.gameSurface = new JPanel(new GridLayout(model.getSurfaceSize(), model.getSurfaceSize()));
        this.footer = new JPanel();
        this.gamer = gamer;
        this.navigator = model;
        this.init();
    }


    public void init () {

        //this.setPreferredSize(new Dimension(800,200));
        this.setLayout(new GridBagLayout());
        this.gamer.addListener(this);

        this.header.add(new JLabel("Some infos"));
        this.addPieces(this.gamer.getPieces());
        this.footer.add(new GoBackPanel(this.navigator));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weighty = 0.25;
        this.add(this.header, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        this.add(this.gameSurface, gbc);
        gbc.gridy = 2;
        gbc.weighty = 0.25;
        this.add(this.footer, gbc);

    }


    public void addPieces (List<E> pieces) {
        for (E piece : pieces) {
            if (piece instanceof ImageIcon) {
                JButton button;
                ImageIcon image = gamer.isPieceBlank(piece) ? new ImageIcon() : (ImageIcon)piece;
                button = new JButton(image);
                button.setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button.addActionListener((e) -> this.gamer.move((E)button.getIcon()));
                this.gameSurface.add(button);
            } else {
                JButton button;
                button = gamer.isPieceBlank(piece) ? new JButton("") : new JButton((String)piece);
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button.addActionListener((e) -> this.gamer.move((E)button.getText()));
                this.gameSurface.add(button);
            }
        }
    }


    @Override
    public void updatedModel (Object source) {
        this.gameSurface.removeAll();
        this.gameSurface.revalidate();
        this.gameSurface.repaint();
        this.addPieces(this.gamer.getPieces());
    }

}