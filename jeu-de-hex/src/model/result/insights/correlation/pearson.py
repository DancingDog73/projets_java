import pandas as pd
import scipy.stats as stats
import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns

df = pd.read_csv("../../game_result.csv")

for col in ["ravePlayerOne", "ravePlayerTwo"]:
    df[col] = df[col].astype(int)

#df["winner"] = (df["winner"] == 1).astype(int)

df["ratio_budget_p1/p2"] = df["budgetPlayer1"]/df["budgetPlayer2"]
stats_names = ["gridSize","budgetPlayer1","budgetPlayer2","winner","duration","movesCount","nodeCountPlayer1","nodeCountPlayer2","ravePlayerOne","ravePlayerTwo","ratio_budget_p1/p2"]
matrice = []

# Calcul des corrélations entre toutes les paires de statistiques
for i in stats_names: #lignes
    ligne = []
    for j in stats_names: #lignes
        # Si une des colonnes n'a qu'une seule valeur unique, la corrélation n'est pas définie
        if df[i].nunique() <= 1 or df[j].nunique() <= 1:
            ligne.append(None)
        else:
            correlation, _ = stats.pearsonr(df[i], df[j])
            ligne.append(correlation)
    matrice.append(ligne)

stats_names_for_display = stats_names
stats_names_for_display[3] = "winner == player 1"
df_corr = pd.DataFrame(matrice, index=stats_names_for_display, columns=stats_names_for_display)
print(df_corr)



df_corr = df_corr.map(lambda x: np.nan if x is None else x)
plt.figure(figsize=(10,8))
sns.heatmap(df_corr, annot=True, fmt='.3f', cmap='viridis', vmin=-1, vmax=1, cbar_kws={'label':'Corrélation de Pearson'})
plt.title("Heatmap des corrélations de Pearson entre les statistiques du jeu de hex")
plt.tight_layout()

#plt.savefig("heatmap_khi2.png")
plt.show()
