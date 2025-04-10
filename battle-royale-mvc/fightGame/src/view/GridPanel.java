package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import modele.GridInterface;
import modele.GridProxy;
import modele.gameElements.weapons.Bomb;
import playerModele.PlayerHuman;
import util.*;


public class GridPanel extends JPanel {
    private GridInterface grid;
    private int size;
    private ToolBarPanel toolBarPanel;

    public GridPanel(GridInterface grid, int size, ToolBarPanel toolBarPanel) {
        this.grid = grid;
        this.size = size;
        this.toolBarPanel = toolBarPanel;
        this.setSize(new Dimension(this.size, this.size));
        this.setBackground(Color.WHITE);
        this.repaint();
        this.clickCellEvent();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int gridSize = this.grid.getSize();
        int cellSize = (int) this.size / gridSize;

        for (int y = 0 ; y < gridSize ; y++) {
            for (int x = 0 ; x < gridSize ; x++) {
                GameElement element = this.grid.getElement(x, y);

                // Draw grid lines
                g.setColor(Color.GRAY);
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                // Draw elements image
                if (element.getImage() != null) {
                    g.drawImage(element.getImage(), x*cellSize+8, y*cellSize+8, cellSize-16, cellSize-16,null);
                }

                if (this.grid instanceof GridProxy) {
                    int[] pos = ((GridProxy)this.grid).getProxyPlayer().getPos();
                    if (pos[0] == x && pos[1] == y) {
                        g.setColor(Color.ORANGE);
                        g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    }
                }
                
            }
        }
    }

    public void clickCellEvent() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (grid instanceof GridProxy) {
                    // If it's not your turn or the player isn't human then we pass
                    if (!(grid.getActifPlayer() == ((GridProxy) grid).getProxyPlayer()) && !(((GridProxy) grid).getProxyPlayer() instanceof PlayerHuman)) {
                        return;
                    }
 
                    int cellSize = (int) size / grid.getSize();
                    PlayerHuman player = (PlayerHuman) ((GridProxy) grid).getProxyPlayer();

                    // Divide by cellSize to get the correct cell coordinates | for e.getX() = 512 and cellSize = 50 -> x = 10
                    int clickedCellX = e.getX() / cellSize;
                    int clickedCellY = e.getY() / cellSize;

                    int playerX = player.getX();
                    int playerY = player.getY();
                    
                    // Fetch the mode set in toolBarPanel, if no buttons were clicked then the default is move
                    // If you click on the bomb button then a cell that is either left up down or right then it will place a bomb in said cell
                    // Mode resets after each go
                    ToolBarPanel.ActionMode mode = toolBarPanel.getCurrentActionMode();
                    System.out.println(mode);
                    if (mode == ToolBarPanel.ActionMode.MOVE) {
                        if (clickedCellX == playerX && clickedCellY == playerY - 1) {
                            player.move("up");
                        } else if (clickedCellX == playerX && clickedCellY == playerY + 1) {
                            player.move("down");
                        } else if (clickedCellX == playerX - 1 && clickedCellY == playerY) {
                            player.move("left");
                        } else if (clickedCellX == playerX + 1 && clickedCellY == playerY) {
                            player.move("right");
                        } else {
                            System.out.println("Invalid move: Clicked cell is not adjacent");
                        }
                    }

                    // Place explosive
                    else if (mode == ToolBarPanel.ActionMode.PLACE_BOMB || mode == ToolBarPanel.ActionMode.PLACE_MINE) {
                        String explosive = mode == ToolBarPanel.ActionMode.PLACE_BOMB ? "Bomb" : "Mine";
                        System.out.println(playerX + " " + playerY + " " + clickedCellX + " " + clickedCellY);
                        if (clickedCellX == playerX - 1 && clickedCellY == playerY - 1) {
                            player.placeExplosive(explosive, 0);
                        } else if (clickedCellX == playerX && clickedCellY == playerY - 1) {
                            player.placeExplosive(explosive, 1);
                        } else if (clickedCellX == playerX + 1 && clickedCellY == playerY - 1) {
                            player.placeExplosive(explosive, 2);
                        } else if (clickedCellX == playerX - 1 && clickedCellY == playerY) {
                            player.placeExplosive(explosive, 3);
                        } else if (clickedCellX == playerX + 1 && clickedCellY == playerY) {
                            player.placeExplosive(explosive, 4);
                        } else if (clickedCellX == playerX - 1 && clickedCellY == playerY + 1) {
                            player.placeExplosive(explosive, 5);
                        } else if (clickedCellX == playerX && clickedCellY == playerY + 1) {
                            player.placeExplosive(explosive, 6);
                        } else if (clickedCellX == playerX + 1 && clickedCellY == playerY + 1) {
                            player.placeExplosive(explosive, 7);
                        } else {
                            System.out.println("Invalid move: Clicked cell is not adjacent");
                        }
                    }

                    // Shoot - Here we just check whether the cell clicked is either on the horizontal line or vertical line
                    else if (mode == ToolBarPanel.ActionMode.SHOOT) {
                        if (clickedCellX == playerX) {
                            player.shoot("vertical");
                        } else if (clickedCellY == playerY) {
                            player.shoot("horizontal");
                        } else {
                            System.out.println("Invalid move: You must shoot either horizontally or vertically");
                        }
                    }

                    player.provideInput();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
    }
}