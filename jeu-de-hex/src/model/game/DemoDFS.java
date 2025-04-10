package model.game;

//classe de démo du Depth First Search(DFS)
public class DemoDFS {
    public static void main(String[] args) {
        Grid grid = new Grid();
       
        
        // Simule une victoire pour le joueur 1
        grid.toggleCell(0, 0, 1);
        grid.toggleCell(0, 1, 1);
        grid.toggleCell(0, 2, 1);
        grid.toggleCell(0, 3, 1);
        grid.toggleCell(0, 4, 1);
        grid.toggleCell(0, 5, 1);
        grid.toggleCell(0, 6, 1);
        grid.toggleCell(0, 7, 1);
        grid.toggleCell(1, 7, 1);
        grid.toggleCell(2, 7, 1);
        grid.toggleCell(2, 8, 1);
        grid.toggleCell(1, 9, 1);
        grid.toggleCell(0, 9, 1);
        grid.toggleCell(0, 10, 1);
        grid.toggleCell(0, 11, 1);
        grid.toggleCell(0, 12, 1);
        grid.toggleCell(0, 13, 1); 
        
        System.out.println(grid);
        System.out.println(grid.hasPlayerWon(1)); // Devrait retourner true
        
    }
}
