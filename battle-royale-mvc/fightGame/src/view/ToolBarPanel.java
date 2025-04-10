package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.GridInterface;
import modele.gameElements.weapons.Bomb;
import modele.gameElements.weapons.Mine;
import playerModele.Player;

public class ToolBarPanel extends JPanel {
    public enum ActionMode {
        MOVE,
        SHOOT,
        PLACE_BOMB,
        PLACE_MINE
    }

    private Player player;
    private GridInterface grid;

    private JLabel turnLabel;
    private JLabel energyLabel;
    private JLabel moveLabel;
    private JButton shootButton;
    private JButton bombButton;
    private JButton mineButton;

    private ActionMode currentActionMode = ActionMode.MOVE;

    public ToolBarPanel(Player player, GridInterface grid) {
        this.player = player;
        this.grid = grid;

        this.setBackground(Color.WHITE);

        // Left Panel

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Vertical layout
        leftPanel.setBackground(Color.WHITE);

        this.turnLabel = new JLabel("");
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.energyLabel = new JLabel("");
        energyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.moveLabel = new JLabel("move");
        moveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(turnLabel);
        leftPanel.add(Box.createVerticalStrut(10)); // Add vertical spacing
        leftPanel.add(energyLabel);
        leftPanel.add(Box.createVerticalStrut(10)); // Add vertical spacing
        leftPanel.add(moveLabel);

        // Middle panel

        this.shootButton = new JButton("Shoot");

        // Right Panel

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Vertical layout
        rightPanel.setBackground(Color.WHITE);

        this.bombButton = new JButton(new ImageIcon(Bomb.loadImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        bombButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.mineButton = new JButton(new ImageIcon(Mine.loadImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        mineButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(mineButton);
        rightPanel.add(Box.createVerticalStrut(10)); // Add vertical spacing
        rightPanel.add(bombButton);

        this.add(leftPanel);
        this.add(this.shootButton);
        this.add(rightPanel);

        this.buttonEvents();
        this.repaint();
    }

    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String text;

        if (this.grid.getActifPlayer() == this.player) {
            text = "Your Turn !";
        } else {
            text = String.format("%s's' turn", this.grid.getActifPlayer().getName());
        }
        this.turnLabel.setText(text);
        this.energyLabel.setText("Energy: " + Integer.toString(this.player.getEnergy()));

        switch (this.currentActionMode) {
            case MOVE:
                this.moveLabel.setText("move");
                break;
            case SHOOT:
                this.moveLabel.setText("shoot");
                break;
            case PLACE_BOMB:
                this.moveLabel.setText("place bomb");
                break;
            case PLACE_MINE:
                this.moveLabel.setText("place mine");
                break;
        }

    }

    public void buttonEvents() {
        this.shootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentActionMode = currentActionMode != ActionMode.SHOOT ? ActionMode.SHOOT : ActionMode.MOVE;
                System.out.println("Shoot button clicked");
                repaint();
            }
        });

        this.bombButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentActionMode = currentActionMode != ActionMode.PLACE_BOMB ? ActionMode.PLACE_BOMB : ActionMode.MOVE;
                System.out.println("Bomb button clicked");
                repaint();
            }
        });

        this.mineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentActionMode = currentActionMode != ActionMode.PLACE_MINE ? ActionMode.PLACE_MINE : ActionMode.MOVE;
                System.out.println("Mine button clicked");
                repaint();
            }
        });
    }

    public ActionMode getCurrentActionMode() {
        ActionMode curr = this.currentActionMode;
        this.currentActionMode = ActionMode.MOVE;
        this.repaint();
        return curr;
    }


}
