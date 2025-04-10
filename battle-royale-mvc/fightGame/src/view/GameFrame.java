package view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import modele.GridInterface;
import modele.GridProxy;
import playerModele.Player;
import util.mvc.EcouteurModele;

public class GameFrame extends JFrame implements EcouteurModele {
    private static final Dimension WINDOW_SIZE = new Dimension(400, 500);
    private GridInterface grid;
    private final boolean main;

    private GridPanel gridPanel;
    private DefaultTableModel tableModel;
    private ToolBarPanel toolbarPanel;

    public GameFrame(GridInterface grid, boolean main) {
        this.grid = grid;
        this.main = main;
        this.init();
        this.initGrid();

        this.addWindowListener((WindowListener) new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                revalidate();
                repaint();
            }
        });
    }

    /**
     * Initialise des paramètres et des statistiques
     */
    public void init() {
        // Frame settings
        this.setTitle("Jeu Combat");
        this.pack();
        Insets insets = this.getInsets();
        this.setSize(new Dimension((int) GameFrame.WINDOW_SIZE.getWidth() + insets.left + insets.right, (int) GameFrame.WINDOW_SIZE.getHeight() + insets.top + insets.bottom));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        if (this.main) {
            String[] columnNames = {"Player", "Position", "Energy", "Ammunition", "Bombs", "Mines"};
            this.tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }
            };
            this.updatePlayerTable();
            JScrollPane scrollPane= new JScrollPane(new JTable(this.tableModel));
            scrollPane.setBounds(0, 0, (int) this.getWidth(), 100);
            this.add(scrollPane);
        } else {
            this.toolbarPanel = new ToolBarPanel(((GridProxy) this.grid).getProxyPlayer(), this.grid);
            this.toolbarPanel.setBounds(0, 0, this.getWidth(), 100);
            this.add(this.toolbarPanel);
        }
        
        this.setVisible(true);
    }

    /**
     * Initialise la grille
     */
    public void initGrid() {
        int size = (int) this.getWidth();
        this.gridPanel = new GridPanel(this.grid, size, this.toolbarPanel == null ? null : this.toolbarPanel);
        this.gridPanel.setBounds(0, 100, size, size);

        this.add(this.gridPanel);
        this.repaint();


    }

    @Override
    public void modeleMisAJour(Object source) {
        this.gridPanel.repaint();
        if (this.main) {
            this.updatePlayerTable();
        } else {
            this.toolbarPanel.repaint();
        }
    }

    /** Recrée le tableau des statistiques
     * 
     */
    public void updatePlayerTable() {
        this.tableModel.setRowCount(0);

        for (Player player : this.grid.getPlayerList()) {
            Map<String, Integer> explosives = player.getExplosives();
            this.tableModel.addRow(new String[]{ 
                player.getName(),
                "x : " + player.getX() + " y : " + player.getY(),
                Integer.toString(player.getEnergy()),
                Integer.toString(player.getAmmo()),
                Integer.toString(explosives.get("Bomb")),
                Integer.toString(explosives.get("Mine"))
            }); 
        }
        this.tableModel.fireTableDataChanged();
    }
}
