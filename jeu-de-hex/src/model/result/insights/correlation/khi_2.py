from scipy.stats import chi2_contingency
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np

df = pd.read_csv("../../game_result.csv")

for col in ["ravePlayerOne", "ravePlayerTwo"]:
    df[col] = df[col].astype(int)

stats_names = ["gridSize","budgetPlayer1","budgetPlayer2","winner","duration","movesCount","nodeCountPlayer1","nodeCountPlayer2","ravePlayerOne","ravePlayerTwo"]
matrice = []

# Calcul des corrélations entre toutes les paires de statistiques
for i in stats_names:  #lignes
    ligne = []
    for j in stats_names: #lignes
        # Si une des colonnes n'a qu'une seule valeur unique, la corrélation n'est pas définie
        if df[i].nunique() <= 1 or df[j].nunique() <= 1:
            ligne.append(None)
        else:
            contingency_table = pd.crosstab(df[i], df[j])
            chi2, p, dof, expected = chi2_contingency(contingency_table)
            ligne.append(p)
    matrice.append(ligne)


stats_names_for_display = stats_names
stats_names_for_display[3] = "winner == player 1"
df_p_values = pd.DataFrame(matrice, index=stats_names_for_display, columns=stats_names_for_display)
print(df_p_values)

df_p_values = df_p_values.map(lambda x: np.nan if x is None else x)
plt.figure(figsize=(10,8))
sns.heatmap(df_p_values, annot=True, fmt='.3f', cmap='coolwarm', vmin=0, vmax=1, cbar_kws={'label':'p-value'})
plt.title("Heatmap des p-values du test Khi-2")
plt.tight_layout()

#plt.savefig("heatmap_khi2.png")
plt.show()

