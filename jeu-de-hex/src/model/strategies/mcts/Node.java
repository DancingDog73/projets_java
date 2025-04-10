package model.strategies.mcts;
import model.game.*;
import model.player.*;

import java.util.*;

/**
 * Représente un nœud dans l'arbre de recherche MCTS (Monte Carlo Tree Search).
 */
public class Node {

    private State state;
    private Node father;
    private List<Node> children;
    private int visits;
    private double wins;
    private Move move;
    private Player player;
    

    /**
     * Constructeur de la classe Node.
     * 
     * @param state  L'état du jeu associé à ce nœud.
     * @param father Le nœud parent de ce nœud (peut être null pour la racine).
     * @param move   Le mouvement qui a conduit à cet état.
     * @param player Le joueur ayant effectué le mouvement.
    */
    public Node(State state, Node father, Move move, Player player){
        this.state = state;
        this.father = father;
        this.children = new ArrayList<>();
        this.visits = 0;
        this.wins = 0.0;
        this.move = move;
        this.player = player;
    }

    public State getState(){
        return this.state;
    }

    public Node getFather(){
        return this.father;
    }

    public List<Node> getChildren(){
        return this.children;
    }

    public int getVisits(){
        return this.visits;
    }

    public double getWins(){
        return this.wins;
    }

    public Move getMove(){
        return this.move;
    }

    public int getMoveX(){
        return this.move.getPosition()[0];
    }

    public int getMoveY(){
        return this.move.getPosition()[1];
    }

    /**
     * Ajoute un nœud enfant à ce nœud.
     * 
     * @param childNode Le nœud enfant à ajouter.
     */
    public void addChild(Node childNode){
        this.children.add(childNode);
    }

    /**
     * Met à jour les statistiques de ce nœud après une simulation.
     * 
     * @param result Résultat de la simulation (victoire ou défaite).
     */
    public void updateStats(double result){
        this.visits++;
        this.wins += result;
    }

    /**
     * Vérifie si ce nœud est entièrement développé, c'est-à-dire si tous ses enfants possibles ont été explorés.
     * 
     * @return true si tous les enfants ont été explorés, sinon false.
     */
    public boolean isFullyExpanded(){
        return this.children.size() == this.state.getMoves(this.player).size();
    }

    /**
     * Retourne le meilleur enfant de ce nœud en fonction de son taux de victoire.
     * 
     * @return Le nœud enfant avec le meilleur taux de victoire.
     */
    public Node getBestChild(){
        return Collections.max(children, Comparator.comparingDouble(Node::getWinRate));
    }

    /**
     * Calcule le taux de victoire de ce nœud.
     * 
     * @return Le taux de victoire (victoires / visites).
     */
    public double getWinRate() {
        return visits == 0 ? 0 : wins / visits;
    }

}