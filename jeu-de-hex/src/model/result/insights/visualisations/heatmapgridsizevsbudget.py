import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt

# Charger les données CSV
df = pd.read_csv("../../game_result.csv")

# Calculer la probabilité de victoire du joueur 1 pour chaque (budgetPlayer1, gridSize)
heatmap_data = df.groupby(["budgetPlayer1", "gridSize"])["winner"].apply(lambda x: (x == 1).mean()).unstack()

# Création de la heatmap
plt.figure(figsize=(12, 6))
sns.heatmap(heatmap_data, cmap="coolwarm", annot=True, fmt=".2f", linewidths=0.5)

# Labels et titre
plt.title("Probabilité de victoire du Joueur 1 en fonction du budget et de la taille de la grille")
plt.xlabel("Taille de la grille")
plt.ylabel("Budget du Joueur 1")
plt.show()
