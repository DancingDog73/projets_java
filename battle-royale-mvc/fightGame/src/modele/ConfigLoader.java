package modele;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable du chargement et du parsing de la configuration d'un jeu à partir d'un fichier XML.
 * Utilise le parseur SAX pour analyser le fichier et construire un objet {@link GameConfig} représentant
 * la configuration complète.
 */
public class ConfigLoader extends DefaultHandler {
    private GameConfig config;
    private StringBuilder currentValue;

    // Variables temporaires pour les joueurs
    private PlayerConfig currentPlayer;
    private List<PlayerConfig> players;

    /**
     * Constructeur par défaut.
     * Initialise la configuration de jeu et les structures nécessaires pour le parsing.
     */
    public ConfigLoader() {
        config = new GameConfig();
        players = new ArrayList<>();
        currentValue = new StringBuilder();
    }

    /**
     * Récupère la configuration du jeu chargée après le parsing.
     *
     * @return un objet {@link GameConfig} contenant les paramètres du jeu.
     */
    public GameConfig getConfig() {
        return config;
    }

    /**
     * Lance le parsing du fichier XML spécifié.
     *
     * @param filePath chemin vers le fichier XML à parser.
     */
    public void parse(String filePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(filePath, this);
        } catch (Exception e) {
            System.err.println("Erreur lors du parsing XML : " + e.getMessage());
        }
    }

    /**
     * Méthode appelée au début d'un élément XML.
     * Utilisée pour initialiser les objets nécessaires lors du parsing.
     *
     * @param uri        l'espace de noms URI (non utilisé ici).
     * @param localName  le nom local de l'élément (non utilisé ici).
     * @param qName      le nom qualifié de l'élément.
     * @param attributes les attributs de l'élément (non utilisé ici).
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        currentValue.setLength(0); // Réinitialise le contenu
        if (qName.equals("player")) {
            currentPlayer = new PlayerConfig();
        }
    }

    /**
     * Méthode appelée à la fin d'un élément XML.
     * Associe les données parsées à la configuration ou au joueur en cours de construction.
     *
     * @param uri       l'espace de noms URI (non utilisé ici).
     * @param localName le nom local de l'élément (non utilisé ici).
     * @param qName     le nom qualifié de l'élément.
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName == "mines") {
            System.out.println(currentValue);
        }
        switch (qName) {
            case "width" -> config.setGridWidth(Integer.parseInt(currentValue.toString()));
            case "height" -> config.setGridHeight(Integer.parseInt(currentValue.toString()));
            case "wallDensity" -> config.setObstacles(Integer.parseInt(currentValue.toString()));
            case "powerups" -> config.setBonus(Integer.parseInt(currentValue.toString()));
            case "turns" -> config.setTurns(Integer.parseInt(currentValue.toString()));
            case "energypill" -> config.setEnergyPill(Integer.parseInt(currentValue.toString()));
            case "ammogrid" -> config.setAmmo(Integer.parseInt(currentValue.toString()));
            case "wallLines" -> config.setWallLines(Integer.parseInt(currentValue.toString()));
            case "mines" -> config.setMines(Integer.parseInt(currentValue.toString()));
            case "bombs" -> config.setBombs(Integer.parseInt(currentValue.toString()));
            case "name" -> currentPlayer.setName(currentValue.toString());
            case "energy" -> currentPlayer.setEnergy(Integer.parseInt(currentValue.toString()));
            case "ammo" -> currentPlayer.setAmmo(Integer.parseInt(currentValue.toString()));
            case "numberofBomb" -> currentPlayer.setNumberOfBomb(Integer.parseInt(currentValue.toString()));
            case "numberofMine" -> currentPlayer.setNumberOfMine(Integer.parseInt(currentValue.toString()));
            case "range" -> currentPlayer.setRange(Integer.parseInt(currentValue.toString()));
            case "player" -> players.add(currentPlayer); // Ajoute le joueur une fois construit
        }
    }

    /**
     * Méthode appelée lors de la lecture des données textuelles entre les balises XML.
     * Accumule les caractères pour construire les valeurs des éléments XML.
     *
     * @param ch     tableau de caractères du document XML.
     * @param start  position de début dans le tableau.
     * @param length nombre de caractères à lire.
     */
    @Override
    public void characters(char[] ch, int start, int length) {
        currentValue.append(ch, start, length);
    }

    /**
     * Méthode appelée à la fin du document XML.
     * Permet de finaliser la configuration du jeu en assignant la liste des joueurs construite.
     */
    @Override
    public void endDocument() {
        config.setPlayers(players);
    }
}
