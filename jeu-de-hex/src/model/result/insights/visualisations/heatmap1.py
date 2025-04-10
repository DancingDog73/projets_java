import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt

df = pd.read_csv('../../game_result.csv')
df['budgetRatio'] = df['budgetPlayer1'] / df['budgetPlayer2']
prob_victory = df.groupby(['gridSize', 'budgetRatio'])['winner'].apply(lambda x: (x == 1).mean()).unstack()
plt.figure(figsize=(10, 6))
print(prob_victory)
sns.heatmap(prob_victory, cmap="coolwarm", annot=True, fmt=".2f", linewidths=0.5)
plt.title("Probabilité de victoire du joueur 1\nen fonction du rapport de budget et de la taille de la grille")
plt.xlabel("Rapport de budget (Joueur 1 / Joueur 2)")
plt.ylabel("Taille de la grille")
plt.show()
