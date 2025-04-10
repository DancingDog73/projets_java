import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt

# Charger les données
df = pd.read_csv("../../game_result.csv")

# Convertir les booléens en entiers (si nécessaire)
df["ravePlayerOne"] = df["ravePlayerOne"].astype(int)
df["ravePlayerTwo"] = df["ravePlayerTwo"].astype(int)

# Transformer la colonne "winner" en une variable binaire (1 si Joueur 1 gagne, 0 sinon)
df["winner"] = (df["winner"] == 1).astype(int)

# Sélection des variables pour l'analyse
features = ["gridSize", "budgetPlayer1", "budgetPlayer2", "movesCount", "duration",
            "nodeCountPlayer1", "nodeCountPlayer2", "ravePlayerOne", "ravePlayerTwo"]

# Calcul des corrélations avec la victoire du Joueur 1
correlation_matrix = df[features + ["winner"]].corr()

# Création de la heatmap
plt.figure(figsize=(10, 8))
sns.heatmap(correlation_matrix, annot=True, cmap="coolwarm", fmt=".2f", linewidths=0.5)

# Labels et titre
plt.title("Corrélation entre les paramètres du jeu et la probabilité de victoire du Joueur 1")
plt.show()
