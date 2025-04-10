import seaborn as sns
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('../../game_result.csv')
df['budgetRatio'] = df['budgetPlayer1'] / df['budgetPlayer2']
heatmap_data = df.groupby(['gridSize', 'budgetRatio'])['movesCount'].apply(lambda x: (x == 1).mean()).unstack()
plt.figure(figsize=(10,6))
sns.heatmap(heatmap_data, cmap="viridis", annot=True, fmt=".1f")
plt.title("Nombre moyen de coups en fonction du rapport de budget et de la taille de la grille")
plt.xlabel("Rapport de budget (Joueur 1 / Joueur 2)")
plt.ylabel("Taille de la grille")
plt.show()