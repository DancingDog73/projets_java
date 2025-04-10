package modele.strategies.playerstrategies;

import java.util.*;
import playerModele.*;
import modele.*;
import modele.gameElements.powerups.PowerUp;

public class AdvancedStrategy extends PlayerStrategy {

    /**
     * Calcule le prochain mouvement du joueur en fonction de la stratégie.
     * La priorité est donnée à l'énergie, à l'attaque, au déplacement vers l'ennemi, puis au déplacement aléatoire.
     * 
     * @param player Le joueur qui effectue le mouvement.
     * @param grid La grille de jeu sur laquelle le joueur se déplace.
     */
    @Override
    public void computeMove(Player player, Grid grid) {
        // Priorité 1 : Si l'énergie est faible, chercher une pastille d'énergie
        if (player.getEnergy() < 50) {
            PowerUp nearestPowerUp = findNearestPowerUp(player, grid);
            if (nearestPowerUp != null) {
                if (moveToWithAStar(player, grid, nearestPowerUp.getX(), nearestPowerUp.getY())) return;
            }
        }

        // Priorité 2 : Si un ennemi est à portée, attaquer
        Player nearestEnemy = findNearestEnemy(player, grid);
        if (nearestEnemy != null) {
            if (isEnemyInLineOfFire(player, nearestEnemy)) {
                if(attackEnemy(player, nearestEnemy)) return;
            }
        }

        // Priorité 3 : Se déplacer vers l'ennemi le plus proche pour l'attaquer
        if (nearestEnemy != null) {
            if (moveToWithAStar(player, grid, nearestEnemy.getX(), nearestEnemy.getY())) return;
        }

        // Priorité 4 : Si rien d'autre, déplacement aléatoire
        moveRandomly(player);
    }

    /**
     * Vérifie si un ennemi est dans une ligne de tir (horizontale ou verticale).
     * 
     * @param player Le joueur qui vérifie la ligne de tir.
     * @param enemy L'ennemi à vérifier.
     * @return true si l'ennemi est dans la ligne de tir, sinon false.
     */
    private boolean isEnemyInLineOfFire(Player player, Player enemy) {
        return (player.getX() == enemy.getX() || player.getY() == enemy.getY()) && 
               (Math.abs(enemy.getX() - player.getX()) <= player.getRange());
    }

    /**
     * Attaque un ennemi si possible en fonction de la portée et de la position du joueur.
     * 
     * @param player Le joueur qui attaque.
     * @param enemy L'ennemi à attaquer.
     * @return true si l'attaque a réussi, sinon false.
     */
    private boolean attackEnemy(Player player, Player enemy) {
        if (player.getX() == enemy.getX() && (Math.abs(enemy.getX() - player.getX()) <= player.getRange())) {
            player.shootVertical();
            return true;
        } else if (player.getY() == enemy.getY() && (Math.abs(enemy.getX() - player.getX()) <= player.getRange())) {
            player.shootHorizontal();
            return true;
        }
        return false;
    }

    /**
     * Effectue un déplacement aléatoire du joueur dans une direction aléatoire.
     * 
     * @param player Le joueur qui effectue le mouvement.
     */
    private void moveRandomly(Player player) {
        int direction = new Random().nextInt(4);
        switch (direction) {
            case 0 -> player.moveRight();
            case 1 -> player.moveLeft();
            case 2 -> player.moveUp();
            case 3 -> player.moveDown();
        }
    }

    /**
     * Déplace le joueur vers une position cible en utilisant l'algorithme A*.
     * 
     * @param player Le joueur à déplacer.
     * @param grid La grille de jeu sur laquelle le joueur se déplace.
     * @param targetX La position X de la cible.
     * @param targetY La position Y de la cible.
     * @return true si le joueur a effectué un déplacement, sinon false.
     */
    private boolean moveToWithAStar(Player player, Grid grid, int targetX, int targetY) {
        List<int[]> path = aStar(player.getX(), player.getY(), targetX, targetY, grid);

        if (path != null && path.size() > 1) {
            int[] nextStep = path.get(1); // Le premier élément est la position actuelle, le suivant est la prochaine étape.
            int dx = nextStep[0] - player.getX();
            int dy = nextStep[1] - player.getY();

            if (dx == 1) player.moveRight();
            else if (dx == -1) player.moveLeft();
            else if (dy == 1) player.moveDown();
            else if (dy == -1) player.moveUp();
            return true;
        }
        return false;
    }

    /**
     * Implémentation de l'algorithme A* pour trouver le chemin optimal entre deux points.
     * 
     * @param startX La position X de départ.
     * @param startY La position Y de départ.
     * @param targetX La position X de la cible.
     * @param targetY La position Y de la cible.
     * @param grid La grille de jeu sur laquelle le joueur se déplace.
     * @return Une liste de coordonnées représentant le chemin optimal.
     */
    private List<int[]> aStar(int startX, int startY, int targetX, int targetY, Grid grid) {
        // Nodes à explorer (priorité par coût total = f(n) = g(n) + h(n))
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.fCost));
        Map<String, Node> allNodes = new HashMap<>();

        Node startNode = new Node(startX, startY, null, 0, heuristic(startX, startY, targetX, targetY));
        openSet.add(startNode);
        allNodes.put(toKey(startX, startY), startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // Si on atteint la cible
            if (current.x == targetX && current.y == targetY) {
                return reconstructPath(current);
            }

            for (int[] neighbor : getNeighbors(current.x, current.y, grid)) {
                int neighborX = neighbor[0];
                int neighborY = neighbor[1];
                String neighborKey = toKey(neighborX, neighborY);

                int tentativeGCost = current.gCost + 1; // Chaque déplacement a un coût de 1
                Node neighborNode = allNodes.getOrDefault(neighborKey, new Node(neighborX, neighborY, null, Integer.MAX_VALUE, 0));

                if (tentativeGCost < neighborNode.gCost) {
                    neighborNode.gCost = tentativeGCost;
                    neighborNode.hCost = heuristic(neighborX, neighborY, targetX, targetY);
                    neighborNode.fCost = neighborNode.gCost + neighborNode.hCost;
                    neighborNode.parent = current;

                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                        allNodes.put(neighborKey, neighborNode);
                    }
                }
            }
        }

        // Aucun chemin trouvé
        return null;
    }

    /**
     * Heuristique pour A* (distance de Manhattan).
     * 
     * @param x1 La position X du point de départ.
     * @param y1 La position Y du point de départ.
     * @param x2 La position X du point cible.
     * @param y2 La position Y du point cible.
     * @return La distance de Manhattan entre les deux points.
     */
    private int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * Reconstruit le chemin à partir du noeud cible.
     * 
     * @param targetNode Le noeud cible à partir duquel le chemin est reconstruit.
     * @return Une liste des coordonnées du chemin reconstruit.
     */
    private List<int[]> reconstructPath(Node targetNode) {
        List<int[]> path = new ArrayList<>();
        Node current = targetNode;

        while (current != null) {
            path.add(0, new int[]{current.x, current.y}); // On reconstruit en remontant les parents
            current = current.parent;
        }
        return path;
    }

    /**
     * Récupère les voisins valides (sans obstacles) sur la grille.
     * 
     * @param x La position X du noeud.
     * @param y La position Y du noeud.
     * @param grid La grille de jeu.
     * @return Une liste des voisins valides du noeud.
     */
    private List<int[]> getNeighbors(int x, int y, Grid grid) {
        List<int[]> neighbors = new ArrayList<>();

        if (grid.isEmpty(x + 1, y)) neighbors.add(new int[]{x + 1, y});
        if (grid.isEmpty(x - 1, y)) neighbors.add(new int[]{x - 1, y});
        if (grid.isEmpty(x, y + 1)) neighbors.add(new int[]{x, y + 1});
        if (grid.isEmpty(x, y - 1)) neighbors.add(new int[]{x, y - 1});

        return neighbors;
    }

    /**
     * Génère une clé unique pour identifier un noeud dans la grille.
     * 
     * @param x La position X du noeud.
     * @param y La position Y du noeud.
     * @return Une clé unique représentant les coordonnées du noeud.
     */
    private String toKey(int x, int y) {
        return x + "," + y;
    }

    /**
     * Classe interne représentant un noeud pour l'algorithme A*.
     */
    private static class Node {
        int x, y;
        Node parent;
        int gCost; // Coût depuis le début
        int hCost; // Heuristique
        int fCost; // gCost + hCost

        Node(int x, int y, Node parent, int gCost, int hCost) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost;
        }
    }
}
