import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from scipy.stats import chi2_contingency

# Charger les données
df = pd.read_csv("../../game_result.csv")

stats_names = [
    "gridSize", "budgetPlayer1", "budgetPlayer2", "winner",
    "duration", "movesCount", "nodeCountPlayer1", "nodeCountPlayer2",
    "ravePlayerOne", "ravePlayerTwo"
]

# --- Calcul des écarts entre valeurs observées et attendues ---
ecarts_matrices = {}
for i in stats_names:
    for j in stats_names:
        if i != j and df[i].nunique() > 1 and df[j].nunique() > 1:
            contingency_table = pd.crosstab(df[i], df[j])
            chi2, p, dof, expected = chi2_contingency(contingency_table, correction=True)
            
            ecart = contingency_table - expected
            ecarts_matrices[f"{i} vs {j}"] = ecart

# --- Heatmaps des écarts observés vs attendus ---
fig, axes = plt.subplots(len(ecarts_matrices) // 3 + 1, 3, figsize=(15, 5 * (len(ecarts_matrices) // 3 + 1)))
axes = axes.flatten()

for idx, (title, matrix) in enumerate(ecarts_matrices.items()):
    sns.heatmap(matrix, annot=True, fmt=".1f", cmap="coolwarm", center=0, ax=axes[idx])
    axes[idx].set_title(title)

plt.tight_layout()
plt.savefig("ecarts_observes_vs_attendus.png")
plt.show()
