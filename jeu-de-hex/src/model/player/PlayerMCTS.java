package model.player;
import model.strategies.mcts.MCTS;

/**
 * Classe représentant un joueur utilisant l'algorithme MCTS (Monte Carlo Tree Search).
 * Cette classe hérite de PlayerAbstract et utilise une stratégie MCTS.
 */
public class PlayerMCTS extends PlayerAbstract {


    /**
     * Constructeur créant un joueur MCTS avec un nombre spécifique de simulations et un paramètre d'exploration.
     *
     * @param simulations        Nombre de simulations utilisées dans l'algorithme MCTS.
     * @param exploration_param  Paramètre d'exploration pour guider la recherche MCTS.
     */
    public PlayerMCTS(int simulations, double exploration_param) {
        super(new MCTS(simulations, exploration_param));
    }

     /**
     * Constructeur créant un joueur MCTS avec un identifiant, un nombre spécifique de simulations et un paramètre d'exploration.
     *
     * @param simulations        Nombre de simulations utilisées dans l'algorithme MCTS.
     * @param exploration_param  Paramètre d'exploration pour guider la recherche MCTS.
     * @param id                 Identifiant unique du joueur.
     */
    public PlayerMCTS(int simulations, double exploration_param, int id) {
        super(new MCTS(simulations, exploration_param), id);
    }

    /**
     * Constructeur créant un joueur MCTS avec un identifiant, un nombre spécifique de simulations,
     * un paramètre d'exploration et l'activation optionnelle de l'optimisation RAVE.
     *
     * @param simulations        Nombre de simulations utilisées dans l'algorithme MCTS.
     * @param exploration_param  Paramètre d'exploration pour guider la recherche MCTS.
     * @param id                 Identifiant unique du joueur.
     * @param useRave            Indique si l'optimisation RAVE doit être activée.
     */
    public PlayerMCTS(int simulations, double exploration_param,int id, boolean useRave) {
        super(new MCTS(simulations, exploration_param, useRave), id);
    }



}
