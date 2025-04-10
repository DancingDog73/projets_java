package view;

import javax.swing.*;

import listeners.ListenerModel;
import model.game.Grid;
import model.game.Game;
import model.game.State;
import model.player.*;

import java.awt.*;

public class HexGameView extends JPanel implements ListenerModel {

    private static int boardSize = 14;
    private final int hexSize = 30; // Size of the hexagons
    private Game game;

    

    public HexGameView(Game game) {
        this.game = game;

        
         
        // Set up panel size
        int panelWidth = (int) ((boardSize + 0.5) * hexSize * 1.5);
        int panelHeight = (int) (boardSize * hexSize * Math.sqrt(3));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }


    private Point getHexCenter(int row, int col) {
        int x = (int) ((col * hexSize * Math.sqrt(3) + row * hexSize * Math.sqrt(3) / 2) + hexSize);
        int y = (int) ((row * hexSize * 1.5) + hexSize);
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the hexagonal grid
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Point hexCenter = getHexCenter(row, col);
                if (this.game.getCurrentState().getGrid().getGrid()[row][col] == 0) {
                    drawHex(g2d, hexCenter.x, hexCenter.y, Color.LIGHT_GRAY);
                } else if (this.game.getCurrentState().getGrid().getGrid()[row][col] == 1) {
                    drawHex(g2d, hexCenter.x, hexCenter.y, Color.RED);
                } else if (this.game.getCurrentState().getGrid().getGrid()[row][col] == 2) {
                    drawHex(g2d, hexCenter.x, hexCenter.y, Color.BLUE);
                }
                
            }
        }
    }

    private void drawHex(Graphics2D g2d, int x, int y, Color color) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(30 + i * 60);
            int xPoint = (int) (x + hexSize * Math.cos(angle));
            int yPoint = (int) (y + hexSize * Math.sin(angle));
            hex.addPoint(xPoint, yPoint);
        }


        g2d.setColor(color);
        g2d.fillPolygon(hex);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(hex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hex Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            Player playerOne = new PlayerMCTS(10, Math.sqrt(2)); // Joueur 1 aléatoire
            Player playerTwo = new PlayerMCTS(10, Math.sqrt(2));

            // Création de la grille et de l'état initial
            Grid grid = new Grid();
            State initialState = new State(grid);

            // Création du jeu
            Game game = new Game(playerOne, playerTwo, initialState);
            HexGameView hexGameView = new HexGameView(game);
            HexGameMenu hexGameMenu = new HexGameMenu(game);
            frame.setLayout(new BorderLayout());
            frame.add(hexGameMenu, BorderLayout.NORTH);
            frame.add(hexGameView, BorderLayout.CENTER);

            frame.setSize(1100, 740);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            
            Timer timer = new Timer(50, e -> {
                if (game.getCurrentState().isFinished() == 0) {
                    game.next();
                    hexGameView.repaint();
                } else {
                    ((Timer) e.getSource()).stop(); // Stop when the game is finished
                }
               
            });
            timer.start();
                
        });
    }


    @Override
    public void modeleMisAJour(Object source) {
        this.repaint();
    }
}


class HexGameMenu extends JPanel {
    private Game game;
    private Timer timer;

    JButton reset;
    JButton start;
    JButton stop;
    JButton previous;

    JComboBox<String> player1Picker;
    JComboBox<String> player2Picker;

    public HexGameMenu(Game game) {
        this.game = game;
        // Menu buttons
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        reset = new JButton("Reset");
        start = new JButton("Start");
        stop = new JButton("Stop");
        previous = new JButton("Previous");

        String[] algorithms = { "Random", "MCTS", "Rave"};

        player1Picker = new JComboBox<>(algorithms);
        player2Picker = new JComboBox<>(algorithms);

        buttons.add(start); buttons.add(reset); buttons.add(stop); buttons.add(previous);
        buttons.add(player1Picker); buttons.add(player2Picker);
        this.buttonsEvent();
        this.add(buttons, BorderLayout.NORTH);
    }

    private void buttonsEvent() {
        // Play simulation
        this.start.addActionListener(event -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }

            System.out.println("Start");
            timer = new Timer(50, e -> {
                if (game.getCurrentState().isFinished() == 0) {
                    game.next();
                } else {
                    timer.stop();
                    Player winner = game.getWinner();
                    System.out.println(winner);
                    String message = (winner != null) 
                        ? "The winner is Player " + winner.getId() + "!" 
                        : "It's a draw!";

                    // Show winner pop-up
                    JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                }
    
            });
            timer.start();
        });

        this.stop.addActionListener(event -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
                System.out.println("Game stopped!");
            }
        });


        this.reset.addActionListener(event -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }

            game.reset();
        });

        this.previous.addActionListener(event -> game.previous());

        this.player1Picker.addActionListener(event -> {
            String algo = (String) this.player1Picker.getSelectedItem();
            if (algo == "Random") {
                this.game.setPlayerOne(new PlayerRandom());
            } else if (algo == "MCTS") {
                this.game.setPlayerOne(new PlayerMCTS(10, Math.sqrt(2)));
            } else if (algo == "Rave") {
                this.game.setPlayerOne(new PlayerMCTS(10, Math.sqrt(2), 1, true));
            }
        });

        this.player2Picker.addActionListener(event -> {
            String algo = (String) this.player2Picker.getSelectedItem();
            if (algo == "Random") {
                this.game.setPlayerTwo(new PlayerRandom());
            } else if (algo == "MCTS") {
                this.game.setPlayerTwo(new PlayerMCTS(10, Math.sqrt(2)));
            } else if (algo == "Rave") {
                this.game.setPlayerTwo(new PlayerMCTS(10, Math.sqrt(2), 2, true));
            }
        });
    }
}
