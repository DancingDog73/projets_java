package modele.factories.playerfactories;

import playerModele.*;
import modele.*;
import modele.strategies.playerstrategies.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe PlayerFactory responsable de la création des joueurs en fonction
 * des configurations définies et des stratégies spécifiées.
 */
public class PlayerFactory {
    private final Map<String, PlayerConfig> playerConfigMap;

    /**
     * Constructeur de PlayerFactory qui initialise une map pour associer les types
     * de joueurs à leur configuration correspondante.
     *
     * @param playerConfigs une liste de configurations de joueurs définies dans le fichier XML.
     */
    public PlayerFactory(List<PlayerConfig> playerConfigs) {
        playerConfigMap = new HashMap<>();
        for (PlayerConfig config : playerConfigs) {
            playerConfigMap.put(config.getName().toLowerCase(), config);
        }
    }

    /**
     * Crée un joueur en fonction de son type, de son nom, de la stratégie spécifiée,
     * et de sa position initiale sur la grille.
     *
     * @param type           le type de joueur (par exemple, "random", "human", etc.).
     * @param playerName     le nom du joueur.
     * @param playerStrategy la stratégie du joueur (null pour un joueur humain).
     * @param x              la position X initiale du joueur sur la grille.
     * @param y              la position Y initiale du joueur sur la grille.
     * @return un objet Player correspondant au type et à la stratégie spécifiés.
     * @throws IllegalArgumentException si le type de joueur est inconnu.
     */
    public Player createPlayer(String type, String playerName, PlayerStrategy playerStrategy, int x, int y) {
        // Récupérer la configuration pour le type donné
        PlayerConfig config = playerConfigMap.get(type.toLowerCase());
        if (config == null) {
            throw new IllegalArgumentException("Type de joueur inconnu : " + type);
        }

        // Création d'un joueur de type "random"
        if (type.toLowerCase().equals("random")) {
            return new PlayerRandom(
                x,
                y,
                playerName,
                config.getEnergy(),
                config.getAmmo(),
                config.getNumberOfBomb(),
                config.getNumberOfMine(),
                config.getRange()
            );
        }

        // Création d'un joueur avec une stratégie donnée
        if (playerStrategy != null) {
            return new PlayerWithStrategy(
                x,
                y,
                playerName,
                config.getEnergy(),
                config.getAmmo(),
                config.getNumberOfBomb(),
                config.getNumberOfMine(),
                config.getRange(),
                playerStrategy
            );
        }

        // Par défaut, création d'un joueur humain
        return new PlayerHuman(
            x,
            y,
            playerName,
            config.getEnergy(),
            config.getAmmo(),
            config.getNumberOfBomb(),
            config.getNumberOfMine(),
            config.getRange()
        );
    }
}
